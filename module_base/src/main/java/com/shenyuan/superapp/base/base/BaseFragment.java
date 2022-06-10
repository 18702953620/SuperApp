package com.shenyuan.superapp.base.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.ColorRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

import com.shenyuan.superapp.base.ARouterPath;
import com.shenyuan.superapp.base.R;
import com.shenyuan.superapp.base.api.common.TokenCommon;
import com.shenyuan.superapp.base.api.exception.BaseException;
import com.shenyuan.superapp.base.api.BasePresenter;
import com.shenyuan.superapp.base.api.BaseView;
import com.shenyuan.superapp.base.api.TokenHandler;
import com.shenyuan.superapp.base.dialog.BaseDialog;
import com.shenyuan.superapp.base.dialog.SimStudentDialog;
import com.shenyuan.superapp.base.utils.LogUtils;
import com.shenyuan.superapp.base.widget.GifLoadingView;


/**
 * @author ch
 * @date 2019/10/09 09:15
 * 基类Fragment
 */
public abstract class BaseFragment<B extends ViewDataBinding, P extends BasePresenter> extends Fragment implements BaseView {
    private View rootView;

    public Context context;
    /**
     * binding
     */
    public B binding;

    /**
     * 控件是否初始化完成
     */
    private boolean isViewCreated;

    /**
     * 当前fragment是否加载过数据,如加载过数据，则不再加载
     */
    private boolean isLoadCompleted;
    /**
     * 是不是可见
     */
    private boolean isUiVisible;

    protected P presenter;
    private GifLoadingView loadingView;


    /**
     * 懒加载,强制子类重写
     */
    protected abstract void loadData();

    /**
     * 获取布局id
     *
     * @return 布局id
     */
    protected abstract int getLayoutId();

    /**
     * 初始化
     */
    protected abstract void initView();

    /**
     * 添加点击事件
     */
    protected abstract void addListener();

    /**
     * 创建 Presenter
     *
     * @return P
     */
    protected abstract P createPresenter();


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isUiVisible = isVisibleToUser;
        if (isVisibleToUser && isViewCreated && isUiVisible && !isLoadCompleted) {
            isLoadCompleted = true;
            loadData();
        }
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (isViewCreated && isUiVisible) {
            loadData();
            isLoadCompleted = true;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        if (binding != null) {
            rootView = binding.getRoot();
        }
        presenter = createPresenter();
        initView();
        addListener();
        isViewCreated = true;
        return rootView;
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        isUiVisible = !hidden;
    }


    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onDestroy() {
        if (presenter != null) {
            presenter.detachView();
        }
        super.onDestroy();
    }

    /**
     * 打开指定的activity
     *
     * @param cls Class
     */
    public void startA(@NonNull Class<?> cls) {
        Intent intent = new Intent(context, cls);
        startActivity(intent);
    }


    /**
     * toast
     *
     * @param msg String
     */
    public void showToast(@NonNull String msg) {
        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }


    /**
     * 通过资源res获得view
     *
     * @param res res
     * @return View
     */
    public View getViewByRes(@LayoutRes int res) {
        return LayoutInflater.from(context).inflate(res, null);
    }

    private void closeLoadingDialog() {
        if (loadingView != null) {
            loadingView.onDismiss(null);
        }
    }


    private void showLoadingDialog() {
        if (loadingView == null) {
            loadingView = new GifLoadingView();
            loadingView.setImageResource(R.mipmap.num19);
        }
        loadingView.show(getActivity().getFragmentManager());
    }


    /**
     * 获得TextView 的文本
     *
     * @param tv TextView
     * @return String
     */
    public String getTv(@NonNull TextView tv) {
        return tv.getText().toString().trim();
    }

    /**
     * @param color color
     * @return int
     */
    public int getValuesColor(@ColorRes int color) {
        return context.getResources().getColor(color);
    }

    public View getRootView() {
        return rootView;
    }

    @Override
    public void showLoading() {
        showLoadingDialog();
    }


    @Override
    public void hideLoading() {
        closeLoadingDialog();
    }

    @Override
    public void showError(String msg) {
        showToast(msg);
    }

    @Override
    public void onErrorCode(String code, String msg) {
        LogUtils.e("BaseFragment code=" + code + ",msg=" + msg);
        if (BaseException.CODE_LOGIN_OUT.equals(code)) {
            //登出
            TokenHandler.getInstance().sendLoginOutMsg();
        } else if (BaseException.USER_ACCOUNT_REPLACED.equals(code)) {
            TokenHandler.getInstance().sendLoginOutWithMsg(msg, context);
        } else if (BaseException.BAD_NETWORK_MSG.equals(code) ||
                BaseException.CONNECT_ERROR_MSG.equals(code) ||
                BaseException.CONNECT_TIMEOUT_MSG.equals(code)) {
            TokenHandler.getInstance().sendErrorPageMsg();
        } else {
            showError(msg);
        }
    }

    public void showLoginDialog() {
        new SimStudentDialog.Builder(context).submitText("去认证").title("该功能仅限校内身份访问请进行实名认证")
                .onBacklistener(new BaseDialog.BaseOnBack() {
                    @Override
                    public void onConfirm() {
                        ARouterPath.router(ARouterPath.AppStudent.APP_STUDENT_LOGIN);
                        if (getActivity() != null) {
                            getActivity().finish();
                        }
                    }
                }).show();
    }
}

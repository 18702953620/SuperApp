package com.shenyuan.superapp.base.base.mvvm;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Toast;

import androidx.activity.ComponentActivity;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.alibaba.android.arouter.launcher.ARouter;
import com.shenyuan.superapp.base.R;
import com.shenyuan.superapp.base.utils.LogUtils;
import com.shenyuan.superapp.base.widget.GifLoadingView;

/**
 * @author ch
 * @date 2022/6/7-17:43
 * desc
 */
public abstract class BaseVMActivity<B extends ViewDataBinding, VM extends BaseViewModel> extends ComponentActivity {

    /**
     * context
     */
    public Context context;

    /**
     * binding
     */
    public B binding;

    /**
     * viewModel
     */
    public VM viewModel;

    /**
     *
     */
    private ViewModelProvider viewModelProvider;

    /**
     * loading
     */
    private GifLoadingView loadingView;


    /**
     * 布局
     *
     * @return id
     */
    protected abstract int getLayoutId();

    /**
     * 创建viewModel
     *
     * @return VM
     */
    protected abstract VM createViewModel();

    /**
     * 初始化view
     */
    protected abstract void initView();

    /**
     * 添加监听
     */
    protected abstract void addListener();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setStatusBar();
        ARouter.getInstance().inject(this);
        if (getLayoutId() != 0) {
            binding = DataBindingUtil.setContentView(this, getLayoutId());
        }
        viewModel = createViewModel();
        initView();
        addListener();
    }

    /**
     * 设置状态栏
     */
    protected void setStatusBar() {

    }

    protected <T extends ViewModel> T getScopeViewModel(@NonNull Class<T> modelClass) {
        if (viewModelProvider == null) {
            viewModelProvider = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()));
        }
        return viewModelProvider.get(modelClass);
    }


    protected <T> boolean isSucc(BaseResult<T> result) {
        LogUtils.e("code=" + result.getCode() + ",msg=" + result.getMsg());

        if (BaseResult.CODE_SHOW_LODING.equals(result.getCode())) {
            //显示加载动画
            showLoadingDialog();
            return false;
        } else if (!BaseResult.CODE_SUCC.equals(result.getCode())) {
            showToast(result.getMsg());
            return false;
        }
        closeLoadingDialog();
        return true;
    }

    protected void closeLoadingDialog() {
        if (loadingView != null) {
            loadingView.onDismiss(null);
        }
    }


    protected void showLoadingDialog() {
        if (loadingView == null) {
            loadingView = new GifLoadingView();
            loadingView.setImageResource(R.mipmap.num19);
        }
        loadingView.show(getFragmentManager());
    }


    /**
     * @param s s
     */
    public void showToast(String s) {
        Toast toast = Toast.makeText(context, s, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }


}

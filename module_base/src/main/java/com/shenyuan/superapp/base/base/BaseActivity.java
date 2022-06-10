package com.shenyuan.superapp.base.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.LayoutRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.alibaba.android.arouter.launcher.ARouter;
import com.jaeger.library.StatusBarUtil;
import com.shenyuan.superapp.base.R;
import com.shenyuan.superapp.base.api.exception.BaseException;
import com.shenyuan.superapp.base.api.BasePresenter;
import com.shenyuan.superapp.base.api.BaseView;
import com.shenyuan.superapp.base.api.TokenHandler;
import com.shenyuan.superapp.base.utils.LogUtils;
import com.shenyuan.superapp.base.utils.StatusBarUtils;
import com.shenyuan.superapp.base.widget.GifLoadingView;

/**
 * @author ch
 * @date 2020/4/21-10:20
 * desc 基类
 */
public abstract class BaseActivity<B extends ViewDataBinding, P extends BasePresenter> extends AppCompatActivity implements BaseView {
    /**
     * context
     */
    public Context context;

    /**
     * presenter
     */
    protected P presenter;
    /**
     * binding
     */
    public B binding;

    /**
     * loading
     */
    private GifLoadingView loadingView;

    /**
     * 创建 Presenter
     *
     * @return P
     */
    protected abstract P createPresenter();

    /**
     * 布局
     *
     * @return id
     */
    protected abstract int getLayoutId();

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
        presenter = createPresenter();
        initView();
        addListener();
    }

    /**
     * 设置状态栏
     */
    protected void setStatusBar() {
        setStatusBarColor();
    }

    /**
     * 白色状态栏
     */
    protected void setStatusBarColor() {
        //设置状态栏颜色;
        setStatusBarColor(R.color.color_ffffff);
    }

    /**
     * 设置状态栏颜色
     */
    protected void setStatusBarColor(int color) {
        //设置状态栏颜色;
        StatusBarUtil.setColorForSwipeBack(this,
                getResources().getColor(color), 0);
        //暗色
        StatusBarUtils.statusBarLightMode(this, StatusBarUtils.statusBarLightMode(this));
    }
    /**
     * 设置状态栏颜色
     */
    protected void setStatusBarColorLight(int color) {
        //设置状态栏颜色;
        StatusBarUtil.setColorForSwipeBack(this,
                getResources().getColor(color), 0);
        //暗色
        StatusBarUtils.statusBarDarkMode(this, StatusBarUtils.statusBarLightMode(this));
    }

    /**
     * 设置 无状态栏 亮色模式（状态栏黑色字体）
     */
    public void setNoStatusBarByLight() {
        StatusBarUtil.setTranslucentForImageView(this, 0, null);
        StatusBarUtils.statusBarLightMode(this, StatusBarUtils.statusBarLightMode(this));
    }

    /**
     * 设置 无状态栏 暗色模式（状态栏白色字体）
     */
    public void setNoStatusBarByDrak() {
        StatusBarUtil.setTranslucentForImageView(this, 0, null);
        //亮色
        StatusBarUtils.statusBarDarkMode(this, StatusBarUtils.statusBarLightMode(this));
    }


    /**
     * @param color color
     * @return int
     */
    public int getValuesColor(int color) {
        return context.getResources().getColor(color);
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

    /**
     * 获得TextView 的文本
     *
     * @param tv TextView
     * @return String
     */
    public String getTv(TextView tv) {
        return tv == null ? "" : tv.getText().toString().trim();
    }


    @SuppressLint("UseCompatLoadingForDrawables")
    public Drawable getDrawableRes(int res) {
        return getResources().getDrawable(res);
    }


    @Override
    protected void onDestroy() {
        if (presenter != null) {
            presenter.detachView();
        }
        super.onDestroy();
    }

    /**
     * @param s s
     */
    public void showToast(String s) {
        Toast toast = Toast.makeText(context, s, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
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
        loadingView.show(getFragmentManager());
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
        LogUtils.e("BaseActivity code=" + code + ",msg=" + msg);
        if (BaseException.CODE_LOGIN_OUT.equals(code)) {
            //登出
            TokenHandler.getInstance().sendLoginOutMsg();
        } else if (BaseException.USER_ACCOUNT_REPLACED.equals(code)) {
            TokenHandler.getInstance().sendLoginOutWithMsg(msg, context);
        } else if (BaseException.BAD_NETWORK_MSG.equals(code) ||
                BaseException.CONNECT_ERROR_MSG.equals(code) ||
                BaseException.CONNECT_TIMEOUT_MSG.equals(code)) {

            TokenHandler.getInstance().sendErrorPageMsg();

            showError(msg);
        } else {
            showError(msg);
        }
    }
}

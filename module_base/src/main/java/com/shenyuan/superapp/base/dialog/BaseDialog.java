package com.shenyuan.superapp.base.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;

import com.shenyuan.superapp.base.R;


/**
 * @author ch
 * @date 2021/1/19 9:50
 * desc
 */
public abstract class BaseDialog<B extends ViewDataBinding> extends Dialog implements LifecycleOwner {

    /**
     * 标题
     */
    public String title;
    /**
     * 取消按钮文字
     */
    public String cancelText;
    /**
     * 确定按钮文字
     */
    public String submitText;

    /**
     * binding
     */
    protected B binding;
    protected LifecycleRegistry mLifecycleRegistry;

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
     * 是否能取消
     */
    protected boolean cancelable;

    /**
     * 回调
     */
    protected BaseOnBack onBack;


    public BaseDialog(@NonNull Context context) {
        super(context, R.style.dialog);
        init();
    }

    public BaseDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        init();
    }


    private void init() {
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), getLayoutId(), null, false);
        if (binding != null) {
            setContentView(binding.getRoot());
            setCanceledOnTouchOutside(cancelable);
            setCancelable(cancelable);
        }

        mLifecycleRegistry = new LifecycleRegistry(this);
        initView();
    }


    /**
     * 展示
     *
     * @param onBack onBack
     */
    public void showDialog(BaseOnBack onBack) {
        this.onBack = onBack;
        show();
    }


    @Override
    public void show() {
        super.show();
        requestMatch();
    }

    private void requestMatch() {
        Window window = getWindow();
        WindowManager.LayoutParams layoutParams = window.getAttributes();
        layoutParams.width = WindowManager.LayoutParams.MATCH_PARENT;
        window.setAttributes(layoutParams);
    }

    /**
     * 取消
     */
    public void onCancel() {
        if (onBack != null) {
            onBack.onCancel();
        }
        dismiss();
    }

    /**
     * 确认
     */
    public void onConfirm() {
        if (onBack != null) {
            onBack.onConfirm();
        }
        dismiss();
    }

    /**
     * 确认
     */
    public void onConfirm(Object object) {
        if (onBack != null) {
            onBack.onConfirm(object);
        }
        dismiss();
    }

    /**
     * @param s s
     */
    public void showToast(String s) {
        Toast toast = Toast.makeText(getContext(), s, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }


    public void setTitle(String title) {
        this.title = title;
    }

    public void setCancelText(String cancelText) {
        this.cancelText = cancelText;
    }

    public void setSubmitText(String submitText) {
        this.submitText = submitText;
    }

    @NonNull
    @Override
    public Lifecycle getLifecycle() {
        return mLifecycleRegistry;
    }

    /**
     * 回调
     */
    public abstract static class BaseOnBack {
        /**
         * 确定
         */
        public void onConfirm() {
        }

        /**
         * 取消
         */
        public void onCancel() {
        }

        /**
         * 带参数
         *
         * @param object object
         */
        public void onConfirm(Object object) {
        }
    }
}

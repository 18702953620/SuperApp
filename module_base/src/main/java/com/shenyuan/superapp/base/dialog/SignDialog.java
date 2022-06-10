package com.shenyuan.superapp.base.dialog;

import android.content.Context;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;

import com.shenyuan.superapp.base.R;
import com.shenyuan.superapp.base.databinding.DialogSignBinding;


/**
 * @author ch
 * @date 2021/1/19 10:24
 * desc
 */
public class SignDialog extends BaseDialog<DialogSignBinding> {
    /**
     * 标题
     */
    private String title;
    /**
     * 确定按钮文字
     */
    private String submitText;

    public void setSubmitTextColor() {
        binding.tvSimSubmit.setTextColor(getContext().getResources().getColor(R.color.color_3cbf7f));
    }


    public SignDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_sign;
    }

    @Override
    protected void initView() {
        binding.tvSimSubmit.setOnClickListener(v -> onConfirm());
    }


    public void setTitle(String title) {
        this.title = title;
    }


    public void setSubmitText(String submitText) {
        this.submitText = submitText;
    }

    @Override
    public void showDialog(BaseOnBack onBack) {
        if (!TextUtils.isEmpty(title)) {
            binding.tvSimTitle.setText(title);
        }
        if (!TextUtils.isEmpty(submitText)) {
            binding.tvSimSubmit.setText(submitText);
        }
        super.showDialog(onBack);
    }

    public static class Builder {
        private final Context context;

        public Builder(Context context) {
            this.context = context;
        }

        /**
         * 标题
         */
        private String title;
        /**
         * 确定按钮文字
         */
        private String submitText;

        private BaseOnBack onBack;

        public Builder onBacklistener(BaseOnBack onBack) {
            this.onBack = onBack;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder submitText(String submitText) {
            this.submitText = submitText;
            return this;
        }


        public SignDialog build() {
            if (context == null) {
                return null;
            }
            SignDialog simDialog = new SignDialog(context);
            simDialog.setTitle(title);
            simDialog.setSubmitText(submitText);
            return simDialog;
        }

        public void show() {
            SignDialog dialog = build();
            if (dialog == null) {
                return;
            }
            dialog.showDialog(onBack);
        }

        public void showStudent() {
            SignDialog dialog = build();
            dialog.setSubmitTextColor();
            if (dialog == null) {
                return;
            }
            dialog.showDialog(onBack);
        }
    }

}

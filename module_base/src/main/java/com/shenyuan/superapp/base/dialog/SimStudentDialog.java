package com.shenyuan.superapp.base.dialog;

import android.content.Context;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;

import com.shenyuan.superapp.base.R;
import com.shenyuan.superapp.base.databinding.DialogSimpleBinding;
import com.shenyuan.superapp.base.databinding.DialogStudentSimpleBinding;


/**
 * @author ch
 * @date 2021/1/19 10:24
 * desc
 */
public class SimStudentDialog extends BaseDialog<DialogStudentSimpleBinding> {
    /**
     * 标题
     */
    private String title;
    /**
     * 取消按钮文字
     */
    private String cancelText;
    /**
     * 确定按钮文字
     */
    private String submitText;

    public SimStudentDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_student_simple;
    }

    @Override
    protected void initView() {
        binding.tvSimCancle.setOnClickListener(v -> onCancel());
        binding.tvSimSubmit.setOnClickListener(v -> onConfirm());
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

    @Override
    public void showDialog(BaseOnBack onBack) {
        if (!TextUtils.isEmpty(title)) {
            binding.tvSimTitle.setText(title);
        }
        if (!TextUtils.isEmpty(submitText)) {
            binding.tvSimSubmit.setText(submitText);
        }
        if (!TextUtils.isEmpty(cancelText)) {
            binding.tvSimCancle.setText(cancelText);
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
         * 取消按钮文字
         */
        private String cancelText;
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

        public Builder cancelText(String cancelText) {
            this.cancelText = cancelText;
            return this;
        }

        public Builder submitText(String submitText) {
            this.submitText = submitText;
            return this;
        }


        public SimStudentDialog build() {
            if (context == null) {
                return null;
            }
            SimStudentDialog simDialog = new SimStudentDialog(context);
            simDialog.setTitle(title);
            simDialog.setSubmitText(submitText);
            simDialog.setCancelText(cancelText);
            return simDialog;
        }

        public void show() {
            SimStudentDialog dialog = build();
            if (dialog == null) {
                return;
            }
            dialog.showDialog(onBack);
        }
    }

}

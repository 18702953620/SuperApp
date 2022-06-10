package com.shenyuan.superapp.admission.window.dialog;

import android.content.Context;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;

import com.shenyuan.admission.R;
import com.shenyuan.admission.databinding.DialogAddDirBinding;
import com.shenyuan.superapp.base.dialog.BaseDialog;

/**
 * @author ch
 * @date 2021/3/9 15:48
 * desc
 */
public class AddDirDialog extends BaseDialog<DialogAddDirBinding> {
    /**
     * 文件夹名称
     */
    private String dirName;

    public void setDirName(String dirName) {
        this.dirName = dirName;
    }

    public AddDirDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_add_dir;
    }

    @Override
    protected void initView() {
        binding.tvSimCancle.setOnClickListener(v -> onCancel());
        binding.tvSimSubmit.setOnClickListener(v -> {
            String text = binding.etText.getText().toString().trim();
            if (TextUtils.isEmpty(text)) {
                showToast(getContext().getResources().getString(R.string.please_input_dir_name));
                return;
            }
            onConfirm(text);
        });
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
        if (!TextUtils.isEmpty(dirName)) {
            binding.etText.setText(dirName);
            binding.etText.setSelection(dirName.length());
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
        /**
         * 文件夹名称
         */
        private String dirName;

        public AddDirDialog.Builder dirName(String dirName) {
            this.dirName = dirName;
            return this;
        }

        private BaseOnBack onBack;

        public AddDirDialog.Builder onBacklistener(BaseOnBack onBack) {
            this.onBack = onBack;
            return this;
        }

        public AddDirDialog.Builder title(String title) {
            this.title = title;
            return this;
        }

        public AddDirDialog.Builder cancelText(String cancelText) {
            this.cancelText = cancelText;
            return this;
        }

        public AddDirDialog.Builder submitText(String submitText) {
            this.submitText = submitText;
            return this;
        }


        public AddDirDialog build() {
            if (context == null) {
                return null;
            }
            AddDirDialog simDialog = new AddDirDialog(context);
            simDialog.setTitle(title);
            simDialog.setSubmitText(submitText);
            simDialog.setCancelText(cancelText);
            simDialog.setDirName(dirName);
            return simDialog;
        }

        public void show() {
            AddDirDialog dialog = build();
            if (dialog == null) {
                return;
            }
            dialog.showDialog(onBack);
        }
    }
}

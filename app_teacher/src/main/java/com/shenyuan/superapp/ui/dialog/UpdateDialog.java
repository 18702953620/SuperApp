package com.shenyuan.superapp.ui.dialog;

import android.content.Context;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.shenyuan.superapp.R;
import com.shenyuan.superapp.base.dialog.BaseDialog;
import com.shenyuan.superapp.databinding.DialogUpdateBinding;

/**
 * @author ch
 * @date 2021/3/24 9:31
 * desc
 */
public class UpdateDialog extends BaseDialog<DialogUpdateBinding> {


    private String content;

    public void setContent(String content) {
        this.content = content;
    }

    public UpdateDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_update;
    }

    @Override
    public void showDialog(BaseOnBack onBack) {
        if (!TextUtils.isEmpty(content)) {
            binding.tvContent.setText(content);
        }
        super.showDialog(onBack);
    }

    @Override
    protected void initView() {
        binding.tvSubmit.setOnClickListener(v -> {
            if (onBack != null) {
                onBack.onConfirm();
            }
        });
    }
}

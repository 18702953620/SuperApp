package com.shenyuan.superstudent.ui.login;

import android.graphics.Bitmap;
import android.text.TextUtils;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.shenyuan.superapp.base.ARouterPath;
import com.shenyuan.superapp.base.base.BaseStuActivity;
import com.shenyuan.superapp.base.utils.AesEncryptUtil;
import com.shenyuan.superapp.base.utils.StrUtils;
import com.shenyuan.superstudent.R;
import com.shenyuan.superstudent.api.presenter.PasswordPresenter;
import com.shenyuan.superstudent.api.view.PasswordView;
import com.shenyuan.superstudent.databinding.AcForgotPasswordResetBinding;

import java.util.HashMap;

/**
 * @author ch
 * @date 2021/1/14-13:05
 * desc 重置密码
 */
@Route(path = ARouterPath.AppStudent.APP_STUDENT_FORGOT_PASSWORD_RESET)
public class ForgotPasswordResetActivity extends BaseStuActivity<AcForgotPasswordResetBinding, PasswordPresenter> implements PasswordView {
    @Autowired
    public String msgVerifyCode;
    @Autowired
    public String msgId;

    @Override
    protected void setStatusBar() {
        setStatusBarColor(R.color.color_f5f5f5);
    }

    @Override
    protected PasswordPresenter createPresenter() {
        return new PasswordPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.ac_forgot_password_reset;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void addListener() {
        binding.etForgotNewPassword.setButtonClickListener(v -> binding.etForgotNewPassword.clear());
        binding.etForgotNewPasswordAgin.setButtonClickListener(v -> binding.etForgotNewPasswordAgin.clear());
        binding.tvForgetSubmit.setOnClickListener(v -> {
            if (TextUtils.isEmpty(msgId)) {
                showToast(getString(R.string.forget_error_sms_code));
                return;
            }
            if (TextUtils.isEmpty(msgVerifyCode)) {
                showToast(getString(R.string.forget_error_sms_code));
                return;
            }
            if (TextUtils.isEmpty(binding.etForgotNewPassword.getText())) {
                showToast(getString(R.string.forget_new_pwd_not_empty));
                return;
            }
            if (!StrUtils.checkPwd(binding.etForgotNewPassword.getText())) {
                showToast(getString(R.string.pass_new_not_support));
                return;
            }
            if (TextUtils.isEmpty(binding.etForgotNewPassword.getText())) {
                showToast(getString(R.string.forget_new_pwd_agin_not_empty));
                return;
            }
            if (!binding.etForgotNewPassword.getText().equals(binding.etForgotNewPasswordAgin.getText())) {
                showToast(getString(R.string.pass_new_not_equals_agin));
                return;
            }

            HashMap<String, Object> map = new HashMap<>();
            map.put("msgId", msgId);
            map.put("msgVerifyCode", msgVerifyCode);
            map.put("isStuApp", 1);
            map.put("pwd", AesEncryptUtil.encrypt(binding.etForgotNewPassword.getText()));
            map.put("pwdConfirm", AesEncryptUtil.encrypt(binding.etForgotNewPassword.getText()));

            presenter.resetPwd(map);
        });

    }

    @Override
    public void onModifyPwd(String o) {

    }

    @Override
    public void onVerify(Bitmap myBitmap, String codeId) {

    }

    @Override
    public void onVerifyCode(String o) {

    }

    @Override
    public void onSmsCode(String o) {

    }

    @Override
    public void onVerifySmsCode(String o) {

    }

    @Override
    public void onResetPwd(String o) {
        showToast(getString(R.string.succ_edit));
        finish();
    }
}

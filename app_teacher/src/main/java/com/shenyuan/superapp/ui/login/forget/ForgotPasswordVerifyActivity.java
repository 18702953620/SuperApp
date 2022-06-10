package com.shenyuan.superapp.ui.login.forget;

import android.graphics.Bitmap;
import android.text.TextUtils;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.shenyuan.superapp.R;
import com.shenyuan.superapp.api.presenter.PasswordPresenter;
import com.shenyuan.superapp.api.view.PasswordView;
import com.shenyuan.superapp.base.base.BaseActivity;
import com.shenyuan.superapp.base.utils.ParseUtils;
import com.shenyuan.superapp.base.ARouterPath;
import com.shenyuan.superapp.databinding.AcForgotPasswordVerifyBinding;

/**
 * @author ch
 * @date 2021/1/14-9:48
 * desc 验证码
 */
@Route(path = ARouterPath.AppTeacher.APP_FORGOT_PASSWORD_VERIFY)
public class ForgotPasswordVerifyActivity extends BaseActivity<AcForgotPasswordVerifyBinding, PasswordPresenter> implements PasswordView {
    @Autowired
    public String tel;

    private String codeId;

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
        return R.layout.ac_forgot_password_verify;
    }

    @Override
    protected void initView() {
        binding.tvVerifyTel.setText(ParseUtils.getPhone(tel));
        presenter.getVerify();
    }

    @Override
    protected void addListener() {
        binding.tvForgetHotlineNext.setOnClickListener(v -> {
            if (TextUtils.isEmpty(binding.etVerifyCode.getText())) {
                showToast(getString(R.string.forget_please_input_verify_code));
                return;
            }
            if (TextUtils.isEmpty(codeId)) {
                showToast(getString(R.string.forget_verify_code_invalid));
                return;
            }

            presenter.verifyCode(codeId, binding.etVerifyCode.getText());
        });
        //刷新验证码
        binding.tvRefresh.setOnClickListener(v -> {
            binding.etVerifyCode.clear();
            presenter.getVerify();
        });
    }

    @Override
    public void onModifyPwd(String o) {

    }

    @Override
    public void onVerify(Bitmap myBitmap, String codeId) {
        this.codeId = codeId;
        if (myBitmap != null) {
            binding.ivVerifyCode.setImageBitmap(myBitmap);
        }
    }

    @Override
    public void onVerifyCode(String o) {
        ARouter.getInstance().build(ARouterPath.AppTeacher.APP_FORGOT_PASSWORD_MESSAGE)
                .withString("tel", tel).navigation();
        finish();
    }

    @Override
    public void onSmsCode(String o) {

    }

    @Override
    public void onVerifySmsCode(String o) {

    }

    @Override
    public void onResetPwd(String o) {

    }
}

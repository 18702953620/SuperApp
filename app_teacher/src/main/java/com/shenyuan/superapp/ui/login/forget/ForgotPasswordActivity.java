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
import com.shenyuan.superapp.databinding.AcForgotPasswordBinding;

/**
 * @author ch
 * @date 2021/1/13-10:48
 * desc 找回密码
 */
@Route(path = ARouterPath.AppTeacher.APP_FORGOT_PASSWORD)
public class ForgotPasswordActivity extends BaseActivity<AcForgotPasswordBinding, PasswordPresenter> implements PasswordView {

    @Autowired
    public String tel;

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
        return R.layout.ac_forgot_password;
    }

    @Override
    protected void initView() {
        binding.tvForgetTel.setText(String.format(getString(R.string.your_tel), ParseUtils.getPhone(tel)));
    }

    @Override
    protected void addListener() {
        binding.tvForgetCant.setOnClickListener(v ->
                ARouter.getInstance().build(ARouterPath.AppTeacher.APP_FORGOT_PASSWORD_HOTLINE).navigation());
        binding.tvForgetSure.setOnClickListener(v -> {
            if (TextUtils.isEmpty(tel)) {
                showToast(getString(R.string.forget_tel_not_empty));
                return;
            }

            ARouter.getInstance().build(ARouterPath.AppTeacher.APP_FORGOT_PASSWORD_VERIFY)
                    .withString("tel", tel).navigation();
            finish();
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

    }

}

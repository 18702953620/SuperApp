package com.shenyuan.superstudent.ui.login;

import android.content.Intent;
import android.net.Uri;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.shenyuan.superapp.base.ARouterPath;
import com.shenyuan.superapp.base.api.BasePresenter;
import com.shenyuan.superapp.base.base.BaseStuActivity;
import com.shenyuan.superstudent.R;
import com.shenyuan.superstudent.databinding.AcForgotPasswordHotlineBinding;

/**
 * @author ch
 * @date 2021/1/14-9:35
 * desc 拨打热线
 */
@Route(path = ARouterPath.AppStudent.APP_STUDENT_FORGOT_PASSWORD_HOTLINE)
public class ForgotPasswordHotlineActivity extends BaseStuActivity<AcForgotPasswordHotlineBinding, BasePresenter> {
    @Override
    protected void setStatusBar() {
        setStatusBarColor(R.color.color_f5f5f5);
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.ac_forgot_password_hotline;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void addListener() {
        binding.tvForgetHotlineCant.setOnClickListener(v -> finish());
        binding.tvForgetHotlineSure.setOnClickListener(v -> toTel());
        binding.tvForgetTel.setOnClickListener(v -> toTel());
    }

    private void toTel() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_DIAL);
        String tel = getString(R.string.hotline);
        intent.setData(Uri.parse("tel:" + tel));
        startActivity(intent);
    }
}

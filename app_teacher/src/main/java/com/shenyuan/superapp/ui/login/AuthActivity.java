package com.shenyuan.superapp.ui.login;

import android.text.TextUtils;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.shenyuan.superapp.R;
import com.shenyuan.superapp.base.base.BaseActivity;
import com.shenyuan.superapp.base.ARouterPath;
import com.shenyuan.superapp.databinding.AcAuthBinding;
import com.shenyuan.superapp.api.presenter.AuthPresenter;
import com.shenyuan.superapp.api.view.AuthView;

/**
 * @author ch
 * @date 2021/1/4-15:29
 * desc 授权页
 */
@Route(path = ARouterPath.AppTeacher.APP_AUTH)
public class AuthActivity extends BaseActivity<AcAuthBinding, AuthPresenter> implements AuthView {
    @Autowired
    public String uuid;

    @Override
    protected AuthPresenter createPresenter() {
        return new AuthPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.ac_auth;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void addListener() {
        binding.tvOk.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(uuid)) {
                presenter.loginConfirm(uuid);
            } else {
                showToast(getString(R.string.auth_uuid_is_empty));
            }
        });
        binding.tvCancle.setOnClickListener(v -> finish());
    }

    @Override
    public void onLoginScan(String uuid) {

    }

    @Override
    public void onLoginConfirm() {
        finish();
    }
}

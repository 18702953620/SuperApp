package com.shenyuan.module_mvvm.ui.login;

import com.alibaba.fastjson.JSON;
import com.shenyuan.module_mvvm.R;
import com.shenyuan.module_mvvm.databinding.ActivityLoginBinding;
import com.shenyuan.superapp.base.base.mvvm.BaseVMActivity;

public class LoginActivity extends BaseVMActivity<ActivityLoginBinding, LoginViewModel> {

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected LoginViewModel createViewModel() {
        return getScopeViewModel(LoginViewModel.class);
    }

    @Override
    protected void initView() {


    }

    @Override
    protected void addListener() {
        binding.login.setOnClickListener(v ->
                viewModel.login(binding.username.getText().toString(), binding.password.getText().toString())
                        .observe(this, result -> {
                            if (isSucc(result)) {
                            }
                        }));
    }
}
package com.shenyuan.superapp.common;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.shenyuan.superapp.base.ARouterPath;
import com.shenyuan.superapp.base.api.BasePresenter;
import com.shenyuan.superapp.base.api.TokenHandler;
import com.shenyuan.superapp.base.base.BaseActivity;
import com.shenyuan.superapp.common.databinding.AcNetErrorBinding;

/**
 * @author ch
 * @date 2021/3/15 10:03
 * desc 网络错误
 */

@Route(path = ARouterPath.Common.COMMON_NET_ERROR)
public class NetErrorActivity extends BaseActivity<AcNetErrorBinding, BasePresenter> {
    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.ac_net_error;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void addListener() {
        binding.tvBack.setOnClickListener(v -> {
            TokenHandler.getInstance().resetErrorState();
            onBackPressed();
            setResult(RESULT_OK);
        });
    }
}

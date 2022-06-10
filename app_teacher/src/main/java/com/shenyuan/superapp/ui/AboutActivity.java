package com.shenyuan.superapp.ui;

import android.content.Intent;
import android.net.Uri;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.shenyuan.superapp.R;
import com.shenyuan.superapp.base.ARouterPath;
import com.shenyuan.superapp.base.api.BasePresenter;
import com.shenyuan.superapp.base.api.common.AppConstant;
import com.shenyuan.superapp.base.base.BaseActivity;
import com.shenyuan.superapp.base.utils.AppUtils;
import com.shenyuan.superapp.common.web.WebActivity;
import com.shenyuan.superapp.databinding.AcAboutUsBinding;

/**
 * @author ch
 * @date 2021/3/20 14:49
 * desc 关于我们
 */
@Route(path = ARouterPath.AppTeacher.APP_ABOUT)
public class AboutActivity extends BaseActivity<AcAboutUsBinding, BasePresenter> {
    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.ac_about_us;
    }

    @Override
    protected void initView() {
        binding.tvVersion.setText(String.format("Version %s", AppUtils.getVersionName(context)));

    }

    @Override
    protected void addListener() {
        binding.tvAgreement.setOnClickListener(v -> WebActivity.loadUrl(context, AppConstant.SERVICE_AGREEMENT_URL));
        binding.tvPrivacy.setOnClickListener(v -> WebActivity.loadUrl(context, AppConstant.PRIVACY_POLICY_URL));

        binding.tvTel.setOnClickListener(v -> toTel());

    }

    private void toTel() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_DIAL);
        String tel = getString(R.string.hotline);
        intent.setData(Uri.parse("tel:" + tel));
        startActivity(intent);
    }
}

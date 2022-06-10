package com.shenyuan.superapp.ui.data;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.shenyuan.superapp.R;
import com.shenyuan.superapp.base.ARouterPath;
import com.shenyuan.superapp.base.api.BasePresenter;
import com.shenyuan.superapp.base.base.BaseActivity;
import com.shenyuan.superapp.databinding.AcAdmissionDataBinding;

/**
 * @author ch
 * @date 2021/5/24 9:34
 * @
 */

@Route(path = ARouterPath.AppTeacher.App_DATA_ADMISSION)
public class AdmissionDataActivity extends BaseActivity<AcAdmissionDataBinding, BasePresenter> {

    private WebFragment webFragment;
    private ProvinceFragment provinceFragment;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.ac_admission_data;
    }

    @Override
    protected void setStatusBar() {
        setStatusBarColorLight(R.color.color_000420);
    }

    @Override
    protected void initView() {
        webFragment = new WebFragment();
        provinceFragment = new ProvinceFragment();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fl_admission, webFragment)
                .add(R.id.fl_admission, provinceFragment)
                .show(webFragment).hide(provinceFragment).commit();
    }

    @Override
    protected void addListener() {
        binding.rbAdmission.setOnClickListener(v -> getSupportFragmentManager().beginTransaction()
                .show(webFragment).hide(provinceFragment).commit());
        binding.rbProvince.setOnClickListener(v -> {
            getSupportFragmentManager().beginTransaction()
                    .show(provinceFragment).hide(webFragment).commit();
            provinceFragment.setUserVisibleHint(true);
        });

        binding.llBack.setOnClickListener(v -> onBackPressed());
    }

}

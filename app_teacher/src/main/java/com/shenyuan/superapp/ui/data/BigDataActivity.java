package com.shenyuan.superapp.ui.data;

import android.os.Handler;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.shenyuan.superapp.R;
import com.shenyuan.superapp.base.ARouterPath;
import com.shenyuan.superapp.base.api.BasePresenter;
import com.shenyuan.superapp.base.base.BaseStuActivity;
import com.shenyuan.superapp.base.utils.DensityUtils;
import com.shenyuan.superapp.databinding.AcBigDataBinding;

/**
 * @author ch
 * @date 2021/5/26 16:10
 * @ 大数据
 */
@Route(path = ARouterPath.AppTeacher.APP_BIG_DATA)
public class BigDataActivity extends BaseStuActivity<AcBigDataBinding, BasePresenter> {
    private RotateAnimation rotateAnimation;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void setStatusBar() {
        setStatusBarColorLight(R.color.color_000420);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.ac_big_data;
    }

    @Override
    protected void initView() {
        new Handler().postDelayed(() -> {
            if (rotateAnimation == null) {
                rotateAnimation = new RotateAnimation(0, 720,
                        binding.ivDataStar.getPivotX(),
                        binding.ivDataStar.getPivotY());
                rotateAnimation.setDuration(256000);
                rotateAnimation.setInterpolator(new LinearInterpolator());
                rotateAnimation.setRepeatCount(-1);

            }
            binding.ivDataStar.startAnimation(rotateAnimation);
        }, 500);
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void addListener() {
        binding.ivDataZs.setOnClickListener(v -> ARouterPath.router(ARouterPath.AppTeacher.App_DATA_ADMISSION));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        rotateAnimation.cancel();
    }
}

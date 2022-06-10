package com.shenyuan.superstudent.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.CountDownTimer;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.shenyuan.superapp.base.ARouterPath;
import com.shenyuan.superapp.base.api.BasePresenter;
import com.shenyuan.superapp.base.base.BaseStuActivity;
import com.shenyuan.superapp.base.helper.result.ResultHelper;
import com.shenyuan.superapp.common.bean.AdvertBean;
import com.shenyuan.superapp.common.utils.GlideUtils;
import com.shenyuan.superapp.common.web.WebActivity;
import com.shenyuan.superstudent.R;
import com.shenyuan.superstudent.databinding.AcAdvertBinding;

/**
 * @author ch
 * @date 2021/5/8 9:54
 * desc
 */
@Route(path = ARouterPath.AppStudent.APP_STUDENT_ADVERT)
public class AdvertActivity extends BaseStuActivity<AcAdvertBinding, BasePresenter> {
    private static final long TOTAL_TIME = 4000;
    private CountDownTimer downTimer;

    @Autowired
    public AdvertBean advert;

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.ac_advert;
    }

    @Override
    protected void initView() {
        if (advert != null) {
            GlideUtils.load(context, advert.getPictureUrl(), binding.ivAdvert);
        }

        downTimer = new CountDownTimer(TOTAL_TIME, 1000) {
            @SuppressLint("DefaultLocale")
            @Override
            public void onTick(long millisUntilFinished) {
                binding.tvSkip.setText(String.format("跳过(%d)", millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                ARouterPath.router(ARouterPath.AppStudent.APP_STUDENT_MAIN);
                downTimer.cancel();
                finish();
            }
        };
        downTimer.start();
    }


    @Override
    protected void addListener() {
        binding.tvSkip.setOnClickListener(v -> {
            ARouterPath.router(ARouterPath.AppStudent.APP_STUDENT_MAIN);
            downTimer.cancel();
            finish();
        });

        binding.ivAdvert.setOnClickListener(v -> {
            if (advert != null) {
                downTimer.cancel();
                Intent intent = new Intent(context, WebActivity.class);
                intent.putExtra(WebActivity.LOADURL, advert.getJumpUrl());
                ResultHelper.init(this).startActivityForResult(intent, (resultCode, data) -> {
                    ARouterPath.router(ARouterPath.AppStudent.APP_STUDENT_MAIN);
                    finish();
                });
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (downTimer != null) {
            downTimer.cancel();
        }
    }
}

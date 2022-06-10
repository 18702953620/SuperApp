package com.shenyuan.superapp.ui;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.CountDownTimer;
import android.text.TextUtils;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.shenyuan.superapp.R;
import com.shenyuan.superapp.base.ARouterPath;
import com.shenyuan.superapp.base.api.BasePresenter;
import com.shenyuan.superapp.base.api.common.TokenCommon;
import com.shenyuan.superapp.base.base.BaseStuActivity;
import com.shenyuan.superapp.base.helper.result.ResultHelper;
import com.shenyuan.superapp.common.bean.AdvertBean;
import com.shenyuan.superapp.common.utils.GlideUtils;
import com.shenyuan.superapp.common.web.WebActivity;
import com.shenyuan.superapp.databinding.AcAdvertBinding;

/**
 * @author ch
 * @date 2021/5/8 9:54
 * desc
 */
@Route(path = ARouterPath.AppTeacher.APP_ADVERT)
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
                if (TextUtils.isEmpty(TokenCommon.getToken())) {
                    ARouter.getInstance().build(ARouterPath.AppTeacher.APP_LOGIN).navigation();
                } else {
                    ARouter.getInstance().build(ARouterPath.AppTeacher.APP_MAIN).navigation();
                }
                downTimer.cancel();
                finish();
            }
        };
        downTimer.start();
    }


    @Override
    protected void addListener() {
        binding.tvSkip.setOnClickListener(v -> {
            if (TextUtils.isEmpty(TokenCommon.getToken())) {
                ARouter.getInstance().build(ARouterPath.AppTeacher.APP_LOGIN).navigation();
            } else {
                ARouter.getInstance().build(ARouterPath.AppTeacher.APP_MAIN).navigation();
            }
            downTimer.cancel();
            finish();
        });

        binding.ivAdvert.setOnClickListener(v -> {
            if (advert != null) {
                downTimer.cancel();
                Intent intent = new Intent(context, WebActivity.class);
                intent.putExtra(WebActivity.LOADURL, advert.getJumpUrl());
                ResultHelper.init(this).startActivityForResult(intent, (resultCode, data) -> {
                    if (TextUtils.isEmpty(TokenCommon.getToken())) {
                        ARouter.getInstance().build(ARouterPath.AppTeacher.APP_LOGIN).navigation();
                    } else {
                        ARouter.getInstance().build(ARouterPath.AppTeacher.APP_MAIN).navigation();
                    }
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

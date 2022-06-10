package com.shenyuan.superstudent.ui;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;

import androidx.databinding.ViewDataBinding;

import com.alibaba.android.arouter.launcher.ARouter;
import com.shenyuan.superapp.base.ARouterPath;
import com.shenyuan.superapp.base.api.common.AppConstant;
import com.shenyuan.superapp.base.base.BaseStuActivity;
import com.shenyuan.superapp.common.api.presenter.AdvertPresenter;
import com.shenyuan.superapp.common.bean.AdvertBean;
import com.shenyuan.superapp.common.api.view.AdvertView;

import java.util.List;

/**
 * @author ch
 * @date 2020/12/18-14:35
 * desc 启动页
 */
public class SplashActivity extends BaseStuActivity<ViewDataBinding, AdvertPresenter> implements AdvertView {
    @SuppressLint("HandlerLeak")
    private final Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (advert == null) {
                ARouter.getInstance()
                        .build(ARouterPath.AppStudent.APP_STUDENT_MAIN)
                        .navigation();
            } else {
                ARouter.getInstance()
                        .build(ARouterPath.AppStudent.APP_STUDENT_ADVERT)
                        .withSerializable("advert", advert)
                        .navigation();
            }
            finish();
        }
    };

    private AdvertBean advert;

    @Override
    protected void setStatusBar() {

    }

    @Override
    protected AdvertPresenter createPresenter() {
        return new AdvertPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return 0;
    }

    @Override
    protected void initView() {
        handler.sendEmptyMessageDelayed(0, 2000);
        presenter.getLoadingAdvert(AppConstant.STUDENT_CLIENT_ID);
    }

    @Override
    protected void addListener() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }

    @Override
    public void onLoadingAdvert(List<AdvertBean> o) {
        if (o != null && o.size() > 0) {
            advert = o.get(0);
        }
    }

    @Override
    public void onErrorCode(String code, String msg) {

    }
}

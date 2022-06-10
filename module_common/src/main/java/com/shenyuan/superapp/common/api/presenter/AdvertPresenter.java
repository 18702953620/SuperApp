package com.shenyuan.superapp.common.api.presenter;

import com.shenyuan.superapp.base.api.ApiRetrofit;
import com.shenyuan.superapp.base.api.BasePresenter;
import com.shenyuan.superapp.base.api.BaseSubscriber;
import com.shenyuan.superapp.base.api.common.AppConstant;
import com.shenyuan.superapp.common.api.CommonApiServer;
import com.shenyuan.superapp.common.api.view.AdvertView;
import com.shenyuan.superapp.common.bean.AdvertBean;

import java.util.List;

/**
 * @author ch
 * @date 2021/5/25 10:48
 * @
 */
public class AdvertPresenter extends BasePresenter<AdvertView> {


    private final CommonApiServer appServer;

    public AdvertPresenter(AdvertView advertView) {
        super(advertView);
        appServer = ApiRetrofit.getInstance().getService(CommonApiServer.class);
    }

    /**
     * 启动页广告
     */
    public void getLoadingAdvert(String client) {
        addDisposable(appServer.getLoadingAdvert(client), new BaseSubscriber<List<AdvertBean>>(baseView) {
            @Override
            public void onSuccess(List<AdvertBean> o) {
                baseView.onLoadingAdvert(o);
            }
        });
    }


    /**
     * 启动页广告
     */
    public void getPopupAdvert(String client) {
        addDisposable(appServer.getPopupAdvert(AppConstant.STUDENT_CLIENT_ID), new BaseSubscriber<List<AdvertBean>>(baseView) {
            @Override
            public void onSuccess(List<AdvertBean> o) {
                baseView.onLoadingAdvert(o);
            }
        });
    }

}

package com.shenyuan.superapp.api.presenter;

import com.shenyuan.superapp.api.AppApiServer;
import com.shenyuan.superapp.api.view.BigDataView;
import com.shenyuan.superapp.base.api.ApiRetrofit;
import com.shenyuan.superapp.base.api.BasePresenter;
import com.shenyuan.superapp.base.api.BaseSubscriber;
import com.shenyuan.superapp.bean.ProvinceDataBean;

import java.util.HashMap;
import java.util.List;

/**
 * @author ch
 * @date 2021/5/27 14:10
 * @
 */
public class BigDataPresenter extends BasePresenter<BigDataView> {
    private final AppApiServer appServer;

    public BigDataPresenter(BigDataView bigDataView) {
        super(bigDataView);
        appServer = ApiRetrofit.getInstance().getService(AppApiServer.class);
    }

    /**
     * 学校库占比
     */
    public void getSchoolTypePer() {
        addDisposable(appServer.getSchoolTypePer(), new BaseSubscriber<HashMap<String, String>>(baseView) {
            @Override
            public void onSuccess(HashMap<String, String> o) {
                baseView.onSchoolTypePer(o);
            }
        });
    }

    /**
     * 学校库省份分布
     */
    public void getSchoolProvince() {
        addDisposable(appServer.getSchoolProvince(), new BaseSubscriber<List<ProvinceDataBean>>(baseView) {
            @Override
            public void onSuccess(List<ProvinceDataBean> o) {
                baseView.onProvinceList(o);
            }
        });
    }
}

package com.shenyuan.superapp.common.api.presenter;

import com.shenyuan.superapp.base.api.BasePresenter;
import com.shenyuan.superapp.common.api.CommonApiServer;
import com.shenyuan.superapp.common.bean.RegionBean;
import com.shenyuan.superapp.base.api.ApiRetrofit;
import com.shenyuan.superapp.base.api.BaseSubscriber;
import com.shenyuan.superapp.common.api.view.RegionView;

import java.util.List;

/**
 * @author ch
 * @date 2021/2/18 10:55
 * desc
 */
public class RegionPresenter extends BasePresenter<RegionView> {
    private final CommonApiServer commonApiServer;

    public RegionPresenter(RegionView baseView) {
        super(baseView);
        commonApiServer = ApiRetrofit.getInstance().getService(CommonApiServer.class);
    }

    /**
     * 省列表
     */
    public void getProvinceList(int id) {
        addDisposable(commonApiServer.regionName(id), new BaseSubscriber<List<RegionBean>>(baseView) {
            @Override
            public void onSuccess(List<RegionBean> o) {
                RegionPresenter.this.baseView.onProvinceList(o);
            }
        });
    }
    /**
     * 市列表
     */
    public void getCityList(int id) {
        addDisposable(commonApiServer.regionName(id), new BaseSubscriber<List<RegionBean>>(baseView) {
            @Override
            public void onSuccess(List<RegionBean> o) {
                RegionPresenter.this.baseView.onCityList(o);
            }
        });
    }
    /**
     * 区列表
     */
    public void getAreaList(int id) {
        addDisposable(commonApiServer.regionName(id), new BaseSubscriber<List<RegionBean>>(baseView) {
            @Override
            public void onSuccess(List<RegionBean> o) {
                RegionPresenter.this.baseView.onAreaList(o);
            }
        });
    }
}

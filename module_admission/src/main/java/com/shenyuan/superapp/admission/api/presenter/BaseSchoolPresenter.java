package com.shenyuan.superapp.admission.api.presenter;

import com.shenyuan.superapp.admission.api.SchoolApiServer;
import com.shenyuan.superapp.base.api.ApiRetrofit;
import com.shenyuan.superapp.base.api.BasePresenter;
import com.shenyuan.superapp.base.api.BaseView;

/**
 * @author ch
 * @date 2021/2/24 15:16
 * desc
 */
public class BaseSchoolPresenter<V extends BaseView> extends BasePresenter<V> {
    public final SchoolApiServer schoolApiServer;

    public BaseSchoolPresenter(V baseView) {
        super(baseView);
        schoolApiServer = ApiRetrofit.getInstance().getService(SchoolApiServer.class);
    }
}

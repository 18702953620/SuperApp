package com.shenyuan.superapp.common.api.presenter;

import com.shenyuan.superapp.base.api.ApiRetrofit;
import com.shenyuan.superapp.base.api.BasePresenter;
import com.shenyuan.superapp.base.api.BaseSubscriber;
import com.shenyuan.superapp.common.api.CommonApiServer;
import com.shenyuan.superapp.common.api.view.CommonView;

import java.util.List;

/**
 * @author ch
 * @date 2021/4/27 14:41
 * desc
 */
public class CommonPresenter extends BasePresenter<CommonView> {
    private final CommonApiServer commonApiServer;

    public CommonPresenter(CommonView baseView) {
        super(baseView);
        commonApiServer = ApiRetrofit.getInstance().getService(CommonApiServer.class);
    }

    public void getUserBtn() {
        addDisposable(commonApiServer.getUserBtn("zsxt"), new BaseSubscriber<List<String>>(baseView) {
            @Override
            public void onSuccess(List<String> o) {
                CommonPresenter.this.baseView.onUserBtn(o);
            }
        });
    }
}

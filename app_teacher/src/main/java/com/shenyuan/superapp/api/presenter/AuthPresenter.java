package com.shenyuan.superapp.api.presenter;

import com.shenyuan.superapp.api.AppApiServer;
import com.shenyuan.superapp.base.api.ApiRetrofit;
import com.shenyuan.superapp.base.api.BasePresenter;
import com.shenyuan.superapp.base.api.BaseSubscriber;
import com.shenyuan.superapp.base.api.common.AppConstant;
import com.shenyuan.superapp.base.api.common.TokenCommon;
import com.shenyuan.superapp.api.view.AuthView;
import com.shenyuan.superapp.bean.ExamStudentInfoBean;

import java.util.HashMap;

/**
 * @author ch
 * @date 2021/1/4-15:41
 * desc 授权相关
 */
public class AuthPresenter extends BasePresenter<AuthView> {
    private final AppApiServer appServer;

    public AuthPresenter(AuthView baseView) {
        super(baseView);
        appServer = ApiRetrofit.getInstance().getService(AppApiServer.class);
    }


    /**
     * 扫码
     *
     * @param uuid uuid
     */
    public void loginScan(String uuid) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("uuid", uuid);
        map.put("client_id", AppConstant.CLIENT_ID);
        map.put("client_secret", AppConstant.CLIENT_SECRET);
        addDisposable(appServer.loginScan(map), new BaseSubscriber<String>(baseView) {
            @Override
            public void onSuccess(String o) {
                baseView.onLoginScan(uuid);
            }
        });
    }


    /**
     * 扫码确认
     *
     * @param uuid uuid
     */
    public void loginConfirm(String uuid) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("uuid", uuid);
        map.put("refresh_token", TokenCommon.getRefreshToken());
        addDisposable(appServer.loginConfirm(map), new BaseSubscriber<String>(baseView, true) {
            @Override
            public void onSuccess(String o) {
                baseView.onLoginConfirm();
            }
        });
    }
}

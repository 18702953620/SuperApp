package com.shenyuan.superapp.base.api;


import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.shenyuan.superapp.base.api.common.AppConstant;
import com.shenyuan.superapp.base.api.common.TokenCommon;
import com.shenyuan.superapp.base.utils.LogUtils;

import java.io.IOException;
import java.util.HashMap;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @author ch
 * @date 2020/4/21-10:20
 * desc  BasePresenter
 */
public class BasePresenter<V extends BaseView> {

    private CompositeDisposable compositeDisposable;

    public V baseView;

    protected ApiServer apiServer = ApiRetrofit.getInstance().getApiService();

    public BasePresenter(V v) {
        this.baseView = v;
    }

    /**
     * 是否正在刷新
     */
    private volatile boolean mIsTokenNeedRefresh;

    /**
     * 耗费时间
     */
    private long tokenChangedTime = 0;
    private final static int REFRESH_TOKEN_VALID_TIME = 30;

    /**
     * 解除绑定
     */
    public void detachView() {
        baseView = null;
        removeDisposable();
    }

    /**
     * 添加
     *
     * @param flowable   flowable
     * @param subscriber subscriber
     */
    protected void addDisposable(Flowable<?> flowable, BaseSubscriber subscriber) {

        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }

//        Disposable disposable = flowable.retryWhen((Function<Flowable<Throwable>, Publisher<?>>) tfe ->
//                tfe.flatMap((Function<Throwable, Publisher<?>>) throwable -> {
//                    if (throwable instanceof TokenTimeOutException) {
//                        //token 过期
//                        return refreshTokenWhenTokenInvalid();
//                    }
//                    return Flowable.error(throwable);
//                })).subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribeWith(subscriber);

        Disposable disposable = flowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(subscriber);

        compositeDisposable.add(disposable);
    }


    /**
     * 销毁
     */
    private void removeDisposable() {
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }


    /**
     * Refresh the token when the current token is invalid.
     *
     * @return Observable
     */
//    private Flowable<?> refreshTokenWhenTokenInvalid() {
//        synchronized (BasePresenter.class) {
//            // Have refreshed the token successfully in the valid time.
//            if (System.currentTimeMillis() - tokenChangedTime < REFRESH_TOKEN_VALID_TIME) {
//                mIsTokenNeedRefresh = false;
//                return Flowable.just(true);
//            } else {
//                mIsTokenNeedRefresh = true;
//                // call the refresh token api.
//                HashMap<String, String> map = new HashMap<>();
//                map.put("grant_type", "refresh_token");
//                map.put("client_id", AppConstant.CLIENT_ID);
//                map.put("client_secret", AppConstant.CLIENT_SECRET);
//                map.put("user_type", AppConstant.USER_TYPE);
//                map.put("refresh_token", TokenCommon.getRefreshToken());
//
//                LogUtils.e("refresh_token------" + JSON.toJSONString(map));
//
//                //同步请求
//                retrofit2.Response<HashMap<String, String>> tokenResponse = null;
//                try {
//                    tokenResponse = apiServer.refreshToken(map).execute();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//                LogUtils.e("tokenResponse------" + JSON.toJSONString(tokenResponse));
//
//                if (tokenResponse != null && tokenResponse.body() != null) {
//
//                    if (!TextUtils.isEmpty(tokenResponse.body().get("token"))) {
//                        tokenChangedTime = System.currentTimeMillis();
//                        mIsTokenNeedRefresh = false;
//                        //保存token
//                        TokenCommon.saveToken(tokenResponse.body().get("token"));
//                        TokenCommon.saveRefreshToken(tokenResponse.body().get("refreshToken"));
//                    } else {
//                        LogUtils.e("sendLoginOutMsg------");
//                        TokenHandler.getInstance().sendLoginOutMsg();
//                    }
//                } else {
//                    LogUtils.e("sendLoginOutMsg------");
//                    TokenHandler.getInstance().sendLoginOutMsg();
//                }
//                return Flowable.just(true);
//            }
//        }
//    }
}

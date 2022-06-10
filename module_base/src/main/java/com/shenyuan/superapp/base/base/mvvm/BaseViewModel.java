package com.shenyuan.superapp.base.base.mvvm;

import androidx.lifecycle.ViewModel;

import com.shenyuan.superapp.base.api.ApiRetrofit;
import com.shenyuan.superapp.base.api.ApiServer;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * @author ch
 * @date 2022/6/7-17:55
 * desc
 */
public class BaseViewModel extends ViewModel {
    protected ApiServer apiServer = ApiRetrofit.getInstance().getApiService();

    /**
     * 添加
     *
     * @param flowable   flowable
     * @param subscriber subscriber
     */
    protected BaseLiveData addDisposable(Flowable<?> flowable, BaseVMSubscriber subscriber) {
        flowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(subscriber);
        return subscriber.getLiveData();
    }
}

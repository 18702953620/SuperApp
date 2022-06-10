package com.shenyuan.superapp.common.api.down;

import io.reactivex.subscribers.DisposableSubscriber;

/**
 * @author ch
 * @date 2021/3/10 13:25
 * desc
 */
public abstract class FileDownLoadSubscriber<T> extends DisposableSubscriber<T> {

    @Override
    public void onNext(T t) {
        onDownLoadSuccess(t);
    }

    @Override
    public void onError(Throwable throwable) {
        onDownLoadFail(throwable);
    }

    @Override
    public void onComplete() {

    }

    /**
     * 下载成功的回调
     *
     * @param t t
     */
    public abstract void onDownLoadSuccess(T t);

    /**
     * 下载失败回调
     *
     * @param throwable 异常
     */
    public abstract void onDownLoadFail(Throwable throwable);
}

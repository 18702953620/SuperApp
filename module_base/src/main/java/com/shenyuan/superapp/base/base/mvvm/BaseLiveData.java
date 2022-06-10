package com.shenyuan.superapp.base.base.mvvm;

import android.os.Handler;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

/**
 * @author ch
 * @date 2022/6/7-18:21
 * desc
 */
public class BaseLiveData<T> extends MutableLiveData<T> {

    private Handler handler;


    public BaseLiveData() {
        handler = new Handler();
    }

    @Override
    public void observe(@NonNull LifecycleOwner owner, @NonNull Observer<? super T> observer) {
        super.observe(owner, observer);
    }

    @Override
    public void postValue(T value) {
        super.postValue(value);
    }

    public void postValueDelay(T value) {
        postValueDelay(value, 2000);
    }

    public void postValueDelay(T value, long time) {
        handler.postDelayed(() -> postValue(value), time);
    }
}

package com.shenyuan.superapp.base.base.mvvm;


import com.alibaba.fastjson.JSONException;
import com.shenyuan.superapp.base.api.BaseView;
import com.shenyuan.superapp.base.api.exception.BaseException;
import com.shenyuan.superapp.base.api.exception.TokenInvalidException;
import com.shenyuan.superapp.base.api.exception.UserAccountReplacedException;

import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.text.ParseException;

import io.reactivex.subscribers.DisposableSubscriber;
import retrofit2.HttpException;

/**
 * @author ch
 * 时间： 2019/11/21 14:05
 * 描述：响应体处理
 * 来源：
 */
public class BaseVMSubscriber<T> extends DisposableSubscriber<T> {

    private BaseLiveData<BaseResult<T>> liveData;


    public BaseLiveData<BaseResult<T>> getLiveData() {
        checkNull();
        return liveData;
    }

    /**
     * 是否显示dialog
     */
    private boolean isShowDialog;

    public BaseVMSubscriber(boolean isShowDialog) {
        this.isShowDialog = isShowDialog;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (isShowDialog) {
            postValue(BaseResult.CODE_SHOW_LODING, "显示加载动画");
        }
    }

    @Override
    public void onNext(T t) {
        postValue(BaseResult.CODE_SUCC, "请求成功", t);
    }

    @Override
    public void onError(Throwable e) {
        if (isShowDialog) {
            postValue(BaseResult.CODE_CLOSE_LODING, "隐藏加载动画");
        }
        if (e != null) {
            //先处理自己定义的异常
            if (e instanceof TokenInvalidException) {
                //token 异常  需要退出登录
                //回调到view层 处理 或者根据项目情况处理
                postValue(BaseResult.CODE_LOGIN_OUT, e.getMessage());

            } else {
                if (e instanceof HttpException) {
                    //   HTTP错误
                    postValue(BaseResult.CODE_BAD_NETWORK, BaseException.BAD_NETWORK_MSG);
                } else if (e instanceof ConnectException
                        || e instanceof UnknownHostException) {
                    //   连接错误
                    postValue(BaseResult.CODE_CONNECT_ERROR, BaseException.CONNECT_ERROR_MSG);
                } else if (e instanceof InterruptedIOException) {
                    //  连接超时
                    postValue(BaseResult.CODE_CONNECT_TIMEOUT, BaseException.CONNECT_TIMEOUT_MSG);
                } else if (e instanceof JSONException
                        || e instanceof ParseException) {
                    //  解析错误
                    postValue(BaseResult.CODE_PARSE_ERROR, BaseException.PARSE_ERROR_MSG);
                } else {
                    postValue(BaseResult.CODE_OTHER_ERROR, e.getMessage());
                }
            }
        } else {
            postValue(BaseResult.CODE_OTHER_ERROR, BaseException.OTHER_MSG);
        }
    }

    @Override
    public void onComplete() {
        if (isShowDialog) {
            postValue(BaseResult.CODE_CLOSE_LODING, "隐藏加载动画");
        }
    }


    private void checkNull() {
        if (liveData == null) {
            liveData = new BaseLiveData<>();
        }
    }

    /**
     * @param code code
     * @param msg  msg
     */
    private void postValue(String code, String msg) {
        postValue(code, msg, null);
    }

    /**
     * @param code code
     * @param msg  msg
     * @param t    t
     */
    private void postValue(String code, String msg, T t) {
        checkNull();
        liveData.postValue(new BaseResult<>(code, msg, t));
    }
}

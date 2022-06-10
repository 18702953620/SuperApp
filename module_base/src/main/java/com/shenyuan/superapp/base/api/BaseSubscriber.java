package com.shenyuan.superapp.base.api;


import com.alibaba.fastjson.JSONException;
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
public abstract class BaseSubscriber<T> extends DisposableSubscriber<T> {

    protected BaseView view;
    /**
     * 是否显示dialog
     */
    private boolean isShowDialog;

    public BaseSubscriber(BaseView view) {
        this.view = view;
    }

    public BaseSubscriber(BaseView view, boolean isShowDialog) {
        this.view = view;
        this.isShowDialog = isShowDialog;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (view != null && isShowDialog) {
            view.showLoading();
        }
    }

    @Override
    public void onNext(T t) {
        onSuccess(t);
    }

    @Override
    public void onError(Throwable e) {
        if (view != null && isShowDialog) {
            view.hideLoading();
        }
        BaseException be;

        if (e != null) {
            //先处理自己定义的异常
            if (e instanceof TokenInvalidException) {
                //token 异常  需要退出登录
                if (view != null) {
                    //回调到view层 处理 或者根据项目情况处理
                    view.onErrorCode(BaseException.CODE_LOGIN_OUT, e.getMessage());
                }

            } else if (e instanceof UserAccountReplacedException) {
                //其他终端登录，需要重新登录
                if (view != null) {
                    //回调到view层 处理 或者根据项目情况处理
                    view.onErrorCode(BaseException.USER_ACCOUNT_REPLACED, e.getMessage());
                }
            } else {
                if (e instanceof HttpException) {
                    //   HTTP错误
                    if (view != null) {
                        //回调到view层 处理 或者根据项目情况处理
                        view.onErrorCode(BaseException.BAD_NETWORK_MSG, BaseException.BAD_NETWORK_MSG);
                    }
                } else if (e instanceof ConnectException
                        || e instanceof UnknownHostException) {
                    //   连接错误
                    if (view != null) {
                        //回调到view层 处理 或者根据项目情况处理
                        view.onErrorCode(BaseException.CONNECT_ERROR_MSG, BaseException.CONNECT_ERROR_MSG);
                    }
                } else if (e instanceof InterruptedIOException) {
                    //  连接超时
                    if (view != null) {
                        //回调到view层 处理 或者根据项目情况处理
                        view.onErrorCode(BaseException.CONNECT_TIMEOUT_MSG, BaseException.CONNECT_TIMEOUT_MSG);
                    }
                } else if (e instanceof JSONException
                        || e instanceof ParseException) {
                    //  解析错误
                    if (view != null) {
                        view.showError(BaseException.PARSE_ERROR_MSG);
                    }
                } else {
                    if (view != null) {
                        view.showError(e.getMessage());
                    }
                }
            }
        } else {
            be = new BaseException(BaseException.OTHER_MSG, e);
            if (view != null) {
                view.showError(be.getErrorMsg());
            }
        }
    }

    @Override
    public void onComplete() {
        if (view != null && isShowDialog) {
            view.hideLoading();
        }
    }

    /**
     * 请求成功
     *
     * @param o o
     */
    public abstract void onSuccess(T o);

}

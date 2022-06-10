package com.shenyuan.superapp.base.api.exception;

import java.io.IOException;

/**
 * @author ch
 * 时间： 2019/2/15 0015-上午 10:18
 * 描述：错误码
 * 来源：
 */

public class BaseException extends IOException {

    /**
     * 解析数据失败
     */
    public static final String PARSE_ERROR_MSG = "数据解析失败";
    /**
     * 网络问题
     */
    public static final String BAD_NETWORK_MSG = "网络问题，请稍后再试";
    /**
     * 连接错误
     */
    public static final String CONNECT_ERROR_MSG = "连接错误，请稍后再试";
    /**
     * 连接超时
     */
    public static final String CONNECT_TIMEOUT_MSG = "连接超时，请稍后再试";
    /**
     * 未知错误
     */
    public static final String OTHER_MSG = "未知错误，请稍后再试";
    /**
     * 需要登出
     */
    public static final String CODE_LOGIN_OUT = "CODE_LOGIN_OUT";
    /**
     * 用户账号已在本终端的别处登录，当前账号将被强制下线。
     */
    public static final String USER_ACCOUNT_REPLACED = "USER_ACCOUNT_REPLACED";


    private final String errorMsg;


    public String getErrorMsg() {
        return errorMsg;
    }


    public BaseException(String errorMsg, Throwable cause) {
        super(errorMsg, cause);
        this.errorMsg = errorMsg;
    }


    public BaseException(String message) {
        this.errorMsg = message;
    }
}

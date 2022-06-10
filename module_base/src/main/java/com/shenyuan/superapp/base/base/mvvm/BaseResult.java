package com.shenyuan.superapp.base.base.mvvm;

/**
 * @author ch
 * @date 2022/6/7-18:16
 * desc
 */
public class BaseResult<T> {

    public static final String CODE_SUCC = "A01";
    public static final String CODE_SHOW_LODING = "A02";
    public static final String CODE_CLOSE_LODING = "A03";
    public static final String CODE_LOGIN_OUT = "A04";
    public static final String CODE_BAD_NETWORK = "A05";
    public static final String CODE_CONNECT_ERROR = "A06";
    public static final String CODE_CONNECT_TIMEOUT = "A07";
    public static final String CODE_PARSE_ERROR = "A08";
    public static final String CODE_OTHER_ERROR = "A09";


    public BaseResult() {
    }

    public BaseResult(String code) {
        this.code = code;
    }

    public BaseResult(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public BaseResult(String code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    private String code;
    private String msg;
    private T data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}

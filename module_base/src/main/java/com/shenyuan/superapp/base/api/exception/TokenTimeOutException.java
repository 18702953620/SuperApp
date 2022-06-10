package com.shenyuan.superapp.base.api.exception;

import com.alibaba.fastjson.JSONException;

/**
 * @author ch
 * @date 2020/12/21-16:38
 * desc 过期 需要刷新
 */
public class TokenTimeOutException extends JSONException {
    public TokenTimeOutException(String message) {
        super(message);
    }
}

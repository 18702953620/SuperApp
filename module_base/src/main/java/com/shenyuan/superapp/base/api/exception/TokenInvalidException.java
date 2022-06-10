package com.shenyuan.superapp.base.api.exception;

import com.alibaba.fastjson.JSONException;

/**
 * @author ch
 * @date 2020/12/21-16:38
 * desc 失效 需要登录
 */
public class TokenInvalidException extends JSONException {

    public TokenInvalidException(String message) {
        super(message);
    }
}

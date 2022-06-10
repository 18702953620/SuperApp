package com.shenyuan.superapp.base.api.exception;

import com.alibaba.fastjson.JSONException;

/**
 * @author ch
 * @date 2021/3/31 15:55
 * desc 用户账号已在本终端的别处登录，当前账号将被强制下线。
 */
public class UserAccountReplacedException extends JSONException {
    public UserAccountReplacedException(String message) {
        super(message);
    }
}

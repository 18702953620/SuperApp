package com.shenyuan.superapp.base.api.common;

import android.text.TextUtils;

import com.tencent.mmkv.MMKV;

/**
 * @author ch
 * @date 2020/12/18-16:36
 * desc token保存
 */
public class TokenCommon extends BaseCommon {

    /**
     * 保存token
     *
     * @param token token
     */
    public static void saveToken(String token) {
        if (!TextUtils.isEmpty(token)) {
            MMKV kv = MMKV.defaultMMKV();
            if (kv != null) {
                kv.encode(AppConstant.APP_TOKEN, token);
            }
        }
    }

    /**
     * 清空token
     */
    public static void clearToken() {
        MMKV kv = MMKV.defaultMMKV();
        if (kv != null) {
            kv.encode(AppConstant.APP_TOKEN, "");
        }
    }

    /**
     * 获取token
     */
    public static String getToken() {
        MMKV kv = MMKV.defaultMMKV();
        if (kv != null) {
            return kv.decodeString(AppConstant.APP_TOKEN);
        }
        return "";
    }

    /**
     * 获取RefreshToken
     */
    public static String getRefreshToken() {
        MMKV kv = MMKV.defaultMMKV();
        if (kv != null) {
            return kv.decodeString(AppConstant.APP_REFRESH_TOKEN);
        }
        return "";
    }


    /**
     * 保存refreshToken
     *
     * @param token token
     */
    public static void saveRefreshToken(String token) {
        if (!TextUtils.isEmpty(token)) {
            MMKV kv = MMKV.defaultMMKV();
            if (kv != null) {
                kv.encode(AppConstant.APP_REFRESH_TOKEN, token);
            }
        }
    }
}

package com.shenyuan.superapp.base.api.common;

import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.tencent.mmkv.MMKV;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ch
 * @date 2020/12/18-16:26
 * desc 用户信息
 */
public class UserCommon extends BaseCommon {
    /**
     * 添加用户
     *
     * @param name name
     */
    public static void addUser(String name) {
        MMKV kv = MMKV.defaultMMKV();
        String jsonList = getUserString();
        List<String> list;
        if (!TextUtils.isEmpty(jsonList)) {
            list = JSONObject.parseArray(jsonList, String.class);
        } else {
            list = new ArrayList<>();
        }
        if (list == null) {
            list = new ArrayList<>();
        }
        if (!list.contains(name)) {
            list.add(name);
        }
        if (kv != null) {
            kv.encode(AppConstant.APP_USER, JSONObject.toJSONString(list));
        }
    }


    public static String getUserString() {
        MMKV kv = MMKV.defaultMMKV();
        if (kv != null) {
            return kv.decodeString(AppConstant.APP_USER);
        }
        return "";
    }

    /**
     * 获取用户列表
     *
     * @return List<String>
     */
    public static List<String> getUserList() {
        String jsonList = getUserString();
        if (!TextUtils.isEmpty(jsonList)) {
            return JSONObject.parseArray(jsonList, String.class);
        }
        return new ArrayList<>();
    }

    /**
     * 保存所有用户
     *
     * @param list list
     */
    public static void saveUser(List<String> list) {
        MMKV kv = MMKV.defaultMMKV();
        if (kv != null) {
            kv.encode(AppConstant.APP_USER, JSONObject.toJSONString(list));
        }
    }
}

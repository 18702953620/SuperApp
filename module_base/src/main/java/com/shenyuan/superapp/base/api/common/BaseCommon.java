package com.shenyuan.superapp.base.api.common;

import android.annotation.SuppressLint;
import android.content.Context;

/**
 * @author ch
 * @date 2020/12/18-16:37
 * desc
 */
public class BaseCommon {
    @SuppressLint("StaticFieldLeak")
    private static Context appContent;

    public static void init(Context context) {
        appContent = context;
    }

    public static Context getAppContext() {
        return appContent;
    }
}

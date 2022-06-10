package com.shenyuan.superapp.base.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * @author ch
 * 时间： 2019/3/7 0007-上午 10:54
 * 描述：
 * 来源：
 */
public class NetWorkUtils {
    /**
     * 没有网络
     */
    public static final int NO_NETWORK = 0;
    /**
     * wifi
     */
    public static final int WIFI = 1;
    /**
     * 流量
     */
    public static final int MOBILE = 2;

    /**
     * 其他
     */
    public static final int OTHER = 3;

    /**
     * 判断网络状态
     *
     * @param context context
     * @return int
     */

    public static int getNetWork(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager != null) {

            NetworkInfo info = manager.getActiveNetworkInfo();
            if (info == null || !info.isConnected()) {
                return NO_NETWORK;
            } else {
                int type = info.getType();
                if (type == ConnectivityManager.TYPE_WIFI) {
                    return WIFI;
                } else if (type == ConnectivityManager.TYPE_MOBILE) {
                    return MOBILE;
                }
            }
        }

        return OTHER;

    }

    /**
     * 判断网络是否连接
     *
     * @param context context
     * @return boolean
     */
    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null && ni.isConnected();
    }
}

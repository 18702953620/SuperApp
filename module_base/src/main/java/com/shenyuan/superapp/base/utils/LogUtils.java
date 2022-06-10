package com.shenyuan.superapp.base.utils;

import android.util.Log;

/**
 * @author ch
 * @date 2020/12/31-15:58
 * desc Log
 */
public class LogUtils {
    private final static boolean ALL = true;
    private final static boolean I = true;
    private final static boolean D = true;
    private final static boolean E = true;
    private final static boolean V = true;
    private final static boolean W = true;
    private final static String DEFAULT_TAG = "cheng";

    private LogUtils() {
    }

    /**
     * i
     *
     * @param msg msg
     */
    public static void i(String msg) {
        if (ALL && I) {
            i(DEFAULT_TAG, msg, null);
        }
    }

    /**
     * i
     *
     * @param tag tag
     * @param msg msg
     */
    public static void i(String tag, String msg) {
        if (ALL && I) {
            Log.i(tag, msg, null);
        }
    }

    /**
     * i
     *
     * @param tag tag
     * @param msg msg
     */
    public static void i(String tag, String msg, Throwable e) {
        if (ALL && I) {
            Log.i(tag, msg, e);
        }
    }

    /**
     * d
     *
     * @param msg msg
     */
    public static void d(String msg) {
        if (ALL && D) {
            d(DEFAULT_TAG, msg, null);
        }
    }

    /**
     * d
     *
     * @param tag tag
     * @param msg msg
     */
    public static void d(String tag, String msg) {
        if (ALL && D) {
            Log.d(tag, msg, null);
        }
    }

    /**
     * d
     *
     * @param tag tag
     * @param msg msg
     */
    public static void d(String tag, String msg, Throwable e) {
        if (ALL && D) {
            Log.d(tag, msg, e);
        }
    }

    /**
     * e
     *
     * @param msg msg
     */
    public static void e(String msg) {
        if (ALL && E) {
            e(DEFAULT_TAG, msg, null);
        }
    }

    /**
     * e
     *
     * @param tag tag
     * @param msg msg
     */
    public static void e(String tag, String msg) {
        if (ALL && E) {
            Log.e(tag, msg, null);
        }
    }

    /**
     * e
     *
     * @param tag tag
     * @param msg msg
     */
    public static void e(String tag, String msg, Throwable e) {
        if (ALL && E) {
            Log.e(tag, msg, e);
        }
    }

    /**
     * v
     *
     * @param msg msg
     */
    public static void v(String msg) {
        if (ALL && V) {
            v(DEFAULT_TAG, msg, null);
        }
    }

    /**
     * v
     *
     * @param tag tag
     * @param msg msg
     */
    public static void v(String tag, String msg) {
        if (ALL && V) {
            Log.v(tag, msg, null);
        }
    }

    /**
     * v
     *
     * @param tag tag
     * @param msg msg
     */
    public static void v(String tag, String msg, Throwable e) {
        if (ALL && V) {
            Log.v(tag, msg, e);
        }
    }

    /**
     * w
     *
     * @param msg msg
     */
    public static void w(String msg) {
        if (ALL && W) {
            w(DEFAULT_TAG, msg, null);
        }
    }

    /**
     * w
     *
     * @param tag tag
     * @param msg msg
     */
    public static void w(String tag, String msg) {
        if (ALL && W) {
            Log.w(tag, msg, null);
        }
    }

    /**
     * w
     *
     * @param tag tag
     * @param msg msg
     */
    public static void w(String tag, String msg, Throwable e) {
        if (ALL && W) {
            Log.w(tag, msg, e);
        }
    }
}

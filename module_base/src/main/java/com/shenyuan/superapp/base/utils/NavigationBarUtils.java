package com.shenyuan.superapp.base.utils;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import androidx.annotation.NonNull;

import java.lang.reflect.Method;

/**
 * @author ch
 * @date 2021/4/1 17:42
 * desc
 */
public class NavigationBarUtils {

    /**
     * 判断虚拟导航栏是否显示
     *
     * @param context 上下文对象
     * @return true(显示虚拟导航栏)，false(不显示或不支持虚拟导航栏)
     */
    public static boolean checkNavigationBarShow(@NonNull Context context) {
        boolean hasNavigationBar = false;
        Resources rs = context.getResources();
        int id = rs.getIdentifier("config_showNavigationBar", "bool", "android");
        if (id > 0) {
            hasNavigationBar = rs.getBoolean(id);
        }
        try {
            if (isMIUI()) {
                //小米手机在全面屏的情况下，虚拟按键栏不展示，但是判断出来是true ,而且还有高度，就专门换一种方式来判断。
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                    hasNavigationBar = Settings.Global.getInt(context.getContentResolver(), "force_fsg_nav_bar", 0) == 0;
                }
            } else {
                Class systemPropertiesClass = Class.forName("android.os.SystemProperties");
                Method m = systemPropertiesClass.getMethod("get", String.class);
                String navBarOverride = (String) m.invoke(systemPropertiesClass, "qemu.hw.mainkeys");
                //判断是否隐藏了底部虚拟导航
                int navigationBarIsMin = 0;
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    navigationBarIsMin = Settings.System.getInt(context.getContentResolver(),
                            "navigationbar_is_min", 0);
                } else {
                    navigationBarIsMin = Settings.Global.getInt(context.getContentResolver(),
                            "navigationbar_is_min", 0);
                }
                if ("1".equals(navBarOverride) || 1 == navigationBarIsMin) {
                    hasNavigationBar = false;
                } else if ("0".equals(navBarOverride)) {
                    hasNavigationBar = true;
                }
            }
        } catch (Exception e) {
        }
        return hasNavigationBar;
    }

    /**
     * 获取 虚拟按键的高度
     *
     * @param context
     * @return
     */
    public static int getNavigationBarHeight(Context context) {
        if (checkNavigationBarShow(context)) {
            int totalHeight = getDpi(context);
            int contentHeight = getScreenHeight(context);
            return totalHeight - contentHeight - getStatusBarHeight(context);
        } else {
            return 0;
        }
    }

    /**
     * 获取屏幕原始尺寸高度，包括虚拟功能键高度
     */
    public static int getDpi(Context context) {
        int dpi = 0;
        WindowManager windowManager = (WindowManager)
                context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        @SuppressWarnings("rawtypes")
        Class c;
        try {
            c = Class.forName("android.view.Display");
            @SuppressWarnings("unchecked")
            Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
            method.invoke(display, displayMetrics);
            dpi = displayMetrics.heightPixels;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dpi;
    }

    /**
     * 获取屏幕高度 不包含虚拟按键
     */
    public static int getScreenHeight(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.heightPixels;
    }

    public static int getStatusBarHeight(Context context) {
        int height = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            height = context.getResources().getDimensionPixelSize(resourceId);
        }
        return height;
    }

    /**
     * 判断是不是小米手机
     *
     * @return
     */
    public static boolean isMIUI() {
        String manufacturer = Build.MANUFACTURER;
        // 这个字符串可以自己定义,例如判断华为就填写huawei,魅族就填写meizu
        return "xiaomi".equalsIgnoreCase(manufacturer);
    }
}

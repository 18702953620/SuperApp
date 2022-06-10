package com.shenyuan.superapp.base.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

/**
 * @author ch
 * @date 2021/1/12-14:10
 * desc
 */
public class PopUtils {
    /**
     * 获取 PopupWindow
     *
     * @param view       view
     * @param heightFull 是否竖行全屏
     * @return PopupWindow
     */
    public static PopupWindow getPopupWindow(Context context, View view, boolean heightFull) {
        return getPopupWindow(context, view, true, heightFull);
    }


    /**
     * 获取 PopupWindow
     *
     * @param view       view
     * @param widthFull  是否全屏
     * @param heightFull 是否全屏
     * @return PopupWindow
     */
    public static PopupWindow getPopupWindow(Context context, View view, boolean widthFull, boolean heightFull) {
        return getPopupWindow(context, view, false, widthFull, heightFull);
    }

    /**
     * 获取 PopupWindow
     *
     * @param view       view
     * @param widthFull  是否全屏
     * @param heightFull 是否全屏
     * @return PopupWindow
     */
    public static PopupWindow getPopupWindow(Context context, View view, boolean istransparent, boolean widthFull, boolean heightFull) {
        PopupWindow window = new PopupWindow(context);
        window.setContentView(view);
        ColorDrawable dw;
        if (heightFull) {
            if (widthFull) {
                window.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            } else {
                window.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
            }

            // 设置SelectPicPopupWindow弹出窗体的高
            window.setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
            dw = new ColorDrawable(Color.parseColor("#80000000"));
        } else {
            if (widthFull) {
                window.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            } else {
                window.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
            }
            // 设置SelectPicPopupWindow弹出窗体的高
            window.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
            if (istransparent) {
                dw = new ColorDrawable(Color.parseColor("#00000000"));
            } else {
                dw = new ColorDrawable(Color.parseColor("#ffffffff"));
            }
        }
        // 设置SelectPicPopupWindow弹出窗体可点击
        window.setFocusable(true);
        window.setBackgroundDrawable(dw);
        // 实例化一个ColorDrawable颜色为半透明
        window.setOutsideTouchable(false);

        if (Build.VERSION.SDK_INT > 21) {
            //覆盖状态栏
            window.setClippingEnabled(false);
        }
        return window;
    }


    /**
     * 获取 透明 PopupWindow
     *
     * @param view view
     * @return PopupWindow
     */
    public static PopupWindow getTransparentWindow(Context context, View view) {
        PopupWindow window = new PopupWindow(context);
        window.setContentView(view);
        ColorDrawable dw;
        window.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        window.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        dw = new ColorDrawable(Color.parseColor("#00000000"));
        // 设置SelectPicPopupWindow弹出窗体可点击
        window.setFocusable(true);
        window.setBackgroundDrawable(dw);
        // 实例化一个ColorDrawable颜色为半透明
        window.setOutsideTouchable(false);

        view.setOnClickListener(v -> window.dismiss());
        return window;
    }

    /**
     * 7.1 以上 阴影显示问题
     *
     * @param activity    activity
     * @param popupWindow popupWindow
     * @param anchor      anchor
     */
    public static void showAsDropDown(Activity activity, PopupWindow popupWindow, View anchor) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            // Android 7.x中,PopupWindow高度为match_parent时,会出现兼容性问题,需要处理兼容性
            int[] location = new int[2];
            anchor.getLocationOnScreen(location);
            int y = location[1] + anchor.getHeight();
            // Android 7.1中,PopupWindow高度为match_parent时,会占据所有屏幕
            DisplayMetrics outMetrics = new DisplayMetrics();
            activity.getWindowManager().getDefaultDisplay().getMetrics(outMetrics);
            int screenHeight = outMetrics.heightPixels;
            if (!isNavigationBarShown(activity)) {
                //全面屏（不显示虚拟键） 需要加上虚拟键的高度
                popupWindow.setHeight(screenHeight - y);
            } else {
                popupWindow.setHeight(screenHeight - y + anchor.getHeight());
            }

            popupWindow.showAtLocation(anchor, Gravity.NO_GRAVITY, 0, y);
        } else {
            popupWindow.showAsDropDown(anchor);
        }

    }

    /**
     * 非全面屏下 虚拟键高度(无论是否隐藏)
     *
     * @param context context
     * @return
     */
    public static int getNavigationBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    /**
     * 非全面屏下 虚拟按键是否打开
     *
     * @param activity activity
     * @return
     */
    public static boolean isNavigationBarShown(Activity activity) {
        //虚拟键的view,为空或者不可见时是隐藏状态
        View view = activity.findViewById(android.R.id.navigationBarBackground);
        if (view == null) {
            return false;
        }
        int visible = view.getVisibility();
        return visible != View.GONE && visible != View.INVISIBLE;
    }
}

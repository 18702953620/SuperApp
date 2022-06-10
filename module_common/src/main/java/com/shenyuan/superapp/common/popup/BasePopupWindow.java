package com.shenyuan.superapp.common.popup;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.shenyuan.superapp.base.api.exception.BaseException;
import com.shenyuan.superapp.base.api.BasePresenter;
import com.shenyuan.superapp.base.api.BaseView;


/**
 * @author ch
 * @date 2021/2/5 16:06
 * desc
 */
public abstract class BasePopupWindow<B extends ViewDataBinding, P extends BasePresenter> extends PopupWindow implements BaseView {
    /**
     * 是否透明
     */
    private boolean isTransparent;
    /**
     * 是否满屏
     */
    private boolean widthFull;
    /**
     * 是否满屏
     */
    private final boolean heightFull;

    protected Context context;

    /**
     * binding
     */
    protected B binding;

    /**
     * presenter
     */
    protected P presenter;

    /**
     * 布局
     *
     * @return id
     */
    protected abstract int getLayoutId();

    /**
     * 初始化view
     */
    protected abstract void initView();

    /**
     * 添加事件
     */
    protected abstract void addListener();

    /**
     * 创建 Presenter
     *
     * @return P
     */
    protected abstract P createPresenter();

    public BasePopupWindow(Context context, boolean heightFull) {
        super(context);
        this.context = context;
        this.heightFull = heightFull;
        init();
    }

    public BasePopupWindow(Context context, boolean widthFull, boolean heightFull) {
        super(context);
        this.context = context;
        this.widthFull = widthFull;
        this.heightFull = heightFull;
        init();
    }

    public BasePopupWindow(Context context, boolean isTransparent, boolean widthFull, boolean heightFull) {
        super(context);
        this.context = context;
        this.isTransparent = isTransparent;
        this.widthFull = widthFull;
        this.heightFull = heightFull;
        init();
    }

    private void init() {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), getLayoutId(), null, false);
        setContentView(binding.getRoot());
        ColorDrawable dw;
        if (heightFull) {
            if (widthFull) {
                setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            } else {
                setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
            }

            // 设置SelectPicPopupWindow弹出窗体的高
            setHeight(ViewGroup.LayoutParams.MATCH_PARENT);
            dw = new ColorDrawable(Color.parseColor("#80000000"));
        } else {
            if (widthFull) {
                setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            } else {
                setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
            }
            // 设置SelectPicPopupWindow弹出窗体的高
            setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
            if (isTransparent) {
                dw = new ColorDrawable(Color.parseColor("#00000000"));
            } else {
                dw = new ColorDrawable(Color.parseColor("#ffffffff"));
            }
        }
        // 设置SelectPicPopupWindow弹出窗体可点击
        setFocusable(true);
        setBackgroundDrawable(dw);
        // 实例化一个ColorDrawable颜色为半透明
        setOutsideTouchable(false);

        if (Build.VERSION.SDK_INT > 21) {
            //覆盖状态栏
            setClippingEnabled(false);
        }
        presenter = createPresenter();

        initView();

        addListener();


        binding.getRoot().setOnClickListener(v -> dismiss());
    }

    /**
     * 7.1 以上 阴影显示问题
     *
     * @param activity activity
     * @param anchor   anchor
     */
    public void showFullAsDropDown(Activity activity, View anchor) {
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
                setHeight(screenHeight - y);
            } else {
                setHeight(screenHeight - y + anchor.getHeight());
            }

            showAtLocation(anchor, Gravity.NO_GRAVITY, 0, y);
        } else {
            showAsDropDown(anchor);
        }
    }

    /**
     * 非全面屏下 虚拟按键是否打开
     *
     * @param activity activity
     * @return boolean
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static boolean isNavigationBarShown(Activity activity) {
        //虚拟键的view,为空或者不可见时是隐藏状态
        View view = activity.findViewById(android.R.id.navigationBarBackground);
        if (view == null) {
            return false;
        }
        int visible = view.getVisibility();
        return visible != View.GONE && visible != View.INVISIBLE;
    }

    @Override
    public void showLoading() {
    }


    @Override
    public void hideLoading() {
    }


    @Override
    public void showError(String msg) {
        showToast(msg);
    }

    @Override
    public void onErrorCode(String code, String msg) {
        if (BaseException.CODE_LOGIN_OUT.equals(code)) {
            //登出
            showToast("退出登录");
        } else {
            showToast(msg);
        }
    }


    /**
     * @param s s
     */
    public void showToast(String s) {
        Toast toast = Toast.makeText(context, s, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    /**
     * 获得TextView 的文本
     *
     * @param tv TextView
     * @return String
     */
    public String getTv(TextView tv) {
        return tv == null ? "" : tv.getText().toString().trim();
    }

    /**
     * @param color color
     * @return int
     */
    public int getValuesColor(int color) {
        return context.getResources().getColor(color);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    public Drawable getDrawableRes(int res) {
        return context.getResources().getDrawable(res);
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }
}

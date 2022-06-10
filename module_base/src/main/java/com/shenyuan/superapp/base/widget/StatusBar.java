package com.shenyuan.superapp.base.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * @author ch
 * @date 2020/4/21-10:20
 * desc 状态栏
 */
public class StatusBar extends View {

    public StatusBar(Context context) {
        this(context, null);
    }

    public StatusBar(Context context,
                     @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StatusBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int height = 0;
        int resourceId = this.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            height = this.getResources().getDimensionPixelSize(resourceId);
        }
        setMeasuredDimension(getMeasuredWidth(), height);
    }
}

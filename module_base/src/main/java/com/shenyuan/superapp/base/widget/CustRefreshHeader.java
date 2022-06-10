package com.shenyuan.superapp.base.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshKernel;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.RefreshState;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.shenyuan.superapp.base.R;
import com.shenyuan.superapp.base.utils.DensityUtils;

/**
 * @author ch
 * @date 2021/1/15-17:01
 * desc
 */
public class CustRefreshHeader extends LinearLayout implements RefreshHeader {
    private ImageView ivBaseRefresh;
    private RotateAnimation rotateAnimation;

    public CustRefreshHeader(Context context) {
        super(context);
        initHead(context);
    }

    public CustRefreshHeader(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initHead(context);
    }

    public CustRefreshHeader(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initHead(context);
    }

    private void initHead(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.base_refresh_head, this);
        ivBaseRefresh = view.findViewById(R.id.iv_base_refresh);

        rotateAnimation = new RotateAnimation(0, 360 * 2,
                ivBaseRefresh.getPivotX() + DensityUtils.dp2px(context, 15),
                ivBaseRefresh.getPivotY() + DensityUtils.dp2px(context, 15));
        rotateAnimation.setDuration(500 * 3);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setRepeatCount(-1);
    }

    @NonNull
    @Override
    public View getView() {
        return this;
    }

    @NonNull
    @Override
    public SpinnerStyle getSpinnerStyle() {
        return SpinnerStyle.Translate;
    }

    @Override
    public void setPrimaryColors(int... colors) {

    }

    @Override
    public void onInitialized(@NonNull RefreshKernel kernel, int height, int maxDragHeight) {

    }

    @Override
    public void onMoving(boolean isDragging, float percent, int offset, int height, int maxDragHeight) {
        if (isDragging) {
            //如果上次动画还未结束 就取消
            if (rotateAnimation != null) {
                rotateAnimation.cancel();
            }
            ivBaseRefresh.setRotation(Math.abs(offset));
        } else {
            ivBaseRefresh.startAnimation(rotateAnimation);
        }
    }

    @Override
    public void onReleased(@NonNull RefreshLayout refreshLayout, int height, int maxDragHeight) {

    }

    @Override
    public void onStartAnimator(@NonNull RefreshLayout refreshLayout, int height, int maxDragHeight) {

    }

    @Override
    public int onFinish(@NonNull RefreshLayout refreshLayout, boolean success) {
        return 100;
    }

    @Override
    public void onHorizontalDrag(float percentX, int offsetX, int offsetMax) {

    }

    @Override
    public boolean isSupportHorizontalDrag() {
        return false;
    }

    @Override
    public void onStateChanged(@NonNull RefreshLayout refreshLayout, @NonNull RefreshState oldState, @NonNull RefreshState newState) {

        switch (newState) {
            //下拉
            case PullDownToRefresh:
                break;
            //松开刷新
            case ReleaseToRefresh:
                break;
            //刷新中
            case Refreshing:
                break;

        }

    }
}

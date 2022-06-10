package com.shenyuan.superapp.base.widget.recy.sticky;


import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.view.ViewCompat;

/**
 * @author ch
 * @date 2020/4/21-10:20
 * desc 悬停头部
 */
public class StickyHeadContainer extends ViewGroup {

    private int mOffset;
    private int mLastOffset = Integer.MIN_VALUE;
    private int mLastStickyHeadPosition = Integer.MIN_VALUE;

    private int mLeft;
    private int mRight;
    private int mTop;
    private int mBottom;

    private DataCallback mDataCallback;

    public StickyHeadContainer(Context context) {
        this(context, null);
    }

    public StickyHeadContainer(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StickyHeadContainer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int desireHeight;
        int desireWidth;

        int count = getChildCount();

        if (count != 1) {
            throw new IllegalArgumentException("只允许容器添加1个子View！");
        }

        final View child = getChildAt(0);
        // 测量子元素并考虑外边距
        measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, 0);
        // 获取子元素的布局参数
        final MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
        // 计算子元素宽度，取子控件最大宽度
        desireWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
        // 计算子元素高度
        desireHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;

        // 考虑父容器内边距
        desireWidth += getPaddingLeft() + getPaddingRight();
        desireHeight += getPaddingTop() + getPaddingBottom();
        // 尝试比较建议最小值和期望值的大小并取大值
        desireWidth = Math.max(desireWidth, getSuggestedMinimumWidth());
        desireHeight = Math.max(desireHeight, getSuggestedMinimumHeight());
        // 设置最终测量值
        setMeasuredDimension(resolveSize(desireWidth, widthMeasureSpec), resolveSize(desireHeight, heightMeasureSpec));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        final View child = getChildAt(0);
        MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();

        final int paddingLeft = getPaddingLeft();
        final int paddingTop = getPaddingTop();

        mLeft = paddingLeft + lp.leftMargin;
        mRight = child.getMeasuredWidth() + mLeft;

        mTop = paddingTop + lp.topMargin + mOffset;
        mBottom = child.getMeasuredHeight() + mTop;

        child.layout(mLeft, mTop, mRight, mBottom);
    }


    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return super.generateDefaultLayoutParams();
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected boolean checkLayoutParams(LayoutParams p) {
        return p instanceof MarginLayoutParams;
    }

    public void scrollChild(int offset) {
        if (mLastOffset != offset) {
            mOffset = offset;
            ViewCompat.offsetTopAndBottom(getChildAt(0), mOffset - mLastOffset);
        }
        mLastOffset = mOffset;
    }

    protected int getChildHeight() {
        return getChildAt(0).getHeight();
    }

    protected void onDataChange(int stickyHeadPosition) {
        if (mDataCallback != null && mLastStickyHeadPosition != stickyHeadPosition) {
            mDataCallback.onDataChange(stickyHeadPosition);
        }
        mLastStickyHeadPosition = stickyHeadPosition;
    }

    public void reset() {
        mLastStickyHeadPosition = Integer.MIN_VALUE;
    }

    public interface DataCallback {
        /**
         * 数据变更
         *
         * @param pos pos
         */
        void onDataChange(int pos);

    }

    public void setDataCallback(DataCallback dataCallback) {
        mDataCallback = dataCallback;
    }
}

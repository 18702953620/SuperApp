package com.shenyuan.superapp.base.widget.recy;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.chad.library.adapter.base.BaseQuickAdapter;

import static android.widget.LinearLayout.VERTICAL;


/**
 * @author ch
 * @date 2020/4/21-10:20
 * desc 间隔线（grid）
 */
public class SpaceDecoration extends RecyclerView.ItemDecoration {

    private final int space;
    private final int headerCount = -1;
    private final int footerCount = Integer.MAX_VALUE;
    private boolean mPaddingEdgeSide = true;
    private boolean mPaddingStart = true;
    private boolean mPaddingHeaderFooter = false;
    private final ColorDrawable mColorDrawable;

    private final boolean mDrawLastItem = true;
    private final boolean mDrawHeaderFooter = false;


    public SpaceDecoration(int space) {
        this.mColorDrawable = new ColorDrawable(Color.parseColor("#e7e7e7"));
        this.space = space;
    }

    public SpaceDecoration(int space, int color) {
        this.mColorDrawable = new ColorDrawable(color);
        this.space = space;
    }

    public void setPaddingEdgeSide(boolean mPaddingEdgeSide) {
        this.mPaddingEdgeSide = mPaddingEdgeSide;
    }

    public void setPaddingStart(boolean mPaddingStart) {
        this.mPaddingStart = mPaddingStart;
    }

    public void setPaddingHeaderFooter(boolean mPaddingHeaderFooter) {
        this.mPaddingHeaderFooter = mPaddingHeaderFooter;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        int spanCount = 0;
        int orientation = 0;
        int spanIndex = 0;
        int headerCount = 0, footerCount = 0;
        if (parent.getAdapter() instanceof BaseQuickAdapter) {
            headerCount = ((BaseQuickAdapter) parent.getAdapter()).getHeaderLayoutCount();
            footerCount = ((BaseQuickAdapter) parent.getAdapter()).getFooterLayoutCount();
        }

        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof StaggeredGridLayoutManager) {
            orientation = ((StaggeredGridLayoutManager) layoutManager).getOrientation();
            spanCount = ((StaggeredGridLayoutManager) layoutManager).getSpanCount();
            spanIndex = ((StaggeredGridLayoutManager.LayoutParams) view.getLayoutParams()).getSpanIndex();
        } else if (layoutManager instanceof GridLayoutManager) {
            orientation = ((GridLayoutManager) layoutManager).getOrientation();
            spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
            spanIndex = ((GridLayoutManager.LayoutParams) view.getLayoutParams()).getSpanIndex();
        } else if (layoutManager instanceof LinearLayoutManager) {
            orientation = ((LinearLayoutManager) layoutManager).getOrientation();
            spanCount = 1;
            spanIndex = 0;
        }

        /**
         * 普通Item的尺寸
         */
        if ((position >= headerCount && position < parent.getAdapter().getItemCount() - footerCount)) {

            if (orientation == VERTICAL) {
                float expectedWidth = (float) (parent.getWidth() - space * (spanCount + (mPaddingEdgeSide ? 1 : -1))) / spanCount;
                float originWidth = (float) parent.getWidth() / spanCount;
                float expectedX = (mPaddingEdgeSide ? space : 0) + (expectedWidth + space) * spanIndex;
                float originX = originWidth * spanIndex;
                outRect.left = (int) (expectedX - originX);
                outRect.right = (int) (originWidth - outRect.left - expectedWidth);
                if (position - headerCount < spanCount && mPaddingStart) {
                    outRect.top = space;
                }
                outRect.bottom = space;
                return;
            } else {
                float expectedHeight = (float) (parent.getHeight() - space * (spanCount + (mPaddingEdgeSide ? 1 : -1))) / spanCount;
                float originHeight = (float) parent.getHeight() / spanCount;
                float expectedY = (mPaddingEdgeSide ? space : 0) + (expectedHeight + space) * spanIndex;
                float originY = originHeight * spanIndex;
                outRect.bottom = (int) (expectedY - originY);
                outRect.top = (int) (originHeight - outRect.bottom - expectedHeight);
                if (position - headerCount < spanCount && mPaddingStart) {
                    outRect.left = space;
                }
                outRect.right = space;
                return;
            }
        } else if (mPaddingHeaderFooter) {
            if (orientation == VERTICAL) {
                outRect.right = outRect.left = mPaddingEdgeSide ? space : 0;
                outRect.top = mPaddingStart ? space : 0;
            } else {
                outRect.top = outRect.bottom = mPaddingEdgeSide ? space : 0;
                outRect.left = mPaddingStart ? space : 0;
            }
        }

    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {

        if (parent.getAdapter() == null) {
            return;
        }

        int orientation = 0;
        int headerCount = 0, footerCount = 0, dataCount;

        if (parent.getAdapter() instanceof BaseQuickAdapter) {
            headerCount = ((BaseQuickAdapter) parent.getAdapter()).getHeaderLayoutCount();
            footerCount = ((BaseQuickAdapter) parent.getAdapter()).getFooterLayoutCount();
            dataCount = parent.getAdapter().getItemCount();
        } else {
            dataCount = parent.getAdapter().getItemCount();
        }
        int dataStartPosition = headerCount;
        int dataEndPosition = headerCount + dataCount;


        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof StaggeredGridLayoutManager) {
            orientation = ((StaggeredGridLayoutManager) layoutManager).getOrientation();
        } else if (layoutManager instanceof GridLayoutManager) {
            orientation = ((GridLayoutManager) layoutManager).getOrientation();
        } else if (layoutManager instanceof LinearLayoutManager) {
            orientation = ((LinearLayoutManager) layoutManager).getOrientation();
        }
        int start, end;
        if (orientation == OrientationHelper.VERTICAL) {
            start = parent.getPaddingLeft();
            end = parent.getWidth() - parent.getPaddingRight();
        } else {
            start = parent.getPaddingTop();
            end = parent.getHeight() - parent.getPaddingBottom();
        }

        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = parent.getChildAt(i);
            int position = parent.getChildAdapterPosition(child);

            if (position >= dataStartPosition && position < dataEndPosition - 1//数据项除了最后一项
                    || (position == dataEndPosition - 1 && mDrawLastItem)//数据项最后一项
                    || (!(position >= dataStartPosition && position < dataEndPosition) && mDrawHeaderFooter)//header&footer且可绘制
            ) {

                if (orientation == OrientationHelper.VERTICAL) {
                    RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
                    int top = child.getBottom() + params.bottomMargin;
                    int bottom = top;
                    mColorDrawable.setBounds(start, top, end, bottom);
                    mColorDrawable.draw(c);
                } else {
                    RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
                    int left = child.getRight() + params.rightMargin;
                    int right = left;
                    mColorDrawable.setBounds(left, start, right, end);
                    mColorDrawable.draw(c);
                }
            }
        }
    }
}
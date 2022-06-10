package com.shenyuan.superapp.common.widget;

import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Build;

import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.listener.OnItemDragListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

/**
 * @author ch
 * @date 2021/1/20 16:44
 * desc
 */
public class CustItemDragListener implements OnItemDragListener {
    @Override
    public void onItemDragStart(RecyclerView.ViewHolder viewHolder, int pos) {
        final BaseViewHolder holder = ((BaseViewHolder) viewHolder);
        // 开始时，item背景色变化，demo这里使用了一个动画渐变，使得自然
        int startColor = Color.WHITE;
        int endColor = Color.rgb(245, 245, 245);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ValueAnimator v = ValueAnimator.ofArgb(startColor, endColor);
            v.addUpdateListener(animation -> holder.itemView.setBackgroundColor((int) animation.getAnimatedValue()));
            v.setDuration(300);
            v.start();
        }
    }

    @Override
    public void onItemDragMoving(RecyclerView.ViewHolder source, int from, RecyclerView.ViewHolder target, int to) {

    }

    @Override
    public void onItemDragEnd(RecyclerView.ViewHolder viewHolder, int pos) {
        final BaseViewHolder holder = ((BaseViewHolder) viewHolder);
        // 结束时，item背景色变化，demo这里使用了一个动画渐变，使得自然
        int startColor = Color.rgb(245, 245, 245);
        int endColor = Color.WHITE;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ValueAnimator v = ValueAnimator.ofArgb(startColor, endColor);
            v.addUpdateListener(animation -> holder.itemView.setBackgroundColor((int) animation.getAnimatedValue()));
            v.setDuration(300);
            v.start();
        }
    }
}

package com.shenyuan.superapp.base.widget.recy.sticky;

/**
 * @author ch
 * @date 2020/4/21-10:20
 * desc 悬停头部数据变更
 */
public interface OnStickyChangeListener {
    /**
     * 滑动
     *
     * @param offset 距离
     */
    void onScrollable(int offset);

    /**
     * 是否可以滑动
     */
    void onInVisible();
}
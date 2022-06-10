package com.shenyuan.superapp.common.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;
import com.scwang.smartrefresh.layout.header.FalsifyHeader;
import com.scwang.smartrefresh.layout.header.TwoLevelHeader;
import com.shenyuan.superapp.base.widget.HomeRefreshHeader;
import com.shenyuan.superapp.common.R;

/**
 * @author ch
 * @date 2021/4/8 14:53
 * desc
 */
public class HomeRefreshLayout extends SmartRefreshLayout {
    public HomeRefreshLayout(Context context) {
        super(context);
        initHead(context);
    }

    public HomeRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initHead(context);
    }

    public HomeRefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initHead(context);
    }

    @SuppressLint("RestrictedApi")
    private void initHead(Context context) {
        ClassicsFooter footer = new ClassicsFooter(context);
        footer.setBackgroundColor(context.getResources().getColor(R.color.color_f5f5f5));
        footer.setDrawableSize(20);
        HomeRefreshHeader header = new HomeRefreshHeader(context);
        setRefreshHeader(header);
        setRefreshFooter(footer);

        setHeaderMaxDragRate(1);
        setHeaderTriggerRate(0.5f);
    }


    /**
     * 刷新/加载更多完成
     */
    public void finishRefreshAndLoadMore() {
        finishRefresh();
        finishLoadMore();
    }
}

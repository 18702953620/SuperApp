package com.shenyuan.superapp.common.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.shenyuan.superapp.base.widget.HomeRefreshHeader;
import com.shenyuan.superapp.base.widget.HomeStudentRefreshHeader;
import com.shenyuan.superapp.common.R;

/**
 * @author ch
 * @date 2021/4/8 14:53
 * desc
 */
public class HomeStudentRefreshLayout extends SmartRefreshLayout {
    public HomeStudentRefreshLayout(Context context) {
        super(context);
        initHead(context);
    }

    public HomeStudentRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initHead(context);
    }

    public HomeStudentRefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initHead(context);
    }

    @SuppressLint("RestrictedApi")
    private void initHead(Context context) {
        ClassicsFooter footer = new ClassicsFooter(context);
        footer.setBackgroundColor(context.getResources().getColor(R.color.color_f5f5f5));
        footer.setDrawableSize(20);
        HomeStudentRefreshHeader header = new HomeStudentRefreshHeader(context);
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

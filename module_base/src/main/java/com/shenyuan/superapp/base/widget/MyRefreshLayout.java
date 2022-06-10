package com.shenyuan.superapp.base.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;

/**
 * @author ch
 * @date 2020/1/6 17:48
 * desc MyRefreshLayout
 */
public class MyRefreshLayout extends SmartRefreshLayout {

    public MyRefreshLayout(Context context) {
        super(context);
        initHead(context);
    }

    public MyRefreshLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initHead(context);
    }

    public MyRefreshLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initHead(context);

    }

    @SuppressLint("RestrictedApi")
    private void initHead(Context context) {
//        ClassicsHeader header = new ClassicsHeader(context);
        ClassicsFooter footer = new ClassicsFooter(context);
        footer.setDrawableSize(20);
        CustRefreshHeader header = new CustRefreshHeader(context);

        setRefreshHeader(header);
        setRefreshFooter(footer);
    }

    /**
     * 刷新/加载更多完成
     */
    public void finishRefreshAndLoadMore() {
        finishRefresh();
        finishLoadMore();
    }

}

package com.shenyuan.superapp.base.base;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.shenyuan.superapp.base.R;
import com.shenyuan.superapp.base.api.BasePresenter;
import com.shenyuan.superapp.base.databinding.AcBaseListBinding;

/**
 * @author ch
 * @date 2020/4/27-17:14
 * desc 基础 列表
 */
public abstract class BaseListActivity<P extends BasePresenter> extends BaseActivity<AcBaseListBinding, P> {

    /**
     * 生成 adpater
     *
     * @return BaseQuickAdapter
     */
    public abstract BaseQuickAdapter<?, BaseViewHolder> getAdapter();

    /**
     * 刷新
     *
     * @param refreshLayout refreshLayout
     */
    public abstract void refresh(RefreshLayout refreshLayout);

    /**
     * 更多
     *
     * @param refreshLayout refreshLayout
     */
    public abstract void loadMore(RefreshLayout refreshLayout);

    @Override
    protected P createPresenter() {
        return presenter;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.ac_base_list;
    }

    @Override
    protected void initView() {
        setAdapter(getAdapter());
        refresh(binding.srlBaseList);
        binding.rvBaseList.setBackgroundColor(getValuesColor(R.color.color_f8f8f8));
    }

    @Override
    protected void addListener() {
        binding.srlBaseList.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                loadMore(refreshLayout);
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refresh(refreshLayout);
            }
        });
    }

    /**
     * 设置标题
     *
     * @param text text
     */
    public void setTitle(@NonNull String text) {
        if (binding != null) {
            binding.title.setTitle(text);
        }
    }

    /**
     * 设置是否可以刷新 和加载更多
     *
     * @param refresh  refresh
     * @param loadmore loadmore
     */
    public void setRefreshLoadMore(boolean refresh, boolean loadmore) {
        if (binding != null) {
            binding.srlBaseList.setEnableRefresh(refresh);
            binding.srlBaseList.setEnableLoadMore(loadmore);
        }
    }


    /**
     * 设置 adapter
     *
     * @param quickAdapter quickAdapter
     */
    protected void setAdapter(@NonNull BaseQuickAdapter quickAdapter) {
        if (binding != null) {
            binding.rvBaseList.setLayoutManager(new LinearLayoutManager(context));
            binding.rvBaseList.setAdapter(quickAdapter);
        }
    }

    /**
     * 添加 ItemDecoration
     *
     * @param decor decor
     */
    public void addItemDecoration(RecyclerView.ItemDecoration decor) {
        binding.rvBaseList.addItemDecoration(decor);
    }

    /**
     * 获取RecyclerView
     *
     * @return RecyclerView
     */
    public RecyclerView getRecyclerView() {
        return binding.rvBaseList;
    }
}

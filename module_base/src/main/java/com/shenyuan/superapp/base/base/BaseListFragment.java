package com.shenyuan.superapp.base.base;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;
import com.shenyuan.superapp.base.R;
import com.shenyuan.superapp.base.api.BasePresenter;
import com.shenyuan.superapp.base.api.BaseView;
import com.shenyuan.superapp.base.databinding.FmBaseListBinding;


/**
 * @author ch
 * @date 2020/7/1-11:30
 * desc
 */
public class BaseListFragment<P extends BasePresenter> extends BaseFragment<FmBaseListBinding, P> implements BaseView {
    /**
     * 是否加载过数据
     */
    private boolean isLoadData;

    private LibBaseCallback callback;
    /**
     * quickAdapter
     */
    private BaseQuickAdapter quickAdapter;
    /**
     * layoutManager
     */
    private RecyclerView.LayoutManager layoutManager;
    /**
     * decoration
     */
    private RecyclerView.ItemDecoration decoration;
    /**
     * emptyRes
     */
    private int emptyRes;


    public void setCallback(LibBaseCallback callback) {
        this.callback = callback;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fm_base_list;
    }

    @Override
    protected void initView() {
        if (layoutManager != null) {
            binding.rvBase.setLayoutManager(layoutManager);
        } else {
            binding.rvBase.setLayoutManager(new LinearLayoutManager(context));
        }
        if (quickAdapter != null) {
            binding.rvBase.setAdapter(quickAdapter);
            //设置空view
            if (emptyRes != 0) {
                quickAdapter.setEmptyView(getViewByRes(emptyRes));
            }
        }

        if (decoration != null) {
            binding.rvBase.addItemDecoration(decoration);
        }
    }

    @Override
    protected void addListener() {
        binding.mrlBase.setOnRefreshLoadMoreListener(new OnRefreshLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                loadMore();
                if (callback != null) {
                    callback.loadMore();
                }
            }

            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                refresh();
                if (callback != null) {
                    callback.refresh();
                }
            }
        });
    }

    @Override
    protected P createPresenter() {
        return presenter;
    }


    @Override
    protected void loadData() {

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            if (callback != null && !isLoadData) {
                isLoadData = true;
                callback.refresh();
                refresh();
            }
        }
    }

    /**
     * 刷新
     */
    protected void refresh() {

    }

    /**
     * 更多
     */
    protected void loadMore() {

    }

    /**
     * 设置 adapter
     *
     * @param quickAdapter quickAdapter
     */
    public void setAdapter(@NonNull BaseQuickAdapter quickAdapter) {
        this.quickAdapter = quickAdapter;
        if (binding != null) {
            binding.rvBase.setLayoutManager(new LinearLayoutManager(context));
            binding.rvBase.setAdapter(quickAdapter);
        }
    }

    /**
     * 设置 adapter
     *
     * @param quickAdapter quickAdapter
     */
    public void setAdapter(@NonNull RecyclerView.LayoutManager layoutManager, @NonNull BaseQuickAdapter quickAdapter) {
        this.quickAdapter = quickAdapter;
        this.layoutManager = layoutManager;
        if (binding != null) {
            binding.rvBase.setLayoutManager(layoutManager);
            binding.rvBase.setAdapter(quickAdapter);
        }
    }

    /**
     * 设置空view
     *
     * @param res res
     */
    public void setEmptyView(@LayoutRes int res) {
        this.emptyRes = res;
    }

    /**
     * 添加 ItemDecoration
     *
     * @param decor decor
     */
    public void addItemDecoration(RecyclerView.ItemDecoration decor) {
        this.decoration = decor;
        if (binding != null) {
            binding.rvBase.addItemDecoration(decor);
        }
    }

    @Override
    public void showLoading() {

    }


    @Override
    public void hideLoading() {

    }

    @Override
    public void showError(String msg) {

    }

    @Override
    public void onErrorCode(String code, String msg) {

    }


    public interface LibBaseCallback {
        /**
         * 加载更多
         */
        void loadMore();

        /**
         * 刷新
         */
        void refresh();
    }
}

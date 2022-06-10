package com.shenyuan.superapp.ui.fragment;

import android.os.Bundle;

import com.alibaba.android.arouter.launcher.ARouter;
import com.shenyuan.superapp.adapter.NewsAdapter;
import com.shenyuan.superapp.api.presenter.HomePresenter;
import com.shenyuan.superapp.api.view.HomeView;
import com.shenyuan.superapp.base.ARouterPath;
import com.shenyuan.superapp.base.base.BaseListFragment;
import com.shenyuan.superapp.bean.BannerBean;
import com.shenyuan.superapp.bean.MenuBean;
import com.shenyuan.superapp.bean.NewsTypeBean;
import com.shenyuan.superapp.bean.ServiceBean;
import com.shenyuan.superapp.common.bean.NewsBean;

import java.util.HashMap;
import java.util.List;

/**
 * @author ch
 * @date 2021/3/29 13:18
 * desc
 */
public class NewsFragment extends BaseListFragment<HomePresenter> implements HomeView {


    private int type;

    private int page = 1;
    private NewsTypeBean newsTypeBean;
    private NewsAdapter newsAdapter;

    private OnLoadMoreListener loadMoreListener;

    public NewsFragment() {
    }

    public NewsFragment(int type, NewsTypeBean newsTypeBean) {
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        bundle.putSerializable("newsTypeBean", newsTypeBean);
        setArguments(bundle);
    }

    @Override
    protected void loadData() {
        loadNews();
    }

    @Override
    protected void refresh() {
        page = 1;
        loadNews();
    }

    @Override
    protected void loadMore() {
        page++;
        loadNews();
    }

    @Override
    protected void initView() {
        super.initView();

        if (getArguments() != null) {
            type = getArguments().getInt("type");
            newsTypeBean = (NewsTypeBean) getArguments().getSerializable("newsTypeBean");
        }
        newsAdapter = new NewsAdapter();
        setAdapter(newsAdapter);

        newsAdapter.setItemClickListener((bean, view, position) -> {
            if (newsTypeBean == null) {
                return;
            }
            if (type == 1) {
                //公告-浏览次数+1
                ARouter.getInstance().build(ARouterPath.Common.COMMON_WEB).withString("noticeId",
                        String.valueOf(bean.getId()))
                        .navigation();
            } else {
                //资讯-浏览次数+1
                ARouter.getInstance().build(ARouterPath.Common.COMMON_WEB).withString("newsId",
                        String.valueOf(bean.getId())).navigation();
            }
        });
    }

    private void loadNews() {
        if (type == 0) {
            presenter.getNewsList(page, newsTypeBean.getId(), 1);
        } else {
            presenter.getNewsList(page, newsTypeBean.getId(), 0);
        }
    }

    @Override
    protected HomePresenter createPresenter() {
        return new HomePresenter(this);
    }

    @Override
    public void onBannerList(List<BannerBean> o) {

    }

    @Override
    public void onServiceList(List<ServiceBean> o) {

    }

    @Override
    public void onMyServiceList(List<MenuBean> o) {

    }

    @Override
    public void onEditSucc(String o) {

    }

    @Override
    public void onNewsTypeList(List<NewsTypeBean> o) {

    }

    @Override
    public void onNewsList(List<NewsBean> o) {
        if (page == 1) {
            newsAdapter.setNewInstance(o);
        } else {
            newsAdapter.addData(o);
        }
        binding.mrlBase.finishRefreshAndLoadMore();
        if (loadMoreListener != null) {
            loadMoreListener.onLoadMore();
        }
    }

    @Override
    public void onNoticeList(List<NewsBean> o) {

    }

    @Override
    public void onUpdate(HashMap<String, String> url) {

    }


    public void loadMore(OnLoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
        page++;
        loadNews();
    }


    public interface OnLoadMoreListener {
        void onLoadMore();
    }
}

package com.shenyuan.superstudent.ui.fragment;


import android.os.Bundle;
import android.text.TextUtils;

import com.alibaba.android.arouter.launcher.ARouter;
import com.shenyuan.superapp.base.ARouterPath;
import com.shenyuan.superapp.base.api.BasePresenter;
import com.shenyuan.superapp.base.api.common.TokenCommon;
import com.shenyuan.superapp.base.base.BaseListFragment;
import com.shenyuan.superapp.common.bean.NewsBean;
import com.shenyuan.superstudent.adapter.NewsAdapter;
import com.shenyuan.superstudent.api.presenter.HomePresenter;
import com.shenyuan.superstudent.api.view.HomeView;
import com.shenyuan.superstudent.bean.BannerBean;
import com.shenyuan.superstudent.bean.MenuBean;
import com.shenyuan.superstudent.bean.NewsTypeBean;
import com.shenyuan.superstudent.bean.ServiceBean;

import java.util.ArrayList;
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

    private OnLoadMoreListener loadMoreListener;
    private NewsAdapter newsAdapter;

    private NewsTypeBean newsTypeBean;

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
            if (TextUtils.isEmpty(TokenCommon.getToken())) {
                showLoginDialog();
                return;
            }
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
            presenter.getNewsTypeList(page);
        } else {
            presenter.getNewsList(page, newsTypeBean.getId(), 0);
        }
    }

    @Override
    protected HomePresenter createPresenter() {
        return new HomePresenter(this);
    }


    public void loadMore(OnLoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
        page++;
        loadNews();
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
        if (o != null && o.size() > 0) {
            if (o.get(0) != null && o.get(0).getNewsListVoList() != null) {

                if (page == 1) {
                    newsAdapter.setNewInstance(o.get(0).getNewsListVoList().getRecords());
                } else {
                    newsAdapter.addData(o.get(0).getNewsListVoList().getRecords());
                }
            }
        }

        binding.mrlBase.finishRefreshAndLoadMore();
        if (loadMoreListener != null) {
            loadMoreListener.onLoadMore();
        }
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


    public interface OnLoadMoreListener {
        void onLoadMore();
    }
}

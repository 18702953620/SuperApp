package com.shenyuan.superapp.api.view;

import com.shenyuan.superapp.base.api.BaseView;
import com.shenyuan.superapp.bean.BannerBean;
import com.shenyuan.superapp.bean.MenuBean;
import com.shenyuan.superapp.common.bean.NewsBean;
import com.shenyuan.superapp.bean.NewsTypeBean;
import com.shenyuan.superapp.bean.ServiceBean;

import java.util.HashMap;
import java.util.List;

/**
 * @author ch
 * @date 2021/3/11 14:11
 * desc
 */
public interface HomeView extends BaseView {
    /**
     * Banner
     *
     * @param o o
     */
    void onBannerList(List<BannerBean> o);

    /**
     * 服务列表
     *
     * @param o O
     */
    void onServiceList(List<ServiceBean> o);

    void onMyServiceList(List<MenuBean> o);

    void onEditSucc(String o);

    void onNewsTypeList(List<NewsTypeBean> o);

    void onNewsList(List<NewsBean> o);

    void onNoticeList(List<NewsBean> o);

    void onUpdate(HashMap<String, String> url);
}

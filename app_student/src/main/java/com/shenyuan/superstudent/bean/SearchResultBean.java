package com.shenyuan.superstudent.bean;

import com.alibaba.fastjson.annotation.JSONField;
import com.shenyuan.superapp.common.bean.NewsBean;

import java.util.List;

/**
 * @author ch
 * @date 2021/3/19 10:17
 * desc
 */
public class SearchResultBean {
    @JSONField(name = "serviceInfoList")
    private List<MenuBean> serviceInfoList;
    @JSONField(name = "newsList")
    private List<NewsBean> newsList;

    public List<MenuBean> getServiceInfoList() {
        return serviceInfoList;
    }

    public void setServiceInfoList(List<MenuBean> serviceInfoList) {
        this.serviceInfoList = serviceInfoList;
    }

    public List<NewsBean> getNewsList() {
        return newsList;
    }

    public void setNewsList(List<NewsBean> newsList) {
        this.newsList = newsList;
    }

}

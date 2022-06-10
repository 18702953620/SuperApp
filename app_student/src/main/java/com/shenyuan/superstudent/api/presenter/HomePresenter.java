package com.shenyuan.superstudent.api.presenter;

import com.alibaba.fastjson.JSON;
import com.shenyuan.superapp.base.api.ApiRetrofit;
import com.shenyuan.superapp.base.api.BasePresenter;
import com.shenyuan.superapp.base.api.BaseSubscriber;
import com.shenyuan.superapp.base.api.JsonRequestBody;
import com.shenyuan.superapp.base.utils.LogUtils;
import com.shenyuan.superapp.common.bean.NewsBean;
import com.shenyuan.superstudent.api.AppApiServer;
import com.shenyuan.superstudent.api.view.HomeView;
import com.shenyuan.superstudent.bean.BannerBean;
import com.shenyuan.superstudent.bean.MenuBean;
import com.shenyuan.superstudent.bean.NewsTypeBean;
import com.shenyuan.superstudent.bean.ServiceBean;

import java.util.HashMap;
import java.util.List;

/**
 * @author ch
 * @date 2021/3/11 14:11
 * desc
 */
public class HomePresenter extends BasePresenter<HomeView> {
    private final AppApiServer appServer;

    public HomePresenter(HomeView view) {
        super(view);
        appServer = ApiRetrofit.getInstance().getService(AppApiServer.class);
    }

    /**
     * 获取轮播图
     */
    public void getBannerList() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("limit", "5");
        map.put("page", "1");
        map.put("status", "0");
        LogUtils.e("getBannerList----" + JSON.toJSONString(map));

        addDisposable(appServer.getBannerList(new JsonRequestBody(map)), new BaseSubscriber<List<BannerBean>>(baseView) {
            @Override
            public void onSuccess(List<BannerBean> o) {
                baseView.onBannerList(o);
            }
        });
    }

    /**
     * 资讯类型
     */
    public void getNewsTypeList(int page) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("limit", "10");
        map.put("page", page);
        LogUtils.e("getNewsTypeList----" + JSON.toJSONString(map));

        addDisposable(appServer.getNewsTypeList(new JsonRequestBody(map)), new BaseSubscriber<List<NewsTypeBean>>(baseView) {
            @Override
            public void onSuccess(List<NewsTypeBean> o) {
                baseView.onNewsTypeList(o);
            }
        });
    }


    /**
     * 资讯列表
     */
    public void getNewsList(int page, int newsTypeId, int isSubscribed) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("limit", "10");
        map.put("page", page);
        if (isSubscribed == 1) {
            map.put("isSubscribed", isSubscribed);
        }
        map.put("newsTypeId", newsTypeId);
        LogUtils.e("getNewsList----" + JSON.toJSONString(map));

        addDisposable(appServer.getNewsList(new JsonRequestBody(map)), new BaseSubscriber<List<NewsBean>>(baseView) {
            @Override
            public void onSuccess(List<NewsBean> o) {
                baseView.onNewsList(o);
            }
        });
    }


    /**
     * 获取所有服务
     */
    public void getAllService() {
        addDisposable(appServer.getAllService(), new BaseSubscriber<List<ServiceBean>>(baseView, true) {
            @Override
            public void onSuccess(List<ServiceBean> o) {
                baseView.onServiceList(o);
            }
        });
    }

    /**
     * 获取我的服务
     */
    public void getMyService() {
        addDisposable(appServer.getMyService(), new BaseSubscriber<List<MenuBean>>(baseView) {
            @Override
            public void onSuccess(List<MenuBean> o) {
                baseView.onMyServiceList(o);
            }
        });
    }

    /**
     * 获取最新版本
     */
    public void findAppMaxVersion(int devVersionCode) {
        addDisposable(appServer.findAppMaxVersion(devVersionCode, "android"), new BaseSubscriber<HashMap<String, String>>(baseView) {
            @Override
            public void onSuccess(HashMap<String, String> o) {
                baseView.onUpdate(o);
            }
        });
    }

    /**
     * 修改我的服务
     */
    public void editMyService(List<HashMap<String, Object>> mapList) {
        LogUtils.e("editMyService----" + JSON.toJSONString(mapList));

        addDisposable(appServer.editMyService(new JsonRequestBody(mapList)), new BaseSubscriber<String>(baseView) {
            @Override
            public void onSuccess(String o) {
                baseView.onEditSucc(o);
            }
        });
    }

}

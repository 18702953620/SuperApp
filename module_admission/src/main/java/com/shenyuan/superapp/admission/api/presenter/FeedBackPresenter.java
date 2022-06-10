package com.shenyuan.superapp.admission.api.presenter;

import com.alibaba.fastjson.JSON;
import com.shenyuan.superapp.admission.api.view.FeedBackView;
import com.shenyuan.superapp.admission.bean.FeedBackInfoBean;
import com.shenyuan.superapp.admission.bean.FeedBackListBean;
import com.shenyuan.superapp.admission.bean.SimpleBean;
import com.shenyuan.superapp.admission.bean.StaffBean;
import com.shenyuan.superapp.base.api.BaseSubscriber;
import com.shenyuan.superapp.base.api.JsonRequestBody;
import com.shenyuan.superapp.base.utils.LogUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author ch
 * @date 2021/3/17 12:00
 * desc
 */
public class FeedBackPresenter extends BaseSchoolPresenter<FeedBackView> {
    public FeedBackPresenter(FeedBackView baseView) {
        super(baseView);
    }

    /**
     * 招生反馈-特色活动意向
     */
    public void getIntentList() {
        addDisposable(schoolApiServer.getIntentList(), new BaseSubscriber<List<SimpleBean>>(baseView) {
            @Override
            public void onSuccess(List<SimpleBean> o) {
                baseView.onIntentList(o);
            }
        });
    }

    /**
     * 招生反馈-喜报是否送达
     */
    public void getGiftList() {
        addDisposable(schoolApiServer.getGiftList(), new BaseSubscriber<List<SimpleBean>>(baseView) {
            @Override
            public void onSuccess(List<SimpleBean> o) {
                baseView.onGiftList(o);
            }
        });
    }

    /**
     * 招生反馈-挂牌意愿
     */
    public void getListingList() {
        addDisposable(schoolApiServer.getListingList(), new BaseSubscriber<List<SimpleBean>>(baseView) {
            @Override
            public void onSuccess(List<SimpleBean> o) {
                baseView.onListingList(o);
            }
        });
    }

    /**
     * 招生反馈-来校参观意向
     */
    public void getVisitList() {
        addDisposable(schoolApiServer.getVisitList(), new BaseSubscriber<List<SimpleBean>>(baseView) {
            @Override
            public void onSuccess(List<SimpleBean> o) {
                baseView.onVisitList(o);
            }
        });
    }


    /**
     * 招生方案-宣传员列表
     */
    public void getPlanStaffList() {
        addDisposable(schoolApiServer.getPlanStaffList(), new BaseSubscriber<List<StaffBean>>(baseView) {
            @Override
            public void onSuccess(List<StaffBean> o) {
                baseView.onPlanStaffList(o);
            }
        });
    }

    /**
     * 招生反馈-添加
     */
    public void addFeedBack(HashMap<String, Object> map) {
        LogUtils.e("addFeedBack----" + JSON.toJSONString(map));
        addDisposable(schoolApiServer.addFeedBack(new JsonRequestBody(map)), new BaseSubscriber<String>(baseView, true) {
            @Override
            public void onSuccess(String o) {
                baseView.onAddFeedBack(o);
            }
        });
    }

    /**
     * 招生反馈-修改
     */
    public void updateFeedBack(HashMap<String, Object> map) {
        LogUtils.e("updateFeedBack----" + JSON.toJSONString(map));
        addDisposable(schoolApiServer.updateFeedBack(new JsonRequestBody(map)), new BaseSubscriber<String>(baseView, true) {
            @Override
            public void onSuccess(String o) {
                baseView.onUpdateFeedBack(o);
            }
        });
    }

    /**
     * 招生反馈-列表
     */
    public void getFeedBackList(HashMap<String, Object> map) {
        LogUtils.e("addFeedBack----" + JSON.toJSONString(map));
        addDisposable(schoolApiServer.getFeedBackList(new JsonRequestBody(map)), new BaseSubscriber<List<FeedBackListBean>>(baseView) {
            @Override
            public void onSuccess(List<FeedBackListBean> o) {
                baseView.onFeedBackList(o);
            }
        });
    }

    /**
     * 查询反馈详情
     *
     * @param id id
     */
    public void getFeedBackById(int id) {
        addDisposable(schoolApiServer.getFeedBackById(id), new BaseSubscriber<FeedBackInfoBean>(baseView, true) {
            @Override
            public void onSuccess(FeedBackInfoBean o) {
                baseView.onFeedBackInfo(o);
            }
        });
    }

    /**
     * 反馈-置顶
     *
     * @param id id
     */
    public void topFeedBackById(int id) {
        addDisposable(schoolApiServer.topFeedBackById(id), new BaseSubscriber<String>(baseView, true) {
            @Override
            public void onSuccess(String o) {
                baseView.onTopFeedBack(o);
            }
        });
    }

    /**
     * 删除-反馈
     *
     * @param id id
     */
    public void deleteFeed(int id) {
        List<Integer> list = new ArrayList<>();
        list.add(id);
        addDisposable(schoolApiServer.deleteFeed(new JsonRequestBody(list)), new BaseSubscriber<String>(baseView, true) {
            @Override
            public void onSuccess(String o) {
                baseView.onDeleteFeedBack(o);
            }
        });
    }
}

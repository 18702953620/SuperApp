package com.shenyuan.superapp.admission.api.presenter;

import com.alibaba.fastjson.JSON;
import com.shenyuan.superapp.admission.api.view.ClaimView;
import com.shenyuan.superapp.admission.bean.ClaimInfoBean;
import com.shenyuan.superapp.admission.bean.ClaimListBean;
import com.shenyuan.superapp.admission.bean.PlanListBean;
import com.shenyuan.superapp.base.api.BaseSubscriber;
import com.shenyuan.superapp.base.api.JsonRequestBody;
import com.shenyuan.superapp.base.utils.LogUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author ch
 * @date 2021/3/23 12:10
 * desc
 */
public class ClaimPresenter extends BaseSchoolPresenter<ClaimView> {
    public ClaimPresenter(ClaimView baseView) {
        super(baseView);
    }

    /**
     * 添加报账
     *
     * @param map map
     */
    public void addClaim(HashMap<String, Object> map) {
        LogUtils.e("addFeedBack----" + JSON.toJSONString(map));
        addDisposable(schoolApiServer.addClaim(new JsonRequestBody(map)), new BaseSubscriber<String>(baseView, true) {
            @Override
            public void onSuccess(String o) {
                baseView.onAddClaim(o);
            }
        });
    }

    /**
     * 预算列表
     *
     * @param map map
     */
    public void getPlanByCondition(HashMap<String, Object> map) {
        LogUtils.e("getPlanByCondition----" + JSON.toJSONString(map));
        addDisposable(schoolApiServer.getPlanByCondition(new JsonRequestBody(map)), new BaseSubscriber<List<PlanListBean>>(baseView) {
            @Override
            public void onSuccess(List<PlanListBean> o) {
                baseView.onPlanList(o);
            }
        });
    }

    /**
     * 报账列表
     */
    public void getClaimList(HashMap<String, Object> map) {
        LogUtils.e("getPlanByCondition----" + JSON.toJSONString(map));
        addDisposable(schoolApiServer.getClaimList(new JsonRequestBody(map)), new BaseSubscriber<List<ClaimListBean>>(baseView) {
            @Override
            public void onSuccess(List<ClaimListBean> o) {
                baseView.onClaimList(o);
            }
        });
    }

    /**
     * 报账详情
     */
    public void getClaimById(int id) {
        addDisposable(schoolApiServer.getClaimById(id), new BaseSubscriber<ClaimInfoBean>(baseView, true) {
            @Override
            public void onSuccess(ClaimInfoBean o) {
                baseView.onClaimInfo(o);
            }
        });
    }

    /**
     * 审核-招生方案
     */
    public void aduitClaim(HashMap<String, Object> map) {
        LogUtils.e("aduitClaim----" + JSON.toJSONString(map));
        addDisposable(schoolApiServer.aduitClaim(new JsonRequestBody(map)), new BaseSubscriber<String>(baseView) {
            @Override
            public void onSuccess(String o) {
                baseView.onAduitClaim(o);
            }
        });
    }

    /**
     * 删除-反馈
     *
     * @param id id
     */
    public void deleteClaim(int id) {
        List<Integer> list = new ArrayList<>();
        list.add(id);
        addDisposable(schoolApiServer.deleteClaim(new JsonRequestBody(list)), new BaseSubscriber<String>(baseView) {
            @Override
            public void onSuccess(String o) {
                baseView.onDeleteClaim(o);
            }
        });
    }

    /**
     * 反馈-置顶
     *
     * @param id id
     */
    public void topClaimById(int id) {
        addDisposable(schoolApiServer.topClaimById(id), new BaseSubscriber<String>(baseView) {
            @Override
            public void onSuccess(String o) {
                baseView.onTopClaim(o);
            }
        });
    }

    /**
     * 修改报账
     */
    public void updateClaim(HashMap<String, Object> bean) {
        LogUtils.e("updateClaim----" + JSON.toJSONString(bean));
        addDisposable(schoolApiServer.updateClaim(new JsonRequestBody(bean)), new BaseSubscriber<String>(baseView, true) {

            @Override
            public void onSuccess(String o) {
                baseView.onUpdateClaim(o);
            }
        });
    }
}

package com.shenyuan.superapp.admission.api.presenter;

import com.alibaba.fastjson.JSON;
import com.shenyuan.superapp.admission.api.view.PlanView;
import com.shenyuan.superapp.admission.bean.PlanInfoBean;
import com.shenyuan.superapp.admission.bean.PlanListBean;
import com.shenyuan.superapp.admission.bean.SimpleBean;
import com.shenyuan.superapp.admission.bean.SimpleStringBean;
import com.shenyuan.superapp.admission.bean.StaffBean;
import com.shenyuan.superapp.base.api.BaseSubscriber;
import com.shenyuan.superapp.base.api.JsonRequestBody;
import com.shenyuan.superapp.base.utils.LogUtils;

import java.util.HashMap;
import java.util.List;

/**
 * @author ch
 * @date 2021/3/13 15:06
 * desc
 */
public class PlanPresenter extends BaseSchoolPresenter<PlanView> {
    public PlanPresenter(PlanView baseView) {
        super(baseView);
    }

    /**
     * 招生方案-审核状态
     */
    public void getExamineStateList() {
        addDisposable(schoolApiServer.getExamineStateList(), new BaseSubscriber<List<SimpleBean>>(baseView) {
            @Override
            public void onSuccess(List<SimpleBean> o) {
                baseView.onExamineList(o);
            }
        });
    }

    /**
     * 招生方案-出差任务
     */
    public void getPlanTaskList() {
        addDisposable(schoolApiServer.getPlanTaskList(), new BaseSubscriber<List<SimpleBean>>(baseView) {
            @Override
            public void onSuccess(List<SimpleBean> o) {
                baseView.onTaskList(o);
            }
        });
    }

    /**
     * 招生方案-创建时间
     */
    public void getPlanCreateList() {
        addDisposable(schoolApiServer.getPlanCreateList(), new BaseSubscriber<List<SimpleStringBean>>(baseView) {
            @Override
            public void onSuccess(List<SimpleStringBean> o) {
                baseView.onCreateList(o);
            }
        });
    }

    /**
     * 招生方案-经费类型
     */
    public void getFeeTypeList() {
        addDisposable(schoolApiServer.getFeeTypeList(), new BaseSubscriber<List<SimpleStringBean>>(baseView) {
            @Override
            public void onSuccess(List<SimpleStringBean> o) {
                baseView.onFeeTypeList(o);
            }
        });
    }

    /**
     * 招生方案-宣传方式
     */
    public void getPropagationWayDictList() {
        addDisposable(schoolApiServer.getPropagationWayDictList(), new BaseSubscriber<List<SimpleStringBean>>(baseView) {
            @Override
            public void onSuccess(List<SimpleStringBean> o) {
                baseView.onPropagationWayDictListList(o);
            }
        });
    }

    /**
     * 招生方案-是否制作喜报
     */
    public void getMadeList() {
        addDisposable(schoolApiServer.getMadeList(), new BaseSubscriber<List<SimpleBean>>(baseView) {
            @Override
            public void onSuccess(List<SimpleBean> o) {
                baseView.onMadeList(o);
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
     * 招生方案-列表
     */
    public void getPlanList(HashMap<String, Object> map) {
        LogUtils.e("getPlanList----" + JSON.toJSONString(map));
        addDisposable(schoolApiServer.getPlanList(new JsonRequestBody(map)), new BaseSubscriber<List<PlanListBean>>(baseView) {
            @Override
            public void onSuccess(List<PlanListBean> o) {
                baseView.onPlanList(o);
            }
        });
    }

    /**
     * 添加-招生方案
     */
    public void addPlan(HashMap<String, Object> map) {
        LogUtils.e("addPlan----" + JSON.toJSONString(map));
        addDisposable(schoolApiServer.addPlan(new JsonRequestBody(map)), new BaseSubscriber<String>(baseView) {
            @Override
            public void onSuccess(String o) {
                baseView.onAddPlan(o);
            }
        });
    }

    /**
     * 审核-招生方案
     */
    public void aduitPlan(HashMap<String, Object> map) {
        LogUtils.e("aduitPlan----" + JSON.toJSONString(map));
        addDisposable(schoolApiServer.aduitPlan(new JsonRequestBody(map)), new BaseSubscriber<String>(baseView) {
            @Override
            public void onSuccess(String o) {
                baseView.onAduitPlan(o);
            }
        });
    }

    /**
     * 通过id查询方案
     */
    public void getPlanById(String id) {
        addDisposable(schoolApiServer.getPlanById(id), new BaseSubscriber<PlanInfoBean>(baseView, true) {

            @Override
            public void onSuccess(PlanInfoBean o) {
                baseView.onPlanInfo(o);
            }
        });
    }

    /**
     * 修改方案
     */
    public void updatePlan(PlanInfoBean bean) {
        LogUtils.e("updatePlan----" + JSON.toJSONString(bean));
        addDisposable(schoolApiServer.updatePlan(new JsonRequestBody(bean)), new BaseSubscriber<String>(baseView, true) {

            @Override
            public void onSuccess(String o) {
                baseView.onUpdatePlan(o);
            }
        });
    }
}

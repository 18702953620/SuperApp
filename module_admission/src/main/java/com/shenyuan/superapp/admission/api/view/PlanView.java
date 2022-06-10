package com.shenyuan.superapp.admission.api.view;

import com.shenyuan.superapp.admission.bean.PlanInfoBean;
import com.shenyuan.superapp.admission.bean.PlanListBean;
import com.shenyuan.superapp.admission.bean.SimpleBean;
import com.shenyuan.superapp.admission.bean.SimpleStringBean;
import com.shenyuan.superapp.admission.bean.StaffBean;
import com.shenyuan.superapp.base.api.BaseView;

import java.util.List;

/**
 * @author ch
 * @date 2021/3/13 15:06
 * desc
 */
public interface PlanView extends BaseView {
    /**
     * 审核状态
     *
     * @param o o
     */
    void onExamineList(List<SimpleBean> o);

    /**
     * 出差任务
     *
     * @param o o
     */
    void onTaskList(List<SimpleBean> o);

    /**
     * 方案列表
     *
     * @param o o
     */
    void onPlanList(List<PlanListBean> o);

    /**
     * 创建时间
     *
     * @param o o
     */
    void onCreateList(List<SimpleStringBean> o);

    /**
     * 经费类型
     *
     * @param o o
     */
    void onFeeTypeList(List<SimpleStringBean> o);

    /**
     * 宣传方式
     *
     * @param o o
     */
    void onPropagationWayDictListList(List<SimpleStringBean> o);

    /**
     * 是否制作喜报
     *
     * @param o o
     */
    void onMadeList(List<SimpleBean> o);

    /**
     * 宣传员列表
     *
     * @param o o
     */
    void onPlanStaffList(List<StaffBean> o);

    /**
     * 添加
     *
     * @param o o
     */
    void onAddPlan(String o);

    /**
     * 方案详情
     *
     * @param o o
     */
    void onPlanInfo(PlanInfoBean o);

    /**
     * 审核
     *
     * @param o o
     */
    void onAduitPlan(String o);

    /**
     * 修改
     *
     * @param o o
     */
    void onUpdatePlan(String o);
}

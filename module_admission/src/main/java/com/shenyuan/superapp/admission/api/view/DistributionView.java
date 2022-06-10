package com.shenyuan.superapp.admission.api.view;

import com.shenyuan.superapp.admission.bean.StaffBean;
import com.shenyuan.superapp.base.api.BaseView;

import java.util.List;

/**
 * @author ch
 * @date 2021/2/22 14:38
 * desc
 */
public interface DistributionView extends BaseView {
    /**
     * 宣传员
     *
     * @param o o
     */
    void ontStaffList(List<StaffBean> o);

    /**
     * 分配目标学校
     *
     * @param o o
     */
    void distributionSchool(String o);

    /**
     * 分配目标生源
     *
     * @param o o
     */
    void distributionStudent(String o);
}

package com.shenyuan.superapp.admission.api.view;

import com.shenyuan.superapp.admission.bean.SchoolListBean;
import com.shenyuan.superapp.base.api.BaseView;

import java.util.List;

/**
 * @author ch
 * @date 2021/2/23 17:24
 * desc
 */
public interface TargetView extends BaseView {
    /**
     * 目标学校
     *
     * @param o o
     */
    void onTargetSchoolList(List<SchoolListBean> o);

    /**
     * 退回列表
     *
     * @param o o
     */
    void onBackSchoolList(List<SchoolListBean> o);

    /**
     * 退回
     * @param o o
     */
    void onBackSchool(String o);
}

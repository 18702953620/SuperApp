package com.shenyuan.superapp.admission.api.view;

import com.shenyuan.superapp.admission.bean.AreaBean;
import com.shenyuan.superapp.admission.bean.AreaUserBean;
import com.shenyuan.superapp.admission.bean.CreaterBean;
import com.shenyuan.superapp.admission.bean.SchoolTypeBean;
import com.shenyuan.superapp.admission.bean.SimpleBean;
import com.shenyuan.superapp.admission.bean.StaffBean;
import com.shenyuan.superapp.base.api.BaseView;

import java.util.List;

/**
 * @author ch
 * @date 2021/2/18 9:59
 * desc
 */
public interface CommonView extends BaseView {
    /**
     * 区域列表
     *
     * @param beans beans
     */
    void onAreaList(List<AreaBean> beans);

    /**
     * 信息录入人列表
     *
     * @param beans beans
     */
    void onCreaterList(List<CreaterBean> beans);

    /**
     * 是否挂牌
     *
     * @param beans beans
     */
    void onLising(List<SimpleBean> beans);

    /**
     * 是否维护
     *
     * @param beans beans
     */
    void onSchoolState(List<SimpleBean> beans);

    /**
     * 学校类型
     *
     * @param beans beans
     */
    void onSchoolLevel(SchoolTypeBean beans);

    /**
     * 学校类型
     *
     * @param beans beans
     */
    void ontUserArea(List<AreaUserBean> beans);

    /**
     * 宣传员
     *
     * @param o o
     */
    void ontStaffList(List<StaffBean> o);

    /**
     * 排序
     *
     * @param o o
     */
    void onSortList(List<SimpleBean> o);
}

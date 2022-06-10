package com.shenyuan.superapp.admission.api.view;

import com.shenyuan.superapp.admission.bean.AreaBean;
import com.shenyuan.superapp.admission.bean.AreaUserBean;
import com.shenyuan.superapp.admission.bean.MajorBean;
import com.shenyuan.superapp.admission.bean.SimpleBean;
import com.shenyuan.superapp.admission.bean.StaffBean;
import com.shenyuan.superapp.admission.bean.YearBean;
import com.shenyuan.superapp.base.api.BaseView;

import java.util.List;

/**
 * @author ch
 * @date 2021/2/24 15:15
 * desc
 */
public interface StudentCommonView extends BaseView {
    void onStudentSourceList(List<SimpleBean> o);

    void onStudentMajorList(List<MajorBean> o);

    void onAreaList(List<AreaBean> o);

    void ontUserArea(List<AreaUserBean> o);

    void ontStaffList(List<StaffBean> o);

    void onStudentYearList(List<YearBean> o);

    void onStudentSubjectList(List<SimpleBean> o);

    void onSchoolLevelList(List<SimpleBean> o);

    void onStudentTargetList(List<SimpleBean> o);

    void onStudentSexList(List<SimpleBean> o);

    void onStudentGenderList(List<SimpleBean> o);
}

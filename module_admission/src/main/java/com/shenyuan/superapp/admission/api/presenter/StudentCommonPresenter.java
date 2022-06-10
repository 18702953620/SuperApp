package com.shenyuan.superapp.admission.api.presenter;

import com.shenyuan.superapp.admission.api.view.StudentCommonView;
import com.shenyuan.superapp.admission.bean.AreaBean;
import com.shenyuan.superapp.admission.bean.AreaUserBean;
import com.shenyuan.superapp.admission.bean.MajorBean;
import com.shenyuan.superapp.admission.bean.SimpleBean;
import com.shenyuan.superapp.admission.bean.StaffBean;
import com.shenyuan.superapp.admission.bean.YearBean;
import com.shenyuan.superapp.base.api.BaseSubscriber;

import java.util.List;

/**
 * @author ch
 * @date 2021/2/24 15:12
 * desc
 */
public class StudentCommonPresenter extends BaseSchoolPresenter<StudentCommonView> {
    public StudentCommonPresenter(StudentCommonView baseView) {
        super(baseView);
    }

    /**
     * 生源来源
     */
    public void getStudentSourceList() {
        addDisposable(schoolApiServer.getStudentSourceList(), new BaseSubscriber<List<SimpleBean>>(baseView) {

            @Override
            public void onSuccess(List<SimpleBean> o) {
                baseView.onStudentSourceList(o);
            }
        });
    }

    /**
     * 意向专业
     */
    public void getStudentMajorList() {
        addDisposable(schoolApiServer.getStudentMajorList(), new BaseSubscriber<List<MajorBean>>(baseView) {

            @Override
            public void onSuccess(List<MajorBean> o) {
                baseView.onStudentMajorList(o);
            }
        });
    }

    /**
     * 毕业年份
     */
    public void getStudentYearList() {
        addDisposable(schoolApiServer.getStudentYearList(), new BaseSubscriber<List<YearBean>>(baseView) {

            @Override
            public void onSuccess(List<YearBean> o) {
                baseView.onStudentYearList(o);
            }
        });
    }

    /**
     * 所属科类
     */
    public void getSubjectList() {
        addDisposable(schoolApiServer.getSubjectList(), new BaseSubscriber<List<SimpleBean>>(baseView) {

            @Override
            public void onSuccess(List<SimpleBean> o) {
                baseView.onStudentSubjectList(o);
            }
        });
    }

    /**
     * 招生层次
     */
    public void getSchoolLevelList() {
        addDisposable(schoolApiServer.getSchoolLevelList(), new BaseSubscriber<List<SimpleBean>>(baseView) {

            @Override
            public void onSuccess(List<SimpleBean> o) {
                baseView.onSchoolLevelList(o);
            }
        });
    }

    /**
     * 生源意向
     */
    public void getSchoolTargetList() {
        addDisposable(schoolApiServer.getSchoolTargetList(), new BaseSubscriber<List<SimpleBean>>(baseView) {

            @Override
            public void onSuccess(List<SimpleBean> o) {
                baseView.onStudentTargetList(o);
            }
        });
    }

    /**
     * 性别
     */
    public void getSexList() {
        addDisposable(schoolApiServer.getSexList(), new BaseSubscriber<List<SimpleBean>>(baseView) {

            @Override
            public void onSuccess(List<SimpleBean> o) {
                baseView.onStudentSexList(o);
            }
        });
    }

    /**
     * 年级
     */
    public void getGenderList() {
        addDisposable(schoolApiServer.getGenderList(), new BaseSubscriber<List<SimpleBean>>(baseView) {

            @Override
            public void onSuccess(List<SimpleBean> o) {
                baseView.onStudentGenderList(o);
            }
        });
    }


    /**
     * 区域列表
     */
    public void getAreaList() {
        addDisposable(schoolApiServer.getAreaList(), new BaseSubscriber<List<AreaBean>>(baseView) {
            @Override
            public void onSuccess(List<AreaBean> o) {
                baseView.onAreaList(o);
            }
        });
    }


    /**
     * 区域负责人
     */
    public void getUserArea() {
        addDisposable(schoolApiServer.getUserArea(), new BaseSubscriber<List<AreaUserBean>>(baseView) {

            @Override
            public void onSuccess(List<AreaUserBean> o) {
                baseView.ontUserArea(o);
            }
        });
    }

    /**
     * 宣传员
     */
    public void getStaffList() {
        addDisposable(schoolApiServer.getStaffList(), new BaseSubscriber<List<StaffBean>>(baseView) {

            @Override
            public void onSuccess(List<StaffBean> o) {
                baseView.ontStaffList(o);
            }
        });
    }


}

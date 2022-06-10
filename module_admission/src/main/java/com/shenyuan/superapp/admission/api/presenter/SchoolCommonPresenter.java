package com.shenyuan.superapp.admission.api.presenter;

import com.shenyuan.superapp.admission.api.view.CommonView;
import com.shenyuan.superapp.admission.bean.AreaBean;
import com.shenyuan.superapp.admission.bean.AreaUserBean;
import com.shenyuan.superapp.admission.bean.CreaterBean;
import com.shenyuan.superapp.admission.bean.SchoolTypeBean;
import com.shenyuan.superapp.admission.bean.SimpleBean;
import com.shenyuan.superapp.admission.bean.StaffBean;
import com.shenyuan.superapp.base.api.BaseSubscriber;

import java.util.List;

/**
 * @author ch
 * @date 2021/2/18 9:59
 * desc
 */
public class SchoolCommonPresenter extends BaseSchoolPresenter<CommonView> {

    public SchoolCommonPresenter(CommonView baseView) {
        super(baseView);
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
     * 信息录入人列表
     */
    public void getCreatNameList() {
        addDisposable(schoolApiServer.getCreatNameList(), new BaseSubscriber<List<CreaterBean>>(baseView) {
            @Override
            public void onSuccess(List<CreaterBean> o) {
               baseView.onCreaterList(o);
            }
        });
    }

    /**
     * 分配人列表
     */
    public void getDisNameList() {
        addDisposable(schoolApiServer.getDisNameList(), new BaseSubscriber<List<CreaterBean>>(baseView) {
            @Override
            public void onSuccess(List<CreaterBean> o) {
               baseView.onCreaterList(o);
            }
        });
    }

    /**
     * 是否挂牌
     */
    public void isLising() {
        addDisposable(schoolApiServer.isLising(), new BaseSubscriber<List<SimpleBean>>(baseView) {
            @Override
            public void onSuccess(List<SimpleBean> o) {
               baseView.onLising(o);
            }
        });
    }

    /**
     * 是否维护
     */
    public void getSchoolState() {
        addDisposable(schoolApiServer.getSchoolState(), new BaseSubscriber<List<SimpleBean>>(baseView) {
            @Override
            public void onSuccess(List<SimpleBean> o) {
               baseView.onSchoolState(o);
            }
        });
    }

    /**
     * 学校类型
     */
    public void getSchoolLevel() {
        addDisposable(schoolApiServer.getSchoolLevel(), new BaseSubscriber<SchoolTypeBean>(baseView) {
            @Override
            public void onSuccess(SchoolTypeBean o) {
               baseView.onSchoolLevel(o);
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
     * 区域负责人
     */
    public void getAddUserArea() {
        addDisposable(schoolApiServer.getAddUserArea(), new BaseSubscriber<List<AreaUserBean>>(baseView) {

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

    /**
     * 排序
     */
    public void getSortList() {
        addDisposable(schoolApiServer.getSortList(), new BaseSubscriber<List<SimpleBean>>(baseView) {

            @Override
            public void onSuccess(List<SimpleBean> o) {
               baseView.onSortList(o);
            }
        });
    }

}

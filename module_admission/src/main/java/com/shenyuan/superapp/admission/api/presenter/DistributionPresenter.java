package com.shenyuan.superapp.admission.api.presenter;

import com.shenyuan.superapp.admission.api.view.DistributionView;
import com.shenyuan.superapp.admission.bean.StaffBean;
import com.shenyuan.superapp.base.api.BaseSubscriber;

import java.util.HashMap;
import java.util.List;

/**
 * @author ch
 * @date 2021/2/22 14:38
 * desc
 */
public class DistributionPresenter extends BaseSchoolPresenter<DistributionView> {

    public DistributionPresenter(DistributionView baseView) {
        super(baseView);
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
     * 分配目标学校
     */
    public void distributionSchool(int schoolId, List<Integer> list) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("schoolId", schoolId);
        StringBuilder ids = new StringBuilder();
        if (list != null && list.size() > 0) {
            for (Integer integer : list) {
                if (ids.length() > 0) {
                    ids.append(",");
                }
                ids.append(integer);
            }
        }
        map.put("staffIds", ids);
        addDisposable(schoolApiServer.distributionSchool(map), new BaseSubscriber<String>(baseView) {

            @Override
            public void onSuccess(String o) {
                baseView.distributionSchool(o);
            }
        });
    }

    /**
     * 分配目标学生
     */
    public void distributionStudent(int studentId, List<Integer> list) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("studentId", studentId);
        StringBuilder ids = new StringBuilder();
        if (list != null && list.size() > 0) {
            for (Integer integer : list) {
                if (ids.length() > 0) {
                    ids.append(",");
                }
                ids.append(integer);
            }
        }
        map.put("staffIds", ids);
        addDisposable(schoolApiServer.distributionStudent(map), new BaseSubscriber<String>(baseView) {

            @Override
            public void onSuccess(String o) {
                baseView.distributionStudent(o);
            }
        });
    }
}

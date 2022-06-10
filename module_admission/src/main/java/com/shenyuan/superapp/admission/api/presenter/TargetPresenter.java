package com.shenyuan.superapp.admission.api.presenter;

import com.alibaba.fastjson.JSON;
import com.shenyuan.superapp.admission.api.view.TargetView;
import com.shenyuan.superapp.admission.bean.SchoolListBean;
import com.shenyuan.superapp.base.api.BaseSubscriber;
import com.shenyuan.superapp.base.api.JsonRequestBody;
import com.shenyuan.superapp.base.utils.LogUtils;

import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @author ch
 * @date 2021/2/23 17:23
 * desc 目标学校
 */
public class TargetPresenter extends BaseSchoolPresenter<TargetView> {

    public TargetPresenter(TargetView baseView) {
        super(baseView);
    }

    /**
     * 目标学校列表
     */
    public void getTargetSchoolList(HashMap<String, Object> map) {
        LogUtils.e("getTargetSchoolList----" + JSON.toJSONString(map));
        addDisposable(schoolApiServer.getTargetSchoolList(new JsonRequestBody(map)), new BaseSubscriber<List<SchoolListBean>>(baseView) {

            @Override
            public void onSuccess(List<SchoolListBean> o) {
                baseView.onTargetSchoolList(o);
            }
        });
    }

    /**
     * 退回库
     */
    public void getBackSchoolList(HashMap<String, Object> map) {
        LogUtils.e("getBackSchoolList----" + JSON.toJSONString(map));
        addDisposable(schoolApiServer.getBackSchoolList(new JsonRequestBody(map)), new BaseSubscriber<List<SchoolListBean>>(baseView) {

            @Override
            public void onSuccess(List<SchoolListBean> o) {
                baseView.onBackSchoolList(o);
            }
        });
    }

    /**
     * 退回
     */
    public void returnBackSchool(String schoolId, String resion) {
        addDisposable(schoolApiServer.returnBackSchool(schoolId, resion), new BaseSubscriber<String>(baseView) {

            @Override
            public void onSuccess(String o) {
                baseView.onBackSchool(o);
            }
        });
    }
}

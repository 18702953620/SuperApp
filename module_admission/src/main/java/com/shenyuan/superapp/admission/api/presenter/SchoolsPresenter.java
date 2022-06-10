package com.shenyuan.superapp.admission.api.presenter;

import com.alibaba.fastjson.JSON;
import com.shenyuan.superapp.admission.api.view.SchoolsView;
import com.shenyuan.superapp.admission.bean.SchoolInfoBean;
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
 * @date 2020/12/15-16:53
 * desc
 */
public class SchoolsPresenter extends BaseSchoolPresenter<SchoolsView> {


    public SchoolsPresenter(SchoolsView baseView) {
        super(baseView);
    }


    /**
     * 学校列表
     */
    public void getSchoolList(HashMap<String, Object> map) {
        LogUtils.e("getSchoolList----" + JSON.toJSONString(map));
        addDisposable(schoolApiServer.getSchoolList(new JsonRequestBody(map)), new BaseSubscriber<List<SchoolListBean>>(baseView) {
            @Override
            public void onSuccess(List<SchoolListBean> o) {
                baseView.onSchoolList(o);
            }
        });
    }


    /**
     * 添加学校
     */
    public void addSchool(HashMap<String, Object> map) {
        LogUtils.e("addSchool----" + JSON.toJSONString(map));
        addDisposable(schoolApiServer.addSchool(new JsonRequestBody(map)), new BaseSubscriber<String>(baseView, true) {

            @Override
            public void onSuccess(String o) {
                baseView.onAddSchool(o);
            }
        });
    }

    /**
     * 更新学校
     */
    public void updateSchool(HashMap<String, Object> map) {
        LogUtils.e("updateSchool----" + JSON.toJSONString(map));
        addDisposable(schoolApiServer.updateSchool(new JsonRequestBody(map)), new BaseSubscriber<String>(baseView, true) {

            @Override
            public void onSuccess(String o) {
                baseView.onUpdateSchool(o);
            }
        });
    }

    /**
     * 删除学校
     */
    public void deleteSchool(String schoolIds) {
        addDisposable(schoolApiServer.deleteSchool(schoolIds), new BaseSubscriber<String>(baseView, true) {

            @Override
            public void onSuccess(String o) {
                baseView.onDeleteSchool(o);
            }
        });
    }

    /**
     * 通过id查询学校
     */
    public void getSchoolById(String schoolIds) {
        addDisposable(schoolApiServer.getSchoolById(schoolIds), new BaseSubscriber<SchoolInfoBean>(baseView, true) {

            @Override
            public void onSuccess(SchoolInfoBean o) {
                baseView.onSchoolInfo(o);
            }
        });
    }

    /**
     * 通过id查询学校
     */
    public void getTargetSchoolById(String schoolIds) {
        addDisposable(schoolApiServer.getTargetSchoolById(schoolIds), new BaseSubscriber<SchoolInfoBean>(baseView, true) {

            @Override
            public void onSuccess(SchoolInfoBean o) {
                baseView.onSchoolInfo(o);
            }
        });
    }

    /**
     * 通过id查询学校
     */
    public void getReturnSchoolById(String schoolIds) {
        addDisposable(schoolApiServer.getReturnSchoolById(schoolIds), new BaseSubscriber<SchoolInfoBean>(baseView, true) {

            @Override
            public void onSuccess(SchoolInfoBean o) {
                baseView.onSchoolInfo(o);
            }
        });
    }
}

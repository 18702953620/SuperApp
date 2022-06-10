package com.shenyuan.superapp.admission.api.presenter;

import com.alibaba.fastjson.JSON;
import com.shenyuan.superapp.admission.api.view.StudentView;
import com.shenyuan.superapp.admission.bean.StudentInfoBean;
import com.shenyuan.superapp.admission.bean.StudentListBean;
import com.shenyuan.superapp.base.api.BaseSubscriber;
import com.shenyuan.superapp.base.api.JsonRequestBody;
import com.shenyuan.superapp.base.utils.LogUtils;

import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @author ch
 * @date 2021/2/25 14:44
 * desc 生源池
 */
public class StudentPresenter extends BaseSchoolPresenter<StudentView> {

    public StudentPresenter(StudentView baseView) {
        super(baseView);
    }

    /**
     * 生源池列表
     */
    public void getStudentList(HashMap<String, Object> map) {
        LogUtils.e("getStudentList----" + JSON.toJSONString(map));
        addDisposable(schoolApiServer.getStudentList(new JsonRequestBody(map)), new BaseSubscriber<List<StudentListBean>>(baseView) {
            @Override
            public void onSuccess(List<StudentListBean> o) {
                baseView.onStudentList(o);
            }
        });
    }

    /**
     * 我的生源池列表
     */
    public void getMyStudentList(HashMap<String, Object> map) {
        LogUtils.e("getStudentList----" + JSON.toJSONString(map));
        addDisposable(schoolApiServer.getMyStudentList(new JsonRequestBody(map)), new BaseSubscriber<List<StudentListBean>>(baseView) {
            @Override
            public void onSuccess(List<StudentListBean> o) {
                baseView.onStudentList(o);
            }
        });
    }

    /**
     * 退回池列表
     */
    public void getStudentBackList(HashMap<String, Object> map) {
        LogUtils.e("getStudentBackList----" + JSON.toJSONString(map));
        addDisposable(schoolApiServer.getStudentBackList(new JsonRequestBody(map)), new BaseSubscriber<List<StudentListBean>>(baseView) {
            @Override
            public void onSuccess(List<StudentListBean> o) {
                baseView.onBackStudentList(o);
            }
        });
    }

    /**
     * 添加生源
     */
    public void addStudent(HashMap<String, Object> map) {
        LogUtils.e("addStudent----" + JSON.toJSONString(map));
        addDisposable(schoolApiServer.addStudent(new JsonRequestBody(map)), new BaseSubscriber<String>(baseView, true) {

            @Override
            public void onSuccess(String o) {
                baseView.onAddStudent(o);
            }
        });
    }

    /**
     * 修改生源
     */
    public void updateStudent(HashMap<String, Object> map) {
        LogUtils.e("updateStudent----" + JSON.toJSONString(map));
        addDisposable(schoolApiServer.updateStudent(new JsonRequestBody(map)), new BaseSubscriber<String>(baseView, true) {

            @Override
            public void onSuccess(String o) {
                baseView.onAddStudent(o);
            }
        });
    }

    /**
     * 修改我的生源
     */
    public void updateMyStudent(HashMap<String, Object> map) {
        LogUtils.e("updateStudent----" + JSON.toJSONString(map));
        addDisposable(schoolApiServer.updateMyStudent(new JsonRequestBody(map)), new BaseSubscriber<String>(baseView, true) {

            @Override
            public void onSuccess(String o) {
                baseView.onAddStudent(o);
            }
        });
    }

    /**
     * 通过id查询学生
     */
    public void getStudentById(String schoolIds) {
        addDisposable(schoolApiServer.getStudentById(schoolIds), new BaseSubscriber<StudentInfoBean>(baseView, true) {

            @Override
            public void onSuccess(StudentInfoBean o) {
                baseView.onStudentInfo(o);
            }
        });
    }

    /**
     * 通过id查询学生
     */
    public void getMyStudentById(String schoolIds) {
        addDisposable(schoolApiServer.getMyStudentById(schoolIds), new BaseSubscriber<StudentInfoBean>(baseView, true) {

            @Override
            public void onSuccess(StudentInfoBean o) {
                baseView.onStudentInfo(o);
            }
        });
    }

    /**
     * 通过id查询学生
     */
    public void getReturnStudentById(String schoolIds) {
        addDisposable(schoolApiServer.getReturnStudentById(schoolIds), new BaseSubscriber<StudentInfoBean>(baseView, true) {

            @Override
            public void onSuccess(StudentInfoBean o) {
                baseView.onStudentInfo(o);
            }
        });
    }


    /**
     * 删除学校
     */
    public void deleteStudent(String schoolIds) {
        addDisposable(schoolApiServer.deleteStudent(schoolIds), new BaseSubscriber<String>(baseView, true) {

            @Override
            public void onSuccess(String o) {
                baseView.onDeleteStudent(o);
            }
        });
    }

    /**
     * 退回
     */
    public void returnBackStudent(String studentId, String resion) {
        addDisposable(schoolApiServer.returnBackStudent(studentId, resion), new BaseSubscriber<String>(baseView) {

            @Override
            public void onSuccess(String o) {
                baseView.onBackStudent(o);
            }
        });
    }
}

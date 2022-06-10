package com.shenyuan.superapp.api.presenter;

import com.shenyuan.superapp.api.AppApiServer;
import com.shenyuan.superapp.api.view.ExamView;
import com.shenyuan.superapp.base.api.ApiRetrofit;
import com.shenyuan.superapp.base.api.BasePresenter;
import com.shenyuan.superapp.base.api.BaseSubscriber;
import com.shenyuan.superapp.bean.ExamStudentInfoBean;

/**
 * @author ch
 * @date 2021/5/19 15:15
 * @
 */
public class ExamPresenter extends BasePresenter<ExamView> {

    private final AppApiServer appServer;

    public ExamPresenter(ExamView examView) {
        super(examView);
        appServer = ApiRetrofit.getInstance().getService(AppApiServer.class);
    }

    /**
     * 考生信息
     *
     * @param uuid uuid
     */
    public void getStudentInfo(String uuid) {
        addDisposable(appServer.getStudentInfo(uuid), new BaseSubscriber<ExamStudentInfoBean>(baseView, true) {
            @Override
            public void onSuccess(ExamStudentInfoBean o) {
                baseView.onExamInfo(o);
            }
        });
    }

    /**
     * 拒绝效验
     *
     * @param uuid uuid
     */
    public void refuseValid(String uuid, String reson) {
        addDisposable(appServer.refuseValid(uuid, reson), new BaseSubscriber<String>(baseView, true) {
            @Override
            public void onSuccess(String o) {
                baseView.onExamOption(o);
            }
        });
    }

    /**
     * 通过效验
     *
     * @param uuid uuid
     */
    public void passValid(String uuid) {
        addDisposable(appServer.passValid(uuid), new BaseSubscriber<String>(baseView, true) {
            @Override
            public void onSuccess(String o) {
                baseView.onExamOption(o);
            }
        });
    }
}



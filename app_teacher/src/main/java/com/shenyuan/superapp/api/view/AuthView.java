package com.shenyuan.superapp.api.view;

import com.shenyuan.superapp.base.api.BaseView;
import com.shenyuan.superapp.bean.ExamStudentInfoBean;

/**
 * @author ch
 * @date 2021/1/4-15:41
 * desc
 */
public interface AuthView extends BaseView {
    /**
     * 扫码成功
     *
     * @param uuid uuid
     */
    void onLoginScan(String uuid);

    /**
     * 扫码确认
     */
    void onLoginConfirm();


}

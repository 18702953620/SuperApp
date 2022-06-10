package com.shenyuan.superapp.admission.api.view;

import com.shenyuan.superapp.admission.bean.StudentInfoBean;
import com.shenyuan.superapp.admission.bean.StudentListBean;
import com.shenyuan.superapp.base.api.BaseView;

import java.util.List;

/**
 * @author ch
 * @date 2021/2/25 14:44
 * desc
 */
public interface StudentView extends BaseView {
    /**
     * 生源池
     *
     * @param o o
     */
    void onStudentList(List<StudentListBean> o);

    /**
     * 添加生源
     *
     * @param o o
     */
    void onAddStudent(String o);

    /**
     * 生源详情
     *
     * @param o o
     */
    void onStudentInfo(StudentInfoBean o);

    /**
     * 删除生源
     *
     * @param o o
     */
    void onDeleteStudent(String o);

    /**
     * 退回
     *
     * @param o o
     */
    void onBackStudent(String o);

    /**
     * 退回池
     *
     * @param o o
     */
    void onBackStudentList(List<StudentListBean> o);
}

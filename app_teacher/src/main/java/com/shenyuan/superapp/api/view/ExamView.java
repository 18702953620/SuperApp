package com.shenyuan.superapp.api.view;

import com.shenyuan.superapp.base.api.BaseView;
import com.shenyuan.superapp.bean.ExamStudentInfoBean;

/**
 * @author ch
 * @date 2021/5/19 15:16
 * @
 */
public interface ExamView extends BaseView {

    /**
     * 考试信息确认
     *
     * @param o o
     */
    void onExamInfo(ExamStudentInfoBean o);

    /**
     * 考试确认
     *
     * @param o o
     */
    void onExamOption(String o);
}

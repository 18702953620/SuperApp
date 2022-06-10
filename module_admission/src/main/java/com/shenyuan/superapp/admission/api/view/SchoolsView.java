package com.shenyuan.superapp.admission.api.view;

import com.shenyuan.superapp.admission.bean.SchoolInfoBean;
import com.shenyuan.superapp.admission.bean.SchoolListBean;
import com.shenyuan.superapp.base.api.BaseView;

import java.util.List;

/**
 * @author ch
 * @date 2020/12/15-16:53
 * desc
 */
public interface SchoolsView extends BaseView {
    /**
     * 学校列表
     *
     * @param o o
     */
    void onSchoolList(List<SchoolListBean> o);

    /**
     * 添加学校
     *
     * @param o o
     */
    void onAddSchool(String o);

    /**
     * 删除学校
     *
     * @param o o
     */
    void onDeleteSchool(String o);

    /**
     * 学校详情
     *
     * @param o o
     */
    void onSchoolInfo(SchoolInfoBean o);

    /**
     * 编辑
     *
     * @param o o
     */
    void onUpdateSchool(String o);
}

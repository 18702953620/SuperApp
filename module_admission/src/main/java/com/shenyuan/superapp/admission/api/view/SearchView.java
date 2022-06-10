package com.shenyuan.superapp.admission.api.view;

import com.shenyuan.superapp.admission.bean.ClaimListBean;
import com.shenyuan.superapp.admission.bean.FeedBackListBean;
import com.shenyuan.superapp.admission.bean.PlanListBean;
import com.shenyuan.superapp.admission.bean.SchoolInfoBean;
import com.shenyuan.superapp.admission.bean.StudentListBean;
import com.shenyuan.superapp.base.api.BaseView;

import java.util.List;

/**
 * @author ch
 * @date 2021/3/8 11:03
 * desc
 */
public interface SearchView extends BaseView {
    /**
     * 搜索学校
     *
     * @param o o
     */
    void onSearchSchoolList(List<SchoolInfoBean> o);


    /**
     * 搜索生源
     *
     * @param o o
     */
    void onSearchStudentList(List<StudentListBean> o);

    /**
     * 搜索方案
     *
     * @param o o
     */
    void onSearchPlanList(List<PlanListBean> o);


    /**
     * 搜索反馈
     *
     * @param o o
     */
    void onSearchFeedList(List<FeedBackListBean> o);

    /**
     * 搜索报账
     *
     * @param o o
     */
    void onSearchClaimList(List<ClaimListBean> o);

    /**
     * 删除搜索历史
     *
     * @param o o
     */
    void onDeleteHistory(String o);


    /**
     * 添加搜索历史
     *
     * @param o o
     */
    void onAddHistory(String o);

    /**
     * 搜索历史
     *
     * @param o o
     */
    void onHistoryList(List<String> o);
}

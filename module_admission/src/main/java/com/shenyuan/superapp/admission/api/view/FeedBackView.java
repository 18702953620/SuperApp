package com.shenyuan.superapp.admission.api.view;

import com.shenyuan.superapp.admission.bean.FeedBackInfoBean;
import com.shenyuan.superapp.admission.bean.FeedBackListBean;
import com.shenyuan.superapp.admission.bean.SimpleBean;
import com.shenyuan.superapp.admission.bean.StaffBean;
import com.shenyuan.superapp.base.api.BaseView;

import java.util.List;

/**
 * @author ch
 * @date 2021/3/17 12:00
 * desc
 */
public interface FeedBackView extends BaseView {
    /**
     * 特色活动意向
     *
     * @param o o
     */
    void onIntentList(List<SimpleBean> o);

    /**
     * 喜报是否送达
     *
     * @param o o
     */
    void onGiftList(List<SimpleBean> o);

    /**
     * 挂牌意愿
     *
     * @param o o
     */
    void onListingList(List<SimpleBean> o);

    /**
     * 来校参观意向
     *
     * @param o o
     */
    void onVisitList(List<SimpleBean> o);

    /**
     * 宣传员列表
     *
     * @param o o
     */
    void onPlanStaffList(List<StaffBean> o);

    /**
     * 添加
     *
     * @param o o
     */
    void onAddFeedBack(String o);

    /**
     * 列表
     *
     * @param o o
     */
    void onFeedBackList(List<FeedBackListBean> o);

    /**
     * 反馈详情
     *
     * @param o o
     */
    void onFeedBackInfo(FeedBackInfoBean o);

    /**
     * 修改
     *
     * @param o o
     */
    void onUpdateFeedBack(String o);

    /**
     * 删除
     *
     * @param o o
     */
    void onDeleteFeedBack(String o);

    /**
     * 置顶
     *
     * @param o o
     */
    void onTopFeedBack(String o);
}

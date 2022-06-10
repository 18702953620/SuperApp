package com.shenyuan.superstudent.api.view;

import com.shenyuan.superapp.base.api.BaseView;
import com.shenyuan.superstudent.bean.IntegralListBean;
import com.shenyuan.superstudent.bean.UserInfoBean;

import java.util.List;

/**
 * @author ch
 * @date 2021/3/11 9:22
 * desc
 */
public interface UserView extends BaseView {

    /**
     * 用户信息
     *
     * @param bean bean
     */
    void onUserInfo(UserInfoBean bean);

    void onEditAvatar(String o);

    void onSmsCode(String msgId);

    void onModify(String o);

    void onTotalPoint(Integer o);

    void onPointList(List<IntegralListBean> o);

    void onVisitorMobile(Integer o);
}

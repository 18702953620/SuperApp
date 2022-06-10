package com.shenyuan.superapp.api.view;

import com.shenyuan.superapp.base.api.BaseView;
import com.shenyuan.superapp.bean.UserInfoBean;

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

    void onVisitorMobile(Integer o);
}

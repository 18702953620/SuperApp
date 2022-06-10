package com.shenyuan.superapp.common.api.view;

import com.shenyuan.superapp.base.api.BaseView;
import com.shenyuan.superapp.common.bean.MsgInfoBean;
import com.shenyuan.superapp.common.bean.NewsBean;

/**
 * @author ch
 * @date 2021/3/24 14:35
 * desc
 */
public interface NoticeView extends BaseView {
    void onNoticeInfo(NewsBean o);

    void onMsgInfo(MsgInfoBean o);

    void onMewsInfo(NewsBean o);
}

package com.shenyuan.superstudent.api.view;

import com.shenyuan.superapp.base.api.BaseView;
import com.shenyuan.superstudent.bean.MsgListBean;

import java.util.List;

/**
 * @author ch
 * @date 2021/3/24 15:10
 * desc
 */
public interface MsgView extends BaseView {
    void onNoticeList(List<MsgListBean> o);

    void onDelMsg(String o, int position);

    void onUnRead(Integer o);
}

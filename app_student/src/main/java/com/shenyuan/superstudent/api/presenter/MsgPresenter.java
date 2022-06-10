package com.shenyuan.superstudent.api.presenter;

import com.alibaba.fastjson.JSON;
import com.shenyuan.superapp.base.api.ApiRetrofit;
import com.shenyuan.superapp.base.api.BasePresenter;
import com.shenyuan.superapp.base.api.BaseSubscriber;
import com.shenyuan.superapp.base.api.JsonRequestBody;
import com.shenyuan.superapp.base.utils.LogUtils;
import com.shenyuan.superstudent.api.AppApiServer;
import com.shenyuan.superstudent.api.view.MsgView;
import com.shenyuan.superstudent.bean.MsgListBean;

import java.util.HashMap;
import java.util.List;

/**
 * @author ch
 * @date 2021/3/24 15:11
 * desc
 */
public class MsgPresenter extends BasePresenter<MsgView> {
    private final AppApiServer appServer;

    public MsgPresenter(MsgView baseView) {
        super(baseView);
        appServer = ApiRetrofit.getInstance().getService(AppApiServer.class);
    }


    /**
     * 消息列表
     */
    public void getMsgList(int page) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("limit", "10");
        map.put("page", page);
        LogUtils.e("getNoticeList----" + JSON.toJSONString(map));

        addDisposable(appServer.getMsgList(new JsonRequestBody(map)), new BaseSubscriber<List<MsgListBean>>(baseView) {
            @Override
            public void onSuccess(List<MsgListBean> o) {
                baseView.onNoticeList(o);
            }
        });
    }

    /**
     * 删除消息
     */
    public void delMsg(int id, int position) {

        addDisposable(appServer.delMsg(id), new BaseSubscriber<String>(baseView) {
            @Override
            public void onSuccess(String o) {
                baseView.onDelMsg(o, position);
            }
        });
    }


    /**
     * 未读消息总数
     */
    public void getUnReadMsgCount() {
        addDisposable(appServer.getUnReadMsgCount(), new BaseSubscriber<Integer>(baseView) {
            @Override
            public void onSuccess(Integer o) {
                baseView.onUnRead(o);
            }
        });
    }
}

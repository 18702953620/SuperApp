package com.shenyuan.superapp.api.presenter;

import com.alibaba.fastjson.JSON;
import com.shenyuan.superapp.api.AppApiServer;
import com.shenyuan.superapp.api.view.MsgView;
import com.shenyuan.superapp.base.api.ApiRetrofit;
import com.shenyuan.superapp.base.api.BasePresenter;
import com.shenyuan.superapp.base.api.BaseSubscriber;
import com.shenyuan.superapp.base.utils.LogUtils;
import com.shenyuan.superapp.bean.MsgListBean;

import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

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
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                JSON.toJSONString(map));
        LogUtils.e("getNoticeList----" + JSON.toJSONString(map));

        addDisposable(appServer.getMsgList(requestBody), new BaseSubscriber<List<MsgListBean>>(baseView) {
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
}

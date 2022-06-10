package com.shenyuan.superapp.common.api.presenter;

import com.shenyuan.superapp.base.api.ApiRetrofit;
import com.shenyuan.superapp.base.api.BasePresenter;
import com.shenyuan.superapp.base.api.BaseSubscriber;
import com.shenyuan.superapp.common.api.CommonApiServer;
import com.shenyuan.superapp.common.api.view.NoticeView;
import com.shenyuan.superapp.common.bean.MsgInfoBean;
import com.shenyuan.superapp.common.bean.NewsBean;

/**
 * @author ch
 * @date 2021/3/24 14:36
 * desc
 */
public class NoticePresenter extends BasePresenter<NoticeView> {
    private final CommonApiServer commonApiServer;

    public NoticePresenter(NoticeView baseView) {
        super(baseView);
        commonApiServer = ApiRetrofit.getInstance().getService(CommonApiServer.class);
    }

    /**
     * 公告详情
     */
    public void getNoticeInfo(String id) {
        addDisposable(commonApiServer.getNoticeInfo(id), new BaseSubscriber<NewsBean>(baseView) {
            @Override
            public void onSuccess(NewsBean o) {
                NoticePresenter.this.baseView.onNoticeInfo(o);
            }
        });
    }


    /**
     * 消息详情
     */
    public void getMsgInfo(String id) {
        addDisposable(commonApiServer.getMsgInfo(id), new BaseSubscriber<MsgInfoBean>(baseView) {
            @Override
            public void onSuccess(MsgInfoBean o) {
                NoticePresenter.this.baseView.onMsgInfo(o);
            }
        });
    }

    /**
     * 资讯详情
     */
    public void getNewsInfo(String id) {
        addDisposable(commonApiServer.getNewsInfo(id), new BaseSubscriber<NewsBean>(baseView) {
            @Override
            public void onSuccess(NewsBean o) {
                NoticePresenter.this.baseView.onMewsInfo(o);
            }
        });
    }
}

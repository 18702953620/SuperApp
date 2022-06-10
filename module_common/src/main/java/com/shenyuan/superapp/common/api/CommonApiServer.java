package com.shenyuan.superapp.common.api;

import com.shenyuan.superapp.common.bean.AdvertBean;
import com.shenyuan.superapp.common.bean.MsgInfoBean;
import com.shenyuan.superapp.common.bean.NewsBean;
import com.shenyuan.superapp.common.bean.RegionBean;

import java.util.List;

import io.reactivex.Flowable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * @author ch
 * @date 2021/2/18 15:06
 * desc
 */
public interface CommonApiServer {

    /**
     * 地址列表
     *
     * @return Flowable
     */
    @GET("zsxt/common/regionName/{id}")
    Flowable<List<RegionBean>> regionName(@Path("id") int id);

    /**
     * 下载文件
     */
    @Streaming
    @GET
    Flowable<ResponseBody> downFiles(@Url String url);

    /**
     * 公告详情
     */
    @GET("mhxt/common/zhxyNoticeAndNews/findNotice/{id}")
    Flowable<NewsBean> getNoticeInfo(@Path("id") String id);

    /**
     * 消息详情
     */
    @GET("mhxt/common/zhxyCommunication/findCommunication/{id}")
    Flowable<MsgInfoBean> getMsgInfo(@Path("id") String id);

    /**
     * 资讯详情
     */
    @GET("mhxt/common/zhxyNoticeAndNews/findNewsInfo/{id}")
    Flowable<NewsBean> getNewsInfo(@Path("id") String id);

    /**
     * 获取用户菜单
     */
    @GET("mhxt/common/btn")
    Flowable<List<String>> getUserBtn(@Query("system") String system);


    /**
     * 获取启动页广告
     */
    @GET("mhxt/common/advert/start/loading")
    Flowable<List<AdvertBean>> getLoadingAdvert(@Query("endpoint") String endpoint);

    /**
     * 获取弹窗页广告
     */
    @GET("mhxt/common/advert/home/popup")
    Flowable<List<AdvertBean>> getPopupAdvert(@Query("endpoint") String endpoint);
}

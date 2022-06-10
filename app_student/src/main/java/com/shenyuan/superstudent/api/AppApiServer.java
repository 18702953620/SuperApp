package com.shenyuan.superstudent.api;

import com.shenyuan.superapp.base.api.JsonRequestBody;
import com.shenyuan.superapp.common.bean.NewsBean;
import com.shenyuan.superstudent.bean.BannerBean;
import com.shenyuan.superstudent.bean.IntegralListBean;
import com.shenyuan.superstudent.bean.MenuBean;
import com.shenyuan.superstudent.bean.MsgListBean;
import com.shenyuan.superstudent.bean.NewsTypeBean;
import com.shenyuan.superstudent.bean.SearchResultBean;
import com.shenyuan.superstudent.bean.ServiceBean;
import com.shenyuan.superstudent.bean.UserInfoBean;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Flowable;
import okhttp3.MultipartBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @author ch
 * @date 2020/12/18-15:09
 * desc
 */
public interface AppApiServer {

    /**
     * 登录
     */
    @FormUrlEncoded
    @POST("auth/oauth/token")
    Flowable<HashMap<String, String>> login(@FieldMap HashMap<String, Object> map);

    /**
     * 获取图片验证码
     */
    @GET("auth/verify/get")
    Flowable<HashMap<String, String>> getVerifyImg();

    /**
     * 登出
     */
    @DELETE("auth/oauth/logout")
    Flowable<String> loginOut();

    /**
     * 扫码
     */
    @FormUrlEncoded
    @PUT("auth/qr/login/scan")
    Flowable<String> loginScan(@FieldMap HashMap<String, Object> map);

    /**
     * 扫码确认
     */
    @FormUrlEncoded
    @PUT("auth/qr/login/confirm")
    Flowable<String> loginConfirm(@FieldMap HashMap<String, Object> map);

    /**
     * 个人信息
     */
    @GET("mhxt/common/me")
    Flowable<UserInfoBean> getUserInfo();

    /**
     * 修改密码
     */
    @POST("mhxt/common/modifyPwd")
    Flowable<String> modifyPwd(@Body JsonRequestBody requestBody);

    /**
     * 根据用户名获取手机号
     */
    @POST("mhxt/common/forget")
    @FormUrlEncoded
    Flowable<HashMap<String, String>> getTelByName(@Field("username") String username, @Field("isStuApp") int isStuApp);

    /**
     * 验证图片验证码
     */
    @POST("mhxt/common/verify")
    @FormUrlEncoded
    Flowable<String> verifyCode(@Field("code_id") String code_id, @Field("verify_code") String verify_code);

    /**
     * 发送短信
     */
    @POST("mhxt/common/sendSms")
    Flowable<HashMap<String, String>> sendSms(@Body JsonRequestBody requestBody);

    /**
     * 发送短信
     */
    @POST("mhxt/common/sendNewMobileSms")
    Flowable<HashMap<String, String>> sendNewMobileSms(@Body JsonRequestBody requestBody);

    /**
     * 修改手机号
     */
    @POST("mhxt/common/modifyPhone")
    Flowable<String> modifyPhone(@Body JsonRequestBody requestBody);

    /**
     * 验证短信验证码
     */
    @POST("mhxt/common/veriSmsCode")
    Flowable<String> veriSmsCode(@Body JsonRequestBody requestBody);


    /**
     * 忘记密码
     */
    @POST("mhxt/common/resetpwd")
    Flowable<String> resetPwd(@Body JsonRequestBody requestBody);

    /**
     * 轮播图
     */
    @POST("mhxt/common/zhxyNoticeAndNews/findBannerList")
    Flowable<List<BannerBean>> getBannerList(@Body JsonRequestBody requestBody);

    /**
     * 资讯类型
     */
    @POST("mhxt/common/zhxyNoticeAndNews/appFindNewsTypeList")
    Flowable<List<NewsTypeBean>> getNewsTypeList(@Body JsonRequestBody requestBody);

    /**
     * 资讯列表
     */
    @POST("mhxt/common/zhxyNoticeAndNews/findNewsList")
    Flowable<List<NewsBean>> getNewsList(@Body JsonRequestBody requestBody);

    /**
     * 公告列表
     */
    @POST("mhxt/common/zhxyNoticeAndNews/findNoticeList")
    Flowable<List<NewsBean>> getNoticeList(@Body JsonRequestBody requestBody);

    /**
     * 所有服务
     */
    @GET("mhxt/common/serviceApp/stu/list")
    Flowable<List<ServiceBean>> getAllService();

    /**
     * 我的服务
     */
    @POST("mhxt/common/service/stuServices")
    Flowable<List<MenuBean>> getMyService();

    /**
     * 修改我的服务
     */
    @POST("mhxt/common/service/app/stu/modify")
    Flowable<String> editMyService(@Body JsonRequestBody requestBody);

    /**
     * 搜索
     */
    @GET("mhxt/common/search/stu/query")
    Flowable<SearchResultBean> search(@Query("keyWords") String keyWords, @Query("isSearch") int isSearch, @Query("limit") int limit);

    /**
     * 搜索历史
     */
    @GET("mhxt/common/search/getSearchHis")
    Flowable<List<String>> searchHistory(@Query("limit") int limit);

    /**
     * 搜索发现
     */
    @GET("mhxt/common/search/getSearchFind")
    Flowable<List<String>> searchFind(@Query("limit") int limit);

    /**
     * 搜索删除
     */
    @POST("mhxt/common/search/deleteSearchHis")
    Flowable<String> searchDelete();

    /**
     * 修改头像
     */
    @POST("mhxt/common/modifyAvatar")
    @Multipart
    Flowable<String> editAvatar(@Part MultipartBody.Part parts);

    /**
     * 获取最新版本
     */
    @GET("mhxt/common/findAppMaxVersion/{devVersionCode}/{sys}")
    Flowable<HashMap<String, String>> findAppMaxVersion(@Path("devVersionCode") int code, @Path("sys") String sys);

    /**
     * 消息列表
     */
    @POST("mhxt/common/zhxyCommunication/findCommunicationList")
    Flowable<List<MsgListBean>> getMsgList(@Body JsonRequestBody requestBody);

    /**
     * 未读消息总数
     */
    @GET("mhxt/common/zhxyCommunication/findNoReadCount")
    Flowable<Integer> getUnReadMsgCount();

    /**
     * 删除消息
     */
    @GET("mhxt/common/zhxyCommunication/delCommunication/{id}")
    Flowable<String> delMsg(@Path("id") int id);

    /**
     * 我的积分总数
     */
    @GET("mhxt/common/points/my/total")
    Flowable<Integer> getTotalPoint();

    /**
     * 我的积分详情
     */
    @GET("mhxt/common/points/my/detail")
    Flowable<List<IntegralListBean>> getPointDetail(@Query("limit") int limit, @Query("page") int page);

    /**
     * 游客登录
     */
    @FormUrlEncoded
    @POST("auth/oauth/token/visitor")
    Flowable<HashMap<String, String>> visitorVerify(@FieldMap HashMap<String, Object> map);

    /**
     * 验证手机号 是否存在
     */
    @GET("mhxt/common/check/mobile")
    Flowable<Integer> visitorMobile(@Query("mobile") String mobile, @Query("endpoint") String endpoint);

}

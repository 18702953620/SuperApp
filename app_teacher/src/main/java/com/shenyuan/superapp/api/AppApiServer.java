package com.shenyuan.superapp.api;

import com.shenyuan.superapp.bean.BannerBean;
import com.shenyuan.superapp.bean.ExamStudentInfoBean;
import com.shenyuan.superapp.bean.MenuBean;
import com.shenyuan.superapp.bean.MsgListBean;
import com.shenyuan.superapp.bean.NewsTypeBean;
import com.shenyuan.superapp.bean.ProvinceDataBean;
import com.shenyuan.superapp.bean.SearchResultBean;
import com.shenyuan.superapp.bean.ServiceBean;
import com.shenyuan.superapp.bean.UserInfoBean;
import com.shenyuan.superapp.common.bean.NewsBean;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Flowable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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
    Flowable<String> modifyPwd(@Body RequestBody requestBody);

    /**
     * 根据用户名获取手机号
     */
    @POST("mhxt/common/forget")
    @FormUrlEncoded
    Flowable<HashMap<String, String>> getTelByName(@Field("username") String username);

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
    Flowable<HashMap<String, String>> sendSms(@Body RequestBody requestBody);

    /**
     * 发送短信
     */
    @POST("mhxt/common/sendNewMobileSms")
    Flowable<HashMap<String, String>> sendNewMobileSms(@Body RequestBody requestBody);

    /**
     * 修改手机号
     */
    @POST("mhxt/common/modifyPhone")
    Flowable<String> modifyPhone(@Body RequestBody requestBody);

    /**
     * 验证短信验证码
     */
    @POST("mhxt/common/veriSmsCode")
    Flowable<String> veriSmsCode(@Body RequestBody requestBody);


    /**
     * 忘记密码
     */
    @POST("mhxt/common/resetpwd")
    Flowable<String> resetPwd(@Body RequestBody requestBody);

    /**
     * 轮播图
     */
    @POST("mhxt/common/zhxyNoticeAndNews/findBannerList")
    Flowable<List<BannerBean>> getBannerList(@Body RequestBody requestBody);

    /**
     * 资讯类型
     */
    @POST("mhxt/common/zhxyNoticeAndNews/appFindNewsTypeList")
    Flowable<List<NewsTypeBean>> getNewsTypeList(@Body RequestBody requestBody);

    /**
     * 资讯列表
     */
    @POST("mhxt/common/zhxyNoticeAndNews/findNewsList")
    Flowable<List<NewsBean>> getNewsList(@Body RequestBody requestBody);

    /**
     * 公告列表
     */
    @POST("mhxt/common/zhxyNoticeAndNews/findNoticeList")
    Flowable<List<NewsBean>> getNoticeList(@Body RequestBody requestBody);

    /**
     * 所有服务
     */
    @GET("mhxt/common/serviceApp/list")
    Flowable<List<ServiceBean>> getAllService();

    /**
     * 我的服务
     */
    @POST("mhxt/common/service/myServices")
    Flowable<List<MenuBean>> getMyService();

    /**
     * 修改我的服务
     */
    @POST("mhxt/common/service/app/modify")
    Flowable<String> editMyService(@Body RequestBody requestBody);

    /**
     * 搜索
     */
    @GET("mhxt/common/search/query")
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
    Flowable<List<MsgListBean>> getMsgList(@Body RequestBody requestBody);

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
     * 获取学生详情
     */
    @POST("zsxt/exam/zsxtStuIndepExam/indepExam")
    @FormUrlEncoded
    Flowable<ExamStudentInfoBean> getStudentInfo(@Field("uuid") String uuid);

    /**
     * 拒绝效验
     */
    @POST("zsxt/exam/zsxtStuIndepExam/refuseValid")
    @FormUrlEncoded
    Flowable<String> refuseValid(@Field("uuid") String uuid, @Field("resion") String resion);

    /**
     * 通过效验
     */
    @POST("zsxt/exam/zsxtStuIndepExam/passValid")
    @FormUrlEncoded
    Flowable<String> passValid(@Field("uuid") String uuid);

    /**
     * 验证手机号 是否存在
     */
    @GET("mhxt/common/check/mobile")
    Flowable<Integer> visitorMobile(@Query("mobile") String mobile, @Query("endpoint") String endpoint);

    /**
     * 学校库占比
     */
    @GET("zsxt/zsxtBigData/zsxtBigData/schoolTypePer")
    Flowable<HashMap<String, String>> getSchoolTypePer();

    /**
     * 学校库省份分布
     */
    @GET("zsxt/zsxtBigData/zsxtBigData/schoolProvince")
    Flowable<List<ProvinceDataBean>> getSchoolProvince();
}

package com.shenyuan.superapp.admission.api;

import com.shenyuan.superapp.admission.bean.AreaBean;
import com.shenyuan.superapp.admission.bean.AreaUserBean;
import com.shenyuan.superapp.admission.bean.ClaimInfoBean;
import com.shenyuan.superapp.admission.bean.ClaimListBean;
import com.shenyuan.superapp.admission.bean.CreaterBean;
import com.shenyuan.superapp.admission.bean.FeedBackInfoBean;
import com.shenyuan.superapp.admission.bean.FeedBackListBean;
import com.shenyuan.superapp.admission.bean.FileTreeBean;
import com.shenyuan.superapp.admission.bean.MajorBean;
import com.shenyuan.superapp.admission.bean.PlanInfoBean;
import com.shenyuan.superapp.admission.bean.PlanListBean;
import com.shenyuan.superapp.admission.bean.SchoolInfoBean;
import com.shenyuan.superapp.admission.bean.SchoolListBean;
import com.shenyuan.superapp.admission.bean.SchoolTypeBean;
import com.shenyuan.superapp.admission.bean.SimpleBean;
import com.shenyuan.superapp.admission.bean.SimpleStringBean;
import com.shenyuan.superapp.admission.bean.StaffBean;
import com.shenyuan.superapp.admission.bean.StudentInfoBean;
import com.shenyuan.superapp.admission.bean.StudentListBean;
import com.shenyuan.superapp.admission.bean.TemSchoolBean;
import com.shenyuan.superapp.admission.bean.YearBean;
import com.shenyuan.superapp.base.api.JsonRequestBody;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Flowable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * @author ch
 * @date 2021/2/18 10:02
 * desc
 */
public interface SchoolApiServer {
    /**
     * 区域列表
     */
    @GET("zsxt/common/listAreaId")
    Flowable<List<AreaBean>> getAreaList();

    /**
     * 信息录入人列表
     */
    @POST("zsxt/school/classSchool/creatName")
    Flowable<List<CreaterBean>> getCreatNameList();

    /**
     * 分配人列表
     */
    @GET("zsxt/school/targetSchool/listDistributionName")
    Flowable<List<CreaterBean>> getDisNameList();

    /**
     * 是否挂牌
     */
    @GET("zsxt/common/listTrueOrFalse")
    Flowable<List<SimpleBean>> isLising();

    /**
     * 是否维护
     */
    @GET("zsxt/school/classSchool/listMaintenanceStatus")
    Flowable<List<SimpleBean>> getSchoolState();

    /**
     * 学校层次
     */
    @GET("zsxt/common/listSchoolLevel")
    Flowable<SchoolTypeBean> getSchoolLevel();


    /**
     * 区域负责人
     */
    @GET("zsxt/common/listUserArea")
    Flowable<List<AreaUserBean>> getUserArea();

    /**
     * 区域负责人
     */
    @GET("zsxt/school/classSchool/listAreaIdAdd")
    Flowable<List<AreaUserBean>> getAddUserArea();

    /**
     * 宣传员
     */
    @GET("zsxt/common/listStaffNameLike")
    Flowable<List<StaffBean>> getStaffList();

    /**
     * 排序
     */
    @GET("zsxt/common/listOrderType")
    Flowable<List<SimpleBean>> getSortList();

    /**
     * 性别
     */
    @GET("zsxt/common/listGrade")
    Flowable<List<SimpleBean>> getSexList();

    /**
     * 年级
     */
    @GET("zsxt/common/listGender")
    Flowable<List<SimpleBean>> getGenderList();

    /**
     * 招生层次
     */
    @GET("zsxt/common/listSchoolStuLevel")
    Flowable<List<SimpleBean>> getSchoolLevelList();

    /**
     * 生源意向
     */
    @GET("zsxt/common/listStuTarget")
    Flowable<List<SimpleBean>> getSchoolTargetList();

    /**
     * 生源来源
     */
    @GET("zsxt/common/listStudentSource")
    Flowable<List<SimpleBean>> getStudentSourceList();

    /**
     * 意向专业
     */
    @GET("zsxt/common/listStudentTarget")
    Flowable<List<MajorBean>> getStudentMajorList();

    /**
     * 毕业年份
     */
    @GET("zsxt/common/listGraduateYear")
    Flowable<List<YearBean>> getStudentYearList();

    /**
     * 所属科类
     */
    @GET("zsxt/common/listSubject")
    Flowable<List<SimpleBean>> getSubjectList();

    /**
     * 意向程度
     */
    @GET("zsxt/common/listTargetDegree")
    Flowable<List<SimpleBean>> getTargetDegreeList();

    /**
     * 学校库列表
     */
    @POST("zsxt/school/classSchool/page")
    Flowable<List<SchoolListBean>> getSchoolList(@Body JsonRequestBody requestBody);

    /**
     * 学校名称模糊搜索
     */
    @GET("zsxt/common/schoolname")
    Flowable<List<SchoolInfoBean>> getSchoolListLikeName(@Query("schoolName") String schoolName, @Query("state") int state);

    /**
     * 学校名称模糊搜索
     */
    @GET("zsxt/common/schoolname")
    Flowable<List<SchoolInfoBean>> getSchoolListLikeName(@Query("schoolName") String schoolName,
                                                         @Query("state") int state, @Query("limit") int limit, @Query("pageNo") int pageNo);

    /**
     * 生源名称模糊搜索
     */
    @GET("zsxt/student/srcStudent/search")
    Flowable<List<StudentListBean>> getStudentListLikeName(@Query("name") String name);

    /**
     * 生源名称模糊搜索
     */
    @GET("zsxt/student/myStudent/search")
    Flowable<List<StudentListBean>> getStudentListLikeNameByMy(@Query("name") String name);

    /**
     * 生源名称模糊搜索
     */
    @GET("zsxt/student/returnStu/search")
    Flowable<List<StudentListBean>> getStudentListLikeNameByReturn(@Query("name") String name);

    /**
     * 方案名称模糊搜索
     */
    @POST("zsxt/assist/zsxtPropgtPlan/listPlan/{name}")
    Flowable<List<PlanListBean>> getPlanListLikeName(@Path("name") String name);

    /**
     * 反馈名称模糊搜索
     */
    @POST("zsxt/assist/zsxtPropgtFeedback/listFeedback/{name}")
    Flowable<List<FeedBackListBean>> getFeedListLikeName(@Path("name") String name);

    /**
     * 报账名称模糊搜索
     */
    @POST("zsxt/assist/zsxtPropgtClaim/listClaim/{name}")
    Flowable<List<ClaimListBean>> getClaimListLikeName(@Path("name") String name);

    /**
     * 目标学校列表
     */
    @POST("zsxt/school/targetSchool/page")
    Flowable<List<SchoolListBean>> getTargetSchoolList(@Body JsonRequestBody requestBody);

    /**
     * 退回库
     */
    @POST("zsxt/school/returnSchool/page")
    Flowable<List<SchoolListBean>> getBackSchoolList(@Body JsonRequestBody requestBody);


    /**
     * 添加学校
     */
    @POST("zsxt/school/classSchool/add")
    Flowable<String> addSchool(@Body JsonRequestBody requestBody);

    /**
     * 添加临时学校
     */
    @POST("zsxt/student/srcStudent/temporaryAdd")
    @FormUrlEncoded
    Flowable<TemSchoolBean> addTemSchool(@FieldMap HashMap<String, Object> map);

    /**
     * 修改
     */
    @POST("zsxt/school/classSchool/update")
    Flowable<String> updateSchool(@Body JsonRequestBody requestBody);

    /**
     * 修改
     */
    @POST("zsxt/student/srcStudent/update")
    Flowable<String> updateStudent(@Body RequestBody requestBody);

    /**
     * 修改
     */
    @POST("zsxt/student/myStudent/update")
    Flowable<String> updateMyStudent(@Body JsonRequestBody requestBody);

    /**
     * 分配目标学校
     */
    @POST("zsxt/school/classSchool/distribution")
    @FormUrlEncoded
    Flowable<String> distributionSchool(@FieldMap HashMap<String, Object> map);

    /**
     * 删除学校
     */
    @POST("zsxt/school/classSchool/remove")
    @FormUrlEncoded
    Flowable<String> deleteSchool(@Field("schoolIds") String schoolIds);

    /**
     * 根据id查询学校
     */
    @POST("zsxt/school/classSchool/get/{schoolId}")
    Flowable<SchoolInfoBean> getSchoolById(@Path("schoolId") String schoolId);

    /**
     * 根据id查询学校
     */
    @POST("zsxt/school/targetSchool/get/{schoolId}")
    Flowable<SchoolInfoBean> getTargetSchoolById(@Path("schoolId") String schoolId);

    /**
     * 根据id查询学校
     */
    @POST("zsxt/school/returnSchool/get/{schoolId}")
    Flowable<SchoolInfoBean> getReturnSchoolById(@Path("schoolId") String schoolId);


    /**
     * 根据id查询学生
     */
    @GET("zsxt/student/srcStudent/get/{studentId}")
    Flowable<StudentInfoBean> getStudentById(@Path("studentId") String studentId);

    /**
     * 根据id查询学生
     */
    @GET("zsxt/student/myStudent/get/{studentId}")
    Flowable<StudentInfoBean> getMyStudentById(@Path("studentId") String studentId);

    /**
     * 根据id查询学生
     */
    @GET("zsxt/student/returnStu/get/{studentId}")
    Flowable<StudentInfoBean> getReturnStudentById(@Path("studentId") String studentId);

    /**
     * 退回
     */
    @POST("zsxt/school/targetSchool/returnBack")
    @FormUrlEncoded
    Flowable<String> returnBackSchool(@Field("schoolId") String schoolId, @Field("resion") String resion);

    /**
     * 退回
     */
    @POST("zsxt/student/myStudent/returnBack")
    @FormUrlEncoded
    Flowable<String> returnBackStudent(@Field("studentId") String studentId, @Field("resion") String resion);

    /**
     * 生源池列表
     */
    @POST("zsxt/student/srcStudent/page")
    Flowable<List<StudentListBean>> getStudentList(@Body JsonRequestBody requestBody);

    /**
     * 我的生源池列表
     */
    @POST("zsxt/student/myStudent/page")
    Flowable<List<StudentListBean>> getMyStudentList(@Body JsonRequestBody requestBody);

    /**
     * 退回池列表
     */
    @POST("zsxt/student/returnStu/page")
    Flowable<List<StudentListBean>> getStudentBackList(@Body JsonRequestBody requestBody);

    /**
     * 添加生源
     */
    @POST("zsxt/student/srcStudent/add")
    Flowable<String> addStudent(@Body JsonRequestBody requestBody);

    /**
     * 分配目标生源
     */
    @POST("zsxt/student/srcStudent/distribution")
    @FormUrlEncoded
    Flowable<String> distributionStudent(@FieldMap HashMap<String, Object> map);

    /**
     * 删除学校
     */
    @POST("zsxt/student/srcStudent/remove")
    @FormUrlEncoded
    Flowable<String> deleteStudent(@Field("studentIds") String schoolIds);

    /**
     * 添加沟通记录
     */
    @POST("zsxt/common/addCommu")
    @Multipart
    Flowable<String> addCommu(@Part MultipartBody.Part[] parts, @Part("commu") RequestBody commu,
                              @Part("studentId") RequestBody studentId, @Part("targetDegree") RequestBody targetDegree);

    /**
     * 添加搜索历史
     */
    @POST("zsxt/common/addHistory")
    @FormUrlEncoded
    Flowable<String> addSchoolHistory(@Field("name") String name);

    /**
     * 删除搜索历史
     */
    @POST("zsxt/common/deleteHistory")
    Flowable<String> deleteSchoolHistory();

    /**
     * 搜索历史列表
     */
    @GET("zsxt/common/listHistory")
    Flowable<List<String>> getSchoolHistoryList();


    /**
     * 资料列表
     */
    @POST("zsxt/data/zsxtDataStore/list")
    @FormUrlEncoded
    Flowable<List<FileTreeBean>> getFileList(@Field("name") String name);

    /**
     * 添加文件夹
     */
    @POST("zsxt/data/zsxtDataStore/createFolder")
    Flowable<String> addDir(@Body JsonRequestBody requestBody);

    /**
     * 删除
     */
    @GET("zsxt/data/zsxtDataStore/deleted")
    Flowable<String> deleteFile(@Query("id") int id);


    /**
     * 修改文件夹
     */
    @POST("zsxt/data/zsxtDataStore/update")
    Flowable<String> updateDir(@Body JsonRequestBody requestBody);

    /**
     * 上传文件
     */
    @POST("zsxt/data/zsxtDataStore/addFile/{parentId}")
    @Multipart
    Flowable<String> addFile(@Part MultipartBody.Part parts, @Path("parentId") int parentId);


    /**
     * 招生方案审核状态
     */
    @POST("zsxt/assist/zsxtPropgtPlan/statusDict")
    Flowable<List<SimpleBean>> getExamineStateList();

    /**
     * 招生方案-出差任务
     */
    @POST("zsxt/assist/zsxtPropgtPlan/taskTypeDict")
    Flowable<List<SimpleBean>> getPlanTaskList();

    /**
     * 招生方案-创建时间
     */
    @POST("zsxt/assist/zsxtPropgtPlan/timePartDict")
    Flowable<List<SimpleStringBean>> getPlanCreateList();

    /**
     * 招生方案-经费类型
     */
    @POST("zsxt/assist/zsxtPropgtPlan/feeTypeDict")
    Flowable<List<SimpleStringBean>> getFeeTypeList();

    /**
     * 招生方案-宣传方式
     */
    @POST("zsxt/assist/zsxtPropgtPlan/propagationWayDict")
    Flowable<List<SimpleStringBean>> getPropagationWayDictList();

    /**
     * 招生方案-是否制作喜报
     */
    @GET("zsxt/assist/zsxtPropgtPlan/isMade")
    Flowable<List<SimpleBean>> getMadeList();

    /**
     * 招生方案-宣传员列表
     */
    @GET("zsxt/assist/zsxtPropgtPlan/listStaffNameLike")
    Flowable<List<StaffBean>> getPlanStaffList();

    /**
     * 方案列表
     */
    @POST("zsxt/assist/zsxtPropgtPlan/findPlanList")
    Flowable<List<PlanListBean>> getPlanList(@Body JsonRequestBody requestBody);

    /**
     * 添加方案
     */
    @POST("zsxt/assist/zsxtPropgtPlan/add")
    Flowable<String> addPlan(@Body JsonRequestBody requestBody);

    /**
     * 审核方案
     */
    @POST("zsxt/assist/zsxtPropgtPlan/aduit")
    Flowable<String> aduitPlan(@Body JsonRequestBody requestBody);

    /**
     * 根据id查询方案
     */
    @POST("zsxt/assist/zsxtPropgtPlan/findPlanInfo/{id}")
    Flowable<PlanInfoBean> getPlanById(@Path("id") String id);

    /**
     * 修改方案
     */
    @POST("zsxt/assist/zsxtPropgtPlan/update")
    Flowable<String> updatePlan(@Body JsonRequestBody requestBody);


    /**
     * 招生反馈-特色活动意向
     */
    @GET("zsxt/assist/zsxtPropgtFeedback/activitiesIntention")
    Flowable<List<SimpleBean>> getIntentList();

    /**
     * 招生反馈-喜报是否送达
     */
    @GET("zsxt/assist/zsxtPropgtFeedback/isGleeDelivered")
    Flowable<List<SimpleBean>> getGiftList();

    /**
     * 招生反馈-挂牌意愿
     */
    @GET("zsxt/assist/zsxtPropgtFeedback/listedIntention")
    Flowable<List<SimpleBean>> getListingList();

    /**
     * 招生反馈-来校参观意向
     */
    @GET("zsxt/assist/zsxtPropgtFeedback/visitIntention")
    Flowable<List<SimpleBean>> getVisitList();

    /**
     * 招生反馈-添加
     */
    @POST("zsxt/assist/zsxtPropgtFeedback/add")
    Flowable<String> addFeedBack(@Body JsonRequestBody requestBody);

    /**
     * 招生反馈-添加
     */
    @POST("zsxt/assist/zsxtPropgtFeedback/update")
    Flowable<String> updateFeedBack(@Body JsonRequestBody requestBody);

    /**
     * 招生反馈-列表
     */
    @POST("zsxt/assist/zsxtPropgtFeedback/findFeedbackList")
    Flowable<List<FeedBackListBean>> getFeedBackList(@Body JsonRequestBody requestBody);

    /**
     * 招生反馈-详情
     */
    @POST("zsxt/assist/zsxtPropgtFeedback/findFeedbackInfo/{id}")
    Flowable<FeedBackInfoBean> getFeedBackById(@Path("id") int id);

    /**
     * 招生报账-详情
     */
    @POST("zsxt/assist/zsxtPropgtClaim/findClaimInfo/{id}")
    Flowable<ClaimInfoBean> getClaimById(@Path("id") int id);

    /**
     * 招生反馈-置顶
     */
    @GET("zsxt/assist/zsxtPropgtFeedback/sort/{id}")
    Flowable<String> topFeedBackById(@Path("id") int id);


    /**
     * 删除反馈
     */
    @POST("zsxt/assist/zsxtPropgtFeedback/delete")
    Flowable<String> deleteFeed(@Body JsonRequestBody requestBody);

    /**
     * 出差报账-添加
     */
    @POST("zsxt/assist/zsxtPropgtClaim/add")
    Flowable<String> addClaim(@Body JsonRequestBody requestBody);

    /**
     * 预算列表
     */
    @POST("zsxt/assist/zsxtPropgtClaim/findPlanListByCondition")
    Flowable<List<PlanListBean>> getPlanByCondition(@Body JsonRequestBody requestBody);

    /**
     * 报账列表
     */
    @POST("zsxt/assist/zsxtPropgtClaim/findClaimList")
    Flowable<List<ClaimListBean>> getClaimList(@Body JsonRequestBody requestBody);

    /**
     * 审核报账
     */
    @POST("zsxt/assist/zsxtPropgtClaim/aduit")
    Flowable<String> aduitClaim(@Body JsonRequestBody requestBody);


    /**
     * 删除报账
     */
    @POST("zsxt/assist/zsxtPropgtClaim/delete")
    Flowable<String> deleteClaim(@Body JsonRequestBody requestBody);

    /**
     * 招生报账-置顶
     */
    @GET("zsxt/assist/zsxtPropgtClaim/sort/{id}")
    Flowable<String> topClaimById(@Path("id") int id);

    /**
     * 修改报账
     */
    @POST("zsxt/assist/zsxtPropgtClaim/update")
    Flowable<String> updateClaim(@Body JsonRequestBody requestBody);
}

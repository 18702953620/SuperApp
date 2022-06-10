package com.shenyuan.superapp.base.api.common;

/**
 * @author ch
 * @date 2020/12/18-16:29
 * desc 常用常量
 */
public class AppConstant {
    //刘欢乐
//    public static final String BASE_SERVER_URL = "http://192.168.20.105:8801/";
    //王浩
    public static final String BASE_SERVER_URL = "http://192.168.20.82:8801/";
//    卫江波
//    public static final String BASE_SERVER_URL = "http://192.168.20.21:8801/";
    //王婵婵
//    public static final String BASE_SERVER_URL = "http://192.168.20.150:8801/";

//    public static final String BASE_SERVER_URL = "http://192.168.20.151:8801/";
//    public static final String BASE_SERVER_WEB_URL = "http://192.168.20.151:9527/";

//        public static final String BASE_SERVER_URL = "http://192.168.0.120:8801/";
//    public static final String BASE_SERVER_WEB_URL = "http://192.168.0.120:9527/";
//    public static final String BASE_SERVER_URL = "http://123.138.78.55:8880/";
//    public static final String BASE_SERVER_WEB_URL = "http://192.168.0.120:9527/";

    //    public static final String BASE_SERVER_URL = "http://sc.mdit.edu.cn:8801/";
//    public static final String BASE_SERVER_WEB_URL = "http://sc.mdit.edu.cn:9527/";
//    public static final String BASE_SERVER_URL = "http://smart.jinyegroup.cn:8801/";
    public static final String BASE_SERVER_WEB_URL = "http://smart.jinyegroup.cn:9527/";

//    public static final String BASE_SERVER_URL = "http://presc.mdit.edu.cn:8801/";
//    public static final String BASE_SERVER_WEB_URL = "http://presc.mdit.edu.cn:9527/";
    /**
     * 服务协议
     */
    public static final String SERVICE_AGREEMENT_URL = BASE_SERVER_WEB_URL + "mobilePage/serviceAgreement.html";
    /**
     * 隐私协议
     */
    public static final String PRIVACY_POLICY_URL = BASE_SERVER_WEB_URL + "mobilePage/privacyPolicy.html";
    /**
     * token
     */
    public static final String APP_TOKEN = "APP_TOKEN";
    /**
     * refresh token
     */
    public static final String APP_REFRESH_TOKEN = "APP_REFRESH_TOKEN";
    /**
     * user
     */
    public static final String APP_USER = "APP_USER";
    /**
     * APP_PERMISSION
     */
    public static final String APP_PERMISSION = "APP_PERMISSION";
    /**
     * APP_DOWN_MODEL
     */
    public static final String APP_DOWN_MODEL = "APP_DOWN_MODEL";
    /**
     * 标识
     */
    public static final String CLIENT_ID = "zhxyAppTch";

    /**
     * 密码
     */
    public static final String CLIENT_SECRET = "123456";
    /**
     * 标识
     */
    public static final String STUDENT_CLIENT_ID = "zhxyAppStu";

    /**
     * 密码
     */
    public static final String STUDENT_CLIENT_SECRET = "123456";

    /**
     * 用户类型
     */
    public static final String USER_TYPE = "staff";

    /**
     * 目标学校
     */
    public static final int FORM_WAY_TARGET_SCHOOL = 1;
    /**
     * 退回库
     */
    public static final int FORM_WAY_BACK_SCHOOL = 2;
    /**
     * 退回池
     */
    public static final int FORM_WAY_BACK_STUDENT = 2;
    /**
     * 我的生源
     */
    public static final int FORM_WAY_MY_STUDENT = 3;
}

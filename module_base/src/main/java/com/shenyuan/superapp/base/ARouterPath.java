package com.shenyuan.superapp.base;

import android.app.Activity;
import android.text.TextUtils;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.launcher.ARouter;
import com.shenyuan.superapp.base.api.common.BaseCommon;

/**
 * @author ch
 * @date 2020/12/17-13:21
 * desc 所有页面对应的路径
 */
public class ARouterPath {
    /**
     * 路由跳转
     *
     * @param router router
     */
    public static void router(String router) {
        if (TextUtils.isEmpty(router)) {
            showToast(R.string.more_arouter_error);
        }
        if ("undefined/undefined".equals(router)) {
            showToast(R.string.unopend);
            return;
        }

        if (!router.startsWith("/")) {
            ARouter.getInstance().build("/" + router).navigation();
        } else {
            ARouter.getInstance().build(router).navigation();
        }
    }

    /**
     * 路由跳转
     *
     * @param router     router
     * @param activity   activity
     * @param resultCode resultCode
     */
    public static void router(String router, Activity activity, int resultCode) {
        if (TextUtils.isEmpty(router)) {
            showToast(R.string.more_arouter_error);
        }
        if ("undefined/undefined".equals(router)) {
            showToast(R.string.unopend);
            return;
        }
        ARouter.getInstance().build(router).navigation(activity, resultCode);
    }


    private static void showToast(int res) {
        Toast.makeText(BaseCommon.getAppContext(), BaseCommon.getAppContext().getString(res), Toast.LENGTH_SHORT).show();
    }

    /**
     * 教工端
     */
    public static class AppTeacher {
        /**
         * app-主页
         */
        public static final String APP_MAIN = "/app/main";
        /**
         * app-登录
         */
        public static final String APP_LOGIN = "/app/login";
        /**
         * app-扫码授权
         */
        public static final String APP_AUTH = "/app/auth";
        /**
         * app-搜索
         */
        public static final String APP_SEARCH = "/app/search";
        /**
         * app-忘记密码
         */
        public static final String APP_FORGOT_PASSWORD = "/app/forgot_password";
        /**
         * app-忘记密码-拨打热线
         */
        public static final String APP_FORGOT_PASSWORD_HOTLINE = "/app/forgot_password_hotline";
        /**
         * app-忘记密码-验证码
         */
        public static final String APP_FORGOT_PASSWORD_VERIFY = "/app/forgot_password_verify";
        /**
         * app-忘记密码-短信验证码
         */
        public static final String APP_FORGOT_PASSWORD_MESSAGE = "/app/forgot_password_message";
        /**
         * app-忘记密码-短信验证码
         */
        public static final String APP_FORGOT_PASSWORD_RESET = "/app/forgot_password_reset";
        /**
         * app-修改密码
         */
        public static final String APP_EDIT_PASSWORD = "/app/edit_password";
        /**
         * app-修改手机号
         */
        public static final String APP_EDIT_TEL = "/app/edit_tel";
        /**
         * app-个人信息
         */
        public static final String APP_USER_INFO = "/app/user_info";
        /**
         * app-更多功能
         */
        public static final String APP_MORE = "/app/more";
        /**
         * app-关于我们
         */
        public static final String APP_ABOUT = "/app/about";
        /**
         * app 考试信息
         */
        public static final String APP_EXAM_INFO = "/app/exam/info";
        /**
         * app 考试信息-拒绝
         */
        public static final String APP_EXAM_REFUSE = "/app/exam/refuse";


        /**
         * app-广告
         */
        public static final String APP_ADVERT = "/app/advert";

        /**
         * app-大数据
         */
        public static final String APP_BIG_DATA = "/app/data";

        /**
         * 招生大数据
         */
        public static final String App_DATA_ADMISSION = "/app/data/admission";

    }

    /**
     * 公共模块
     */
    public static class Common {
        /**
         * common-web
         */
        public static final String COMMON_WEB = "/common/web";
        /**
         * common- net - error
         */
        public static final String COMMON_NET_ERROR = "/common/net/error";
        /**
         * common- net - error
         */
        public static final String COMMON_NET_ERROR_STUDENT = "/common/net/error/student";
        /**
         * common- video
         */
        public static final String COMMON_VIDEO = "/common/video";
        /**
         * common- file
         */
        public static final String COMMON_FILE = "/common/file";
    }

    /**
     * 招生系统
     */
    public static class Admission {
        /**
         * 学校库
         */
        public static final String ADMISSION_SCHOOLS = "/admission/schools";
        /**
         * 学校库-分配目标学校
         */
        public static final String ADMISSION_SCHOOLS_DISTRITION = "/admission/schools/distrition";
        /**
         * 学校库-分配宣传员
         */
        public static final String ADMISSION_DISTRITION_PERSON = "/admission/distrition/person";
        /**
         * 学校库-退回库
         */
        public static final String ADMISSION_SCHOOLS_BACK = "/admission/schools/back";
        /**
         * 学校库-删除
         */
        public static final String ADMISSION_SCHOOLS_DELETE = "/admission/schools/delete";
        /**
         * 学校库-添加
         */
        public static final String ADMISSION_SCHOOLS_ADD = "/admission/schools/add";

        /**
         * 目标学校
         */
        public static final String ADMISSION_TARGET_SCHOOLS = "/admission/target/schools";

        /**
         * 生源池
         */
        public static final String ADMISSION_STUDENT_POOL = "/admission/student/pool";
        /**
         * 生源池-删除
         */
        public static final String ADMISSION_STUDENT_DELETE = "/admission/student/delete";
        /**
         * 生源池-添加
         */
        public static final String ADMISSION_STUDENT_POOL_ADD = "/admission/student/add";

        /**
         * 生源池/学校库-搜索
         */
        public static final String ADMISSION_SCHOOLS_SEARCH = "/admission/search";
        /**
         * 生源池-添加-学校库-搜索
         */
        public static final String ADMISSION_STUDENT_ADD_SEARCH = "/admission/student/add/search";
        /**
         * 生源池-生源详情
         */
        public static final String ADMISSION_STUDENT_INFO = "/admission/student/info";
        /**
         * 生源池-添加沟通记录
         */
        public static final String ADMISSION_STUDENT_ADD_RECORD = "/admission/student/add/record";

        /**
         * 生源池-分配生源列表
         */
        public static final String ADMISSION_STUDENT_DISTRITION_LIST = "/admission/student/distrition/list";
        /**
         * 生源池-分配生源
         */
        public static final String ADMISSION_STUDENT_DISTRITION = "/admission/student/distrition";

        /**
         * 生源池-退回池
         */
        public static final String ADMISSION_STUDENT_BACK = "/admission/student/back";

        /**
         * 我的生源池
         */
        public static final String ADMISSION_STUDENT_POOL_MY = "/admission/student/pool/my";

        /**
         * 招生资料
         */
        public static final String ADMISSION_FILE_LIST = "/admission/file/list";
        /**
         * 传输列表
         */
        public static final String ADMISSION_FILE_RECORD = "/admission/file/record";
        /**
         * 招生方案-列表
         */
        public static final String ADMISSION_PLAN_LIST = "/admission/plan/list";
        /**
         * 招生方案-添加
         */
        public static final String ADMISSION_PLAN_ADD = "/admission/plan/add";
        /**
         * 招生方案-添加
         */
        public static final String ADMISSION_PLAN_ADD_INFO = "/admission/plan/add/info";
        /**
         * 招生方案-添加线路
         */
        public static final String ADMISSION_PLAN_ADD_LINE = "/admission/plan/add/line";
        /**
         * 招生方案-经费预算
         */
        public static final String ADMISSION_PLAN_ADD_FUND = "/admission/plan/add/fund";
        /**
         * 招生方案-详情
         */
        public static final String ADMISSION_PLAN_INFO = "/admission/plan/info";

        /**
         * 招生反馈-列表
         */
        public static final String ADMISSION_FEEDBACK_LIST = "/admission/feedback/list";
        /**
         * 招生反馈--添加
         */
        public static final String ADMISSION_FEEDBACK_ADD = "/admission/feedback/add";
        /**
         * 招生报账-列表
         */
        public static final String ADMISSION_REIMBURSE_LIST = "/admission/reimburse/list";
        /**
         * 招生报账--添加
         */
        public static final String ADMISSION_REIMBURSE_ADD = "/admission/reimburse/add";
        /**
         * 招生报账--关联预算
         */
        public static final String ADMISSION_REIMBURSE_PLAN = "/admission/reimburse/plan";

    }

    /**
     * 学生端
     */
    public static class AppStudent {
        /**
         * app-主页
         */
        public static final String APP_STUDENT_MAIN = "/student/main";
        /**
         * app-广告
         */
        public static final String APP_STUDENT_ADVERT = "/student/advert";
        /**
         * app - 搜索
         */
        public static final String APP_STUDENT_SEARCH = "/student/search";
        /**
         * app - 更多
         */
        public static final String APP_STUDENT_MORE = "/student/more";
        /**
         * app - 修改密码
         */
        public static final String APP_STUDENT_EDIT_PASSWORD = "/student/edit/password";
        /**
         * app - 修改手机号
         */
        public static final String APP_STUDENT_EDIT_PHONE = "/student/edit/phone";

        /**
         * app - 个人信息
         */
        public static final String APP_STUDENT_USERINFO = "/student/userinfo";
        /**
         * app - 积分
         */
        public static final String APP_STUDENT_INTEGRAL = "/student/integral";
        /**
         * app - 关于我们
         */
        public static final String APP_STUDENT_ABOUTUS = "/student/aboutUs";
        /**
         * app - 登录
         */
        public static final String APP_STUDENT_LOGIN = "/student/login";
        /**
         * app - 游客登录
         */
        public static final String APP_STUDENT_LOGIN_VISTOR = "/student/login/vistor";
        /**
         * app-忘记密码
         */
        public static final String APP_STUDENT_FORGOT_PASSWORD = "/student/forgot_password";
        /**
         * app-忘记密码-拨打热线
         */
        public static final String APP_STUDENT_FORGOT_PASSWORD_HOTLINE = "/student/forgot_password_hotline";
        /**
         * app-忘记密码-验证码
         */
        public static final String APP_STUDENT_FORGOT_PASSWORD_VERIFY = "/student/forgot_password_verify";
        /**
         * app-忘记密码-短信验证码
         */
        public static final String APP_STUDENT_FORGOT_PASSWORD_MESSAGE = "/student/forgot_password_message";
        /**
         * app-忘记密码-短信验证码
         */
        public static final String APP_STUDENT_FORGOT_PASSWORD_RESET = "/student/forgot_password_reset";

    }

}

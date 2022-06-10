package com.shenyuan.superstudent.api.presenter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Base64;

import com.alibaba.fastjson.JSON;
import com.shenyuan.superapp.base.api.ApiRetrofit;
import com.shenyuan.superapp.base.api.BasePresenter;
import com.shenyuan.superapp.base.api.BaseSubscriber;
import com.shenyuan.superapp.base.api.JsonRequestBody;
import com.shenyuan.superapp.base.api.common.AppConstant;
import com.shenyuan.superapp.base.utils.AesEncryptUtil;
import com.shenyuan.superapp.base.utils.LogUtils;
import com.shenyuan.superstudent.api.AppApiServer;
import com.shenyuan.superstudent.api.view.LoginView;

import java.util.HashMap;

/**
 * @author ch
 * @date 2020/12/18-15:06
 * desc 登录相关
 */
public class LoginPresenter extends BasePresenter<LoginView> {

    private final AppApiServer appServer;

    public LoginPresenter(LoginView baseView) {
        super(baseView);
        appServer = ApiRetrofit.getInstance().getService(AppApiServer.class);
    }

    /**
     * 登录
     *
     * @param name 用户名
     * @param pwd  密码
     */
    public void login(String name, String pwd, String code, String code_id) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("username", name);
        map.put("verify_code", code);
        map.put("code_id", code_id);
        map.put("password", AesEncryptUtil.encrypt(pwd));
        map.put("grant_type", "password");
        map.put("user_type", "student");
        map.put("client_id", AppConstant.STUDENT_CLIENT_ID);
        map.put("client_secret", AesEncryptUtil.encrypt(AppConstant.STUDENT_CLIENT_SECRET));

        addDisposable(appServer.login(map), new BaseSubscriber<HashMap<String, String>>(baseView, true) {
            @Override
            public void onSuccess(HashMap<String, String> o) {
                baseView.onLogin(o);
            }
        });
    }

    /**
     * 登出
     */
    public void loginOut() {
        addDisposable(appServer.loginOut(), new BaseSubscriber<String>(baseView) {
            @Override
            public void onSuccess(String o) {
                baseView.onLoginOut();
            }
        });
    }

    /**
     * 获取图片验证码
     */
    public void getVerify() {
        addDisposable(appServer.getVerifyImg(), new BaseSubscriber<HashMap<String, String>>(baseView) {
            @Override
            public void onSuccess(HashMap<String, String> o) {
                if (o != null) {
                    String img = o.get("verify_img");
                    String code_id = o.get("code_id");
                    if (!TextUtils.isEmpty(img)) {
                        byte[] bytes = Base64.decode(img, Base64.DEFAULT);
                        Bitmap myBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        baseView.onVerify(myBitmap, code_id);
                    }
                }
            }
        });
    }

    /**
     * 根据用户名获取手机号
     *
     * @param username username
     */
    public void getTelByName(String username) {
        addDisposable(appServer.getTelByName(username, 1), new BaseSubscriber<HashMap<String, String>>(baseView, true) {
            @Override
            public void onSuccess(HashMap<String, String> o) {
                baseView.onGetUser(o);
            }
        });
    }


    /**
     * 发送短信
     */
    public void sendSms(String tel) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("mobile", tel);
        map.put("msgType", "visitorLogin");
        map.put("isStuApp", "1");

        JsonRequestBody requestBody = new JsonRequestBody(map);
        LogUtils.e("sendSms----" + JSON.toJSONString(map));
        addDisposable(appServer.sendNewMobileSms(requestBody), new BaseSubscriber<HashMap<String, String>>(baseView, true) {
            @Override
            public void onSuccess(HashMap<String, String> o) {
                if (o != null) {
                    baseView.onSmsCode(o.get("msgId"));
                }
            }
        });
    }

    /**
     * 游客登录
     */
    public void visitorVerify(String msgVerifyCode, String msgId) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("msgVerifyCode", msgVerifyCode);
        map.put("msgId", msgId);
        map.put("grant_type", "password");
        map.put("user_type", "student");
        map.put("client_id", AppConstant.STUDENT_CLIENT_ID);
        map.put("client_secret", AesEncryptUtil.encrypt(AppConstant.STUDENT_CLIENT_SECRET));

        addDisposable(appServer.visitorVerify(map), new BaseSubscriber<HashMap<String, String>>(baseView, true) {
            @Override
            public void onSuccess(HashMap<String, String> o) {
                baseView.onLogin(o);
            }
        });
    }

    /**
     * 验证手机号 是否存在
     */
    public void visitorMobile(String mobile) {
        addDisposable(appServer.visitorMobile(mobile, AppConstant.STUDENT_CLIENT_ID),
                new BaseSubscriber<Integer>(baseView, true) {
                    @Override
                    public void onSuccess(Integer o) {
                        baseView.onVisitorMobile(o);
                    }
                });
    }


}

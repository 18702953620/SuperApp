package com.shenyuan.superapp.api.presenter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Base64;

import com.shenyuan.superapp.api.AppApiServer;
import com.shenyuan.superapp.base.api.ApiRetrofit;
import com.shenyuan.superapp.base.api.BasePresenter;
import com.shenyuan.superapp.base.api.BaseSubscriber;
import com.shenyuan.superapp.base.api.common.AppConstant;
import com.shenyuan.superapp.base.utils.AesEncryptUtil;
import com.shenyuan.superapp.api.view.LoginView;

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
        map.put("user_type", "staff");
        map.put("client_id", AppConstant.CLIENT_ID);
//        map.put("client_secret", AppConstant.CLIENT_SECRET);
        map.put("client_secret", AesEncryptUtil.encrypt(AppConstant.CLIENT_SECRET));

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
        addDisposable(appServer.getTelByName(username), new BaseSubscriber<HashMap<String, String>>(baseView, true) {
            @Override
            public void onSuccess(HashMap<String, String> o) {
                baseView.onGetUser(o);
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

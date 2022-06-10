package com.shenyuan.superstudent.api.view;

import android.graphics.Bitmap;

import com.shenyuan.superapp.base.api.BaseView;

import java.util.HashMap;

/**
 * @author ch
 * @date 2020/12/18-15:06
 * desc
 */
public interface LoginView extends BaseView {
    /**
     * 登录成功
     *
     * @param map map
     */
    void onLogin(HashMap<String, String> map);

    /**
     * 登出成功
     */
    void onLoginOut();

    void onVerify(Bitmap myBitmap, String code_id);

    void onGetUser(HashMap<String, String> o);

    void onSmsCode(String o);

    void onVisitorMobile(Integer o);
}

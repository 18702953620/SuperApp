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
import com.shenyuan.superapp.base.utils.LogUtils;
import com.shenyuan.superstudent.api.AppApiServer;
import com.shenyuan.superstudent.api.view.PasswordView;

import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * @author ch
 * @date 2021/3/9 16:18
 * desc
 */
public class PasswordPresenter extends BasePresenter<PasswordView> {
    private final AppApiServer appServer;

    public PasswordPresenter(PasswordView baseView) {
        super(baseView);
        appServer = ApiRetrofit.getInstance().getService(AppApiServer.class);
    }

    /**
     * 修改密码
     *
     * @param map map
     */
    public void modifyPwd(HashMap<String, Object> map) {
        JsonRequestBody requestBody = new JsonRequestBody(map);
        LogUtils.e("modifyPwd----" + JSON.toJSONString(map));
        addDisposable(appServer.modifyPwd(requestBody), new BaseSubscriber<String>(baseView, true) {
            @Override
            public void onSuccess(String o) {
                baseView.onModifyPwd(o);
            }
        });
    }

    /**
     * 验证
     *
     * @param code_id     code_id
     * @param verify_code verify_code
     */
    public void verifyCode(String code_id, String verify_code) {
        addDisposable(appServer.verifyCode(code_id, verify_code), new BaseSubscriber<String>(baseView, true) {
            @Override
            public void onSuccess(String o) {
                baseView.onVerifyCode(o);
            }
        });
    }

    /**
     * 验证短信
     *
     * @param msgId         msgId
     * @param msgVerifyCode msgVerifyCode
     */
    public void veriSmsCode(String msgId, String msgVerifyCode) {
        HashMap<String, String> map = new HashMap<>();
        map.put("msgId", msgId);
        map.put("msgVerifyCode", msgVerifyCode);
        map.put("isStuApp", "1");
        JsonRequestBody requestBody = new JsonRequestBody(map);
        LogUtils.e("sendSms----" + JSON.toJSONString(map));
        addDisposable(appServer.veriSmsCode(requestBody), new BaseSubscriber<String>(baseView, true) {
            @Override
            public void onSuccess(String o) {
                baseView.onVerifySmsCode(o);
            }
        });
    }

    /**
     * 发送短信
     */
    public void sendSms(String tel) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("mobile", tel);
        map.put("msgType", "forget");
        map.put("isStuApp", "1");

        JsonRequestBody requestBody = new JsonRequestBody(map);
        LogUtils.e("sendSms----" + JSON.toJSONString(map));
        addDisposable(appServer.sendSms(requestBody), new BaseSubscriber<HashMap<String, String>>(baseView, true) {
            @Override
            public void onSuccess(HashMap<String, String> o) {
                if (o != null) {
                    baseView.onSmsCode(o.get("msgId"));
                }
            }
        });
    }

    /**
     * 重置密码
     */
    public void resetPwd(HashMap<String, Object> map) {
        JsonRequestBody requestBody = new JsonRequestBody(map);
        LogUtils.e("resetPwd----" + JSON.toJSONString(map));
        addDisposable(appServer.resetPwd(requestBody), new BaseSubscriber<String>(baseView, true) {
            @Override
            public void onSuccess(String o) {
                baseView.onResetPwd(o);
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
                    String codeId = o.get("code_id");
                    if (!TextUtils.isEmpty(img)) {
                        byte[] bytes = Base64.decode(img, Base64.DEFAULT);
                        Bitmap myBitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                        baseView.onVerify(myBitmap, codeId);
                    }
                }
            }
        });
    }
}

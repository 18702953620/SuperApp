package com.shenyuan.superapp.api.presenter;

import com.alibaba.fastjson.JSON;
import com.shenyuan.superapp.api.AppApiServer;
import com.shenyuan.superapp.api.view.UserView;
import com.shenyuan.superapp.base.api.ApiRetrofit;
import com.shenyuan.superapp.base.api.BasePresenter;
import com.shenyuan.superapp.base.api.BaseSubscriber;
import com.shenyuan.superapp.base.api.common.AppConstant;
import com.shenyuan.superapp.base.utils.LogUtils;
import com.shenyuan.superapp.bean.UserInfoBean;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @author ch
 * @date 2021/3/11 9:22
 * desc
 */
public class UserPresenter extends BasePresenter<UserView> {
    private final AppApiServer appServer;

    public UserPresenter(UserView baseView) {
        super(baseView);
        appServer = ApiRetrofit.getInstance().getService(AppApiServer.class);
    }


    /**
     * 个人信息
     */
    public void getUserInfo() {
        addDisposable(appServer.getUserInfo(), new BaseSubscriber<UserInfoBean>(baseView) {
            @Override
            public void onSuccess(UserInfoBean o) {
                baseView.onUserInfo(o);
            }
        });
    }

    /**
     * 修改头像
     *
     * @param path path
     */
    public void editAvatar(String path) {
        if (path == null) {
            return;
        }
        File file = new File(path);
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);

        MultipartBody.Part filePart = null;
        try {

            filePart = MultipartBody.Part.createFormData("file", URLEncoder.encode(file.getName(), "UTF-8"), requestFile);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        addDisposable(appServer.editAvatar(filePart), new BaseSubscriber<String>(baseView) {
            @Override
            public void onSuccess(String o) {
                baseView.onEditAvatar(o);
            }
        });
    }

    /**
     * 发送短信
     */
    public void sendSms(String tel) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("mobile", tel);
        map.put("msgType", "updatePhone");

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                JSON.toJSONString(map));
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
     * 修改手机号
     */
    public void modifyPhone(String msgId, String mobile, String msgVerifyCode) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("mobile", mobile);
        map.put("msgType", "updatePhone");
        map.put("msgVerifyCode", msgVerifyCode);
        map.put("msgId", msgId);

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                JSON.toJSONString(map));
        LogUtils.e("modifyPhone----" + JSON.toJSONString(map));
        addDisposable(appServer.modifyPhone(requestBody), new BaseSubscriber<String>(baseView, true) {
            @Override
            public void onSuccess(String o) {
                if (o != null) {
                    baseView.onModify(o);
                }
            }
        });
    }

    /**
     * 验证手机号 是否存在
     */
    public void visitorMobile(String mobile) {
        addDisposable(appServer.visitorMobile(mobile, AppConstant.CLIENT_ID),
                new BaseSubscriber<Integer>(baseView, true) {
                    @Override
                    public void onSuccess(Integer o) {
                        baseView.onVisitorMobile(o);
                    }
                });
    }
}

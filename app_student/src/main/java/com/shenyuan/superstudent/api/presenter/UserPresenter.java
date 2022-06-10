package com.shenyuan.superstudent.api.presenter;

import com.alibaba.fastjson.JSON;
import com.shenyuan.superapp.base.api.ApiRetrofit;
import com.shenyuan.superapp.base.api.BasePresenter;
import com.shenyuan.superapp.base.api.BaseSubscriber;
import com.shenyuan.superapp.base.api.JsonRequestBody;
import com.shenyuan.superapp.base.api.common.AppConstant;
import com.shenyuan.superapp.base.utils.LogUtils;
import com.shenyuan.superstudent.api.AppApiServer;
import com.shenyuan.superstudent.api.view.UserView;
import com.shenyuan.superstudent.bean.IntegralListBean;
import com.shenyuan.superstudent.bean.UserInfoBean;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;

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

        LogUtils.e("sendSms----" + JSON.toJSONString(map));
        addDisposable(appServer.sendNewMobileSms(new JsonRequestBody(map)), new BaseSubscriber<HashMap<String, String>>(baseView, true) {
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
        map.put("isStuApp", 1);
        LogUtils.e("modifyPhone----" + JSON.toJSONString(map));
        addDisposable(appServer.modifyPhone(new JsonRequestBody(map)), new BaseSubscriber<String>(baseView, true) {
            @Override
            public void onSuccess(String o) {
                if (o != null) {
                    baseView.onModify(o);
                }
            }
        });
    }

    /**
     * 我的积分总数
     */
    public void getTotalPoint() {
        addDisposable(appServer.getTotalPoint(), new BaseSubscriber<Integer>(baseView) {
            @Override
            public void onSuccess(Integer o) {
                if (o != null) {
                    baseView.onTotalPoint(o);
                }
            }
        });
    }

    /**
     * 我的积分总数
     */
    public void getPointDetail(int page) {
        addDisposable(appServer.getPointDetail(10, page), new BaseSubscriber<List<IntegralListBean>>(baseView) {
            @Override
            public void onSuccess(List<IntegralListBean> o) {
                if (o != null) {
                    baseView.onPointList(o);
                }
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

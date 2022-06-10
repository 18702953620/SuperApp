package com.shenyuan.superapp.base.api;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.alibaba.android.arouter.launcher.ARouter;
import com.shenyuan.superapp.base.ARouterPath;
import com.shenyuan.superapp.base.api.common.BaseCommon;
import com.shenyuan.superapp.base.api.common.TokenCommon;
import com.shenyuan.superapp.base.dialog.BaseDialog;
import com.shenyuan.superapp.base.dialog.SignDialog;
import com.shenyuan.superapp.base.utils.AppUtils;
import com.shenyuan.superapp.base.utils.LogUtils;

import java.util.HashMap;


/**
 * @author ch
 * @date 2021/4/6 18:20
 * desc 处理错误
 */
public class TokenHandler extends Handler {
    /**
     * 退出登录
     */
    public final static int LOGIN_OUT = 1003;
    /**
     * 同端登录
     */
    public final static int LOGIN_OUT_WITH_MESSAGE = 1004;
    /**
     * 错误页
     */
    public final static int ERROR_PAGE = 1005;

    private static TokenHandler tokenHandler;


    private volatile boolean isStartErrorPage;

    private volatile boolean isStartLoginPage;

    public static TokenHandler getInstance() {
        if (tokenHandler == null) {
            synchronized (TokenHandler.class) {
                if (tokenHandler == null) {
                    tokenHandler = new TokenHandler();
                }
            }
        }
        return tokenHandler;
    }


    @Override
    public void handleMessage(@NonNull Message msg) {
        LogUtils.e("TokenHandler code = " + msg.what + ", msg = " + msg.obj + ",TokenHandler = " + this.toString());
        synchronized (TokenHandler.class) {
            if (msg.what == LOGIN_OUT) {
                if (isStartLoginPage) {
                    LogUtils.e("TokenHandler isStartLoginPage = " + isStartLoginPage);
                    return;
                }
                isStartLoginPage = true;
                toLoginOut();
            } else if (msg.what == LOGIN_OUT_WITH_MESSAGE) {
                if (isStartLoginPage) {
                    LogUtils.e("TokenHandler isStartLoginPage = " + isStartLoginPage);
                    return;
                }

                HashMap<String, Object> map = (HashMap<String, Object>) msg.obj;
                if (map != null) {
                    String m = (String) map.get("msg");
                    Context context = (Context) map.get("context");
                    isStartLoginPage = true;
                    toLoginOutWithMsg(context, m);
                }

            } else if (msg.what == ERROR_PAGE) {
                if (isStartErrorPage) {
                    LogUtils.e("TokenHandler isStartErrorPage = " + isStartErrorPage);
                    return;
                }
                isStartErrorPage = true;
                toErrorPage();
                LogUtils.e("TokenHandler StartErrorPage, isStartErrorPage = " + isStartErrorPage);
            }
        }
    }

    /**
     * 同端登录
     *
     * @param msg     msg
     * @param context context
     */
    public void sendLoginOutWithMsg(String msg, Context context) {
        Message message = new Message();
        message.what = TokenHandler.LOGIN_OUT_WITH_MESSAGE;
        HashMap<String, Object> map = new HashMap<>();
        map.put("msg", msg);
        map.put("context", context);
        message.obj = map;
        sendMessage(message);
    }

    /**
     * 直接退出登录
     */
    public void sendLoginOutMsg() {
        sendEmptyMessage(LOGIN_OUT);
    }

    /**
     * 去错误页
     */
    public void sendErrorPageMsg() {
        sendEmptyMessage(ERROR_PAGE);
    }

    public void resetErrorState() {
        isStartErrorPage = false;
    }

    public void resetLoginState() {
        isStartLoginPage = false;
    }

    /**
     * 退出登录
     */
    private void toLoginOut() {
        if (!TextUtils.isEmpty(TokenCommon.getToken())) {
            return;
        }

        postDelayed(() -> {
            if (!TextUtils.isEmpty(TokenCommon.getToken())) {
                return;
            }
            String packageName = AppUtils.getAppPackageName(BaseCommon.getAppContext());
            if ("com.shenyuan.superapp".equals(packageName)) {
                ARouter.getInstance().build(ARouterPath.AppTeacher.APP_MAIN).withString("action", "loginOut").navigation();
            } else if ("com.shenyuan.superstudent".equals(packageName)) {
                ARouter.getInstance().build(ARouterPath.AppStudent.APP_STUDENT_MAIN).withString("action", "loginOut").navigation();
            }
        }, 500);
    }

    /**
     * 错误页
     */
    private void toErrorPage() {
        String packageName = AppUtils.getAppPackageName(BaseCommon.getAppContext());
        if ("com.shenyuan.superapp".equals(packageName)) {
            ARouter.getInstance().build(ARouterPath.Common.COMMON_NET_ERROR).navigation();
        } else if ("com.shenyuan.superstudent".equals(packageName)) {
            ARouter.getInstance().build(ARouterPath.Common.COMMON_NET_ERROR_STUDENT).navigation();
        }
    }

    /**
     * 带弹窗的退出登录
     *
     * @param msg msg
     */
    private void toLoginOutWithMsg(Context context, String msg) {
        new SignDialog.Builder(context).title(msg).onBacklistener(new BaseDialog.BaseOnBack() {
            @Override
            public void onConfirm() {
                toLoginOut();
            }
        }).show();
    }
}

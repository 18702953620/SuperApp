package com.shenyuan.superstudent.api.view;

import android.graphics.Bitmap;

import com.shenyuan.superapp.base.api.BaseView;

/**
 * @author ch
 * @date 2021/3/9 16:19
 * desc
 */
public interface PasswordView extends BaseView {
    void onModifyPwd(String o);

    void onVerify(Bitmap myBitmap, String codeId);

    void onVerifyCode(String o);

    void onSmsCode(String o);

    void onVerifySmsCode(String o);

    void onResetPwd(String o);
}

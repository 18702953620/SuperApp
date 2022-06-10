package com.shenyuan.superapp.ui.login;

import android.graphics.Bitmap;
import android.text.TextUtils;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.shenyuan.superapp.R;
import com.shenyuan.superapp.api.presenter.PasswordPresenter;
import com.shenyuan.superapp.api.view.PasswordView;
import com.shenyuan.superapp.base.api.common.TokenCommon;
import com.shenyuan.superapp.base.base.BaseActivity;
import com.shenyuan.superapp.base.utils.AesEncryptUtil;
import com.shenyuan.superapp.base.ARouterPath;
import com.shenyuan.superapp.base.utils.StrUtils;
import com.shenyuan.superapp.databinding.AcEditPasswordBinding;

import java.util.HashMap;

/**
 * @author ch
 * @date 2021/1/18 17:04
 * desc 修改密码
 */
@Route(path = ARouterPath.AppTeacher.APP_EDIT_PASSWORD)
public class EditPasswordActivity extends BaseActivity<AcEditPasswordBinding, PasswordPresenter> implements PasswordView {
    @Override
    protected void setStatusBar() {
        setStatusBarColor(R.color.color_f5f5f5);
    }

    @Override
    protected PasswordPresenter createPresenter() {
        return new PasswordPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.ac_edit_password;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void addListener() {
        binding.tvSubmit.setOnClickListener(v -> {
            if (TextUtils.isEmpty(binding.dtOldPwd.getText())) {
                showToast(getString(R.string.pass_old_not_empty));
                return;
            }
            if (TextUtils.isEmpty(binding.dtNewPwd.getText())) {
                showToast(getString(R.string.pass_new_not_empty));
                return;
            }
            if (!StrUtils.checkPwd(binding.dtNewPwd.getText())) {
                showToast(getString(R.string.pass_new_not_support));
                return;
            }
            if (TextUtils.isEmpty(binding.dtNewPwdAgin.getText())) {
                showToast(getString(R.string.pass_agin_not_empty));
                return;
            }
            if (!binding.dtNewPwd.getText().equals(binding.dtNewPwdAgin.getText())) {
                showToast(getString(R.string.pass_new_not_equals_agin));
                return;
            }


            HashMap<String, Object> map = new HashMap<>();
            map.put("oldPwd", AesEncryptUtil.encrypt(binding.dtOldPwd.getText()));
            map.put("newPwd", AesEncryptUtil.encrypt(binding.dtNewPwd.getText()));
            map.put("newPwdConfirm", AesEncryptUtil.encrypt(binding.dtNewPwdAgin.getText()));

            presenter.modifyPwd(map);
        });
    }

    @Override
    public void onModifyPwd(String o) {
        showToast(getString(R.string.succ_edit));
        TokenCommon.clearToken();
        ARouter.getInstance().build(ARouterPath.AppTeacher.APP_MAIN).withString("action", "loginOut").navigation();
        finish();
    }


    @Override
    public void onVerify(Bitmap myBitmap, String codeId) {

    }

    @Override
    public void onVerifyCode(String o) {

    }

    @Override
    public void onSmsCode(String o) {

    }

    @Override
    public void onVerifySmsCode(String o) {

    }

    @Override
    public void onResetPwd(String o) {

    }
}

package com.shenyuan.superstudent.ui.login;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.widget.PopupWindow;

import androidx.databinding.DataBindingUtil;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.shenyuan.superapp.base.ARouterPath;
import com.shenyuan.superapp.base.base.BaseStuActivity;
import com.shenyuan.superapp.base.utils.ParseUtils;
import com.shenyuan.superapp.base.utils.PopUtils;
import com.shenyuan.superstudent.R;
import com.shenyuan.superstudent.api.presenter.PasswordPresenter;
import com.shenyuan.superstudent.api.view.PasswordView;
import com.shenyuan.superstudent.databinding.AcForgotPasswordMessageBinding;
import com.shenyuan.superstudent.databinding.PopMessageCodeBinding;

/**
 * @author ch
 * @date 2021/1/14-10:45
 * desc 安全验证
 */
@Route(path = ARouterPath.AppStudent.APP_STUDENT_FORGOT_PASSWORD_MESSAGE)
public class ForgotPasswordMessageActivity extends BaseStuActivity<AcForgotPasswordMessageBinding, PasswordPresenter> implements PasswordView {
    private CountDownTimer downTimer;

    @Autowired
    public String tel;

    private String msgId;

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
        return R.layout.ac_forgot_password_message;
    }

    @Override
    protected void initView() {
        downTimer = new CountDownTimer(60 * 1000, 1000) {
            @SuppressLint("DefaultLocale")
            @Override
            public void onTick(long millisUntilFinished) {
                binding.tvGetMsgCode.setText(String.format(getString(R.string.forget_reset_time), millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                binding.tvGetMsgCode.setText(R.string.forget_resend);
                binding.tvGetMsgCode.setTextColor(getValuesColor(R.color.color_0077ff));
                binding.tvGetMsgCode.setClickable(true);
            }
        };
        if (!TextUtils.isEmpty(tel)) {
            binding.tvVerifyTel.setText(String.format(getString(R.string.forget_we_send_sms), ParseUtils.getPhone(tel)));
        }

    }

    @Override
    protected void addListener() {
        binding.tvGetMsgCode.setOnClickListener(v -> {
            if (TextUtils.isEmpty(tel)) {
                showToast(getString(R.string.forget_error_tel));
                return;
            }

            binding.tvGetMsgCode.setTextColor(getValuesColor(R.color.color_666666));
            downTimer.start();
            binding.tvGetMsgCode.setClickable(false);

            presenter.sendSms(tel);
        });

        binding.tvForgetCantMessage.setOnClickListener(v -> showMessageDialog());

        binding.tvForgetNext.setOnClickListener(v -> {
            if (TextUtils.isEmpty(binding.etMsgCode.getText())) {
                showToast(getString(R.string.forget_please_input_sms_code));
                return;
            }
            if (TextUtils.isEmpty(msgId)) {
                showToast(getString(R.string.forget_error_sms_code));
                return;
            }
            presenter.veriSmsCode(msgId, binding.etMsgCode.getText());
        });
    }

    private void showMessageDialog() {
        PopMessageCodeBinding codeBinding = DataBindingUtil.inflate(LayoutInflater.from(context),
                R.layout.pop_message_code, null, false);

        PopupWindow popupWindow = PopUtils.getPopupWindow(context, codeBinding.getRoot(), false);
        popupWindow.showAsDropDown(binding.tvForgetCantMessage);
    }

    @Override
    protected void onDestroy() {
        downTimer.cancel();
        super.onDestroy();
    }

    @Override
    public void onModifyPwd(String o) {

    }

    @Override
    public void onVerify(Bitmap myBitmap, String codeId) {

    }

    @Override
    public void onVerifyCode(String o) {

    }

    @Override
    public void onSmsCode(String o) {
        msgId = o;
    }

    @Override
    public void onVerifySmsCode(String o) {
        ARouter.getInstance().build(ARouterPath.AppStudent.APP_STUDENT_FORGOT_PASSWORD_RESET)
                .withString("msgId", msgId)
                .withString("msgVerifyCode", binding.etMsgCode.getText())
                .navigation();
        finish();
    }

    @Override
    public void onResetPwd(String o) {

    }
}

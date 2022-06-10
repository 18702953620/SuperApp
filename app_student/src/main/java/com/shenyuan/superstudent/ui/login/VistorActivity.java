package com.shenyuan.superstudent.ui.login;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.shenyuan.superapp.base.ARouterPath;
import com.shenyuan.superapp.base.api.BasePresenter;
import com.shenyuan.superapp.base.api.TokenHandler;
import com.shenyuan.superapp.base.api.common.TokenCommon;
import com.shenyuan.superapp.base.api.common.UserCommon;
import com.shenyuan.superapp.base.base.BaseStuActivity;
import com.shenyuan.superapp.base.dialog.BaseDialog;
import com.shenyuan.superapp.base.dialog.SignDialog;
import com.shenyuan.superapp.base.dialog.SimStudentDialog;
import com.shenyuan.superapp.base.utils.ParseUtils;
import com.shenyuan.superstudent.R;
import com.shenyuan.superstudent.api.presenter.LoginPresenter;
import com.shenyuan.superstudent.api.view.LoginView;
import com.shenyuan.superstudent.databinding.AcVistorBinding;

import java.util.HashMap;

/**
 * @author ch
 * @date 2020/12/18-14:44
 * desc 游客登录
 */
@Route(path = ARouterPath.AppStudent.APP_STUDENT_LOGIN_VISTOR)
public class VistorActivity extends BaseStuActivity<AcVistorBinding, LoginPresenter> implements LoginView {

    private CountDownTimer downTimer;
    private String msgId;

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.ac_vistor;
    }

    @Override
    protected void setStatusBar() {
        setNoStatusBarByDrak();
    }

    @Override
    protected void initView() {
        String text = getString(R.string.login_agreemnet);
        SpannableStringBuilder style = new SpannableStringBuilder(text);
        ClickableSpan serverSpan = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                showToast("服务协议");
            }
        };
        ClickableSpan concealSpan = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                showToast("隐私协议");
            }
        };
        style.setSpan(serverSpan, 7, 13, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        style.setSpan(concealSpan, 14, text.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

        style.setSpan(new ForegroundColorSpan(getValuesColor(R.color.color_3cbf7f)), 7, 13, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        style.setSpan(new ForegroundColorSpan(getValuesColor(R.color.color_3cbf7f)), 14, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        binding.tvAgreement.setText(style);
        binding.tvAgreement.setMovementMethod(LinkMovementMethod.getInstance());

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
    }

    @Override
    protected void addListener() {
        binding.etVistorTel.setButtonClickListener(v -> binding.etVistorTel.clear());
        binding.etVistorTel.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 11) {
                    presenter.visitorMobile(s.toString());
                }

            }
        });

        binding.tvGetMsgCode.setOnClickListener(v -> {
            if (TextUtils.isEmpty(binding.etVistorTel.getText())) {
                showToast(getString(R.string.tel_please_input_tel));
                return;
            }
            if (!ParseUtils.isMobileNo(binding.etVistorTel.getText())) {
                showToast(getString(R.string.tel_please_input_right_tel));
                return;
            }
            binding.tvGetMsgCode.setClickable(false);

            presenter.sendSms(binding.etVistorTel.getText());
        });

        binding.tvGetMsgCode.setClickable(false);


        //登录
        binding.btnLogin.setOnClickListener(v -> {
            if (TextUtils.isEmpty(binding.etVistorTel.getText())) {
                showToast(getString(R.string.tel_please_input_tel));
                return;
            }
            if (TextUtils.isEmpty(binding.etMsgCode.getText())) {
                showToast(getString(R.string.login_input_verify_code));
                return;
            }
            presenter.visitorVerify(binding.etMsgCode.getText(), msgId);
        });
    }

    @Override
    public void onLogin(HashMap<String, String> map) {
        if (map != null) {
            showToast(getString(R.string.login_succ));
            //保存token
            TokenCommon.saveToken(map.get("token"));
            TokenCommon.saveRefreshToken(map.get("refreshToken"));

            TokenHandler.getInstance().resetLoginState();
            //跳转到主页面
            ARouter.getInstance().build(ARouterPath.AppStudent.APP_STUDENT_MAIN).navigation();
            finish();
        }
    }

    @Override
    public void onLoginOut() {

    }

    @Override
    public void onVerify(Bitmap myBitmap, String code_id) {

    }

    @Override
    public void onGetUser(HashMap<String, String> o) {

    }

    @Override
    public void onSmsCode(String msgId) {
        this.msgId = msgId;
        downTimer.start();
    }

    @Override
    public void onVisitorMobile(Integer o) {
        if (o != null) {
            if (o == 0) {
                binding.tvGetMsgCode.setClickable(true);
                binding.tvGetMsgCode.setTextColor(getValuesColor(R.color.color_3cbf7f));
            } else if (o == 1) {
                new SignDialog.Builder(context)
                        .title(getString(R.string.please_log_in_directly))
                        .onBacklistener(new BaseDialog.BaseOnBack() {
                            @Override
                            public void onConfirm() {
                                finish();
                            }
                        }).showStudent();
            }
        }
    }


    @Override
    protected void onDestroy() {
        downTimer.cancel();
        super.onDestroy();
    }
}

package com.shenyuan.superapp.ui.login;

import android.annotation.SuppressLint;
import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.widget.PopupWindow;

import androidx.databinding.DataBindingUtil;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.shenyuan.superapp.R;
import com.shenyuan.superapp.api.presenter.UserPresenter;
import com.shenyuan.superapp.api.view.UserView;
import com.shenyuan.superapp.base.base.BaseActivity;
import com.shenyuan.superapp.base.ARouterPath;
import com.shenyuan.superapp.base.utils.ParseUtils;
import com.shenyuan.superapp.base.utils.PopUtils;
import com.shenyuan.superapp.bean.UserInfoBean;
import com.shenyuan.superapp.databinding.AcEditTelBinding;
import com.shenyuan.superapp.databinding.PopMessageCodeBinding;

/**
 * @author ch
 * @date 2021/1/18 17:32
 * desc 修改手机号
 */
@Route(path = ARouterPath.AppTeacher.APP_EDIT_TEL)
public class EditTelActivity extends BaseActivity<AcEditTelBinding, UserPresenter> implements UserView {
    private String msgId;
    private CountDownTimer downTimer;

    @Override
    protected void setStatusBar() {
        setStatusBarColor(R.color.color_f5f5f5);
    }

    @Override
    protected UserPresenter createPresenter() {
        return new UserPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.ac_edit_tel;
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

    }

    @Override
    protected void addListener() {
        binding.tvGetMsgCode.setOnClickListener(v -> {
            if (TextUtils.isEmpty(getTv(binding.etTel))) {
                showToast(getString(R.string.tel_please_input_tel));
                return;
            }
            if (!ParseUtils.isMobileNo(getTv(binding.etTel))) {
                showToast(getString(R.string.tel_please_input_right_tel));
                return;
            }
            presenter.sendSms(getTv(binding.etTel));
        });
        binding.tvGetMsgCode.setClickable(false);

        binding.tvForgetCantMessage.setOnClickListener(v -> showMessageDialog());

        binding.tvForgetNext.setOnClickListener(v -> {
            if (TextUtils.isEmpty(getTv(binding.etTel))) {
                showToast(getString(R.string.tel_please_input_tel));
                return;
            }
            if (TextUtils.isEmpty(binding.etMsgCode.getText())) {
                showToast(getString(R.string.tel_input_msg_code));
                return;
            }
            if (TextUtils.isEmpty(msgId)) {
                showToast(getString(R.string.tel_error_sms_code));
                return;
            }
            presenter.modifyPhone(msgId, getTv(binding.etTel), binding.etMsgCode.getText());
        });

        binding.etTel.addTextChangedListener(new TextWatcher() {
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
    public void onUserInfo(UserInfoBean bean) {

    }

    @Override
    public void onEditAvatar(String o) {

    }

    @Override
    public void onSmsCode(String msgId) {
        this.msgId = msgId;
        downTimer.start();
    }

    @Override
    public void onModify(String o) {
        showToast(getString(R.string.succ_edit));
        setResult(RESULT_OK);
        finish();
    }

    @Override
    public void onVisitorMobile(Integer o) {
        if (o != null) {
            if (o == 0) {
                binding.tvGetMsgCode.setClickable(true);
                binding.tvGetMsgCode.setTextColor(getValuesColor(R.color.color_0077ff));
            } else if (o == 1) {
                showToast(getString(R.string.tel_used));
            }
        }
    }
}

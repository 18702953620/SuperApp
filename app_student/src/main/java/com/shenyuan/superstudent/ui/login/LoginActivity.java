package com.shenyuan.superstudent.ui.login;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.viewholder.BaseDataBindingHolder;
import com.shenyuan.superapp.base.ARouterPath;
import com.shenyuan.superapp.base.api.TokenHandler;
import com.shenyuan.superapp.base.api.common.AppConstant;
import com.shenyuan.superapp.base.api.common.TokenCommon;
import com.shenyuan.superapp.base.api.common.UserCommon;
import com.shenyuan.superapp.base.base.BaseStuActivity;
import com.shenyuan.superapp.base.utils.DensityUtils;
import com.shenyuan.superapp.base.utils.PopUtils;
import com.shenyuan.superapp.common.base.BaseVBAdapter;
import com.shenyuan.superapp.common.web.WebActivity;
import com.shenyuan.superstudent.R;
import com.shenyuan.superstudent.api.presenter.LoginPresenter;
import com.shenyuan.superstudent.api.view.LoginView;
import com.shenyuan.superstudent.databinding.AcLoginBinding;
import com.shenyuan.superstudent.databinding.ItemLoginAccountBinding;
import com.shenyuan.superstudent.databinding.PopLoginAccountBinding;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

/**
 * @author ch
 * @date 2020/12/18-14:44
 * desc 登录页
 */
@Route(path = ARouterPath.AppStudent.APP_STUDENT_LOGIN)
public class LoginActivity extends BaseStuActivity<AcLoginBinding, LoginPresenter> implements LoginView {
    private BaseVBAdapter<String, BaseDataBindingHolder> accountAdapter;
    private String codeId;

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.ac_login;
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
                WebActivity.loadUrl(context, AppConstant.SERVICE_AGREEMENT_URL);
            }
        };
        ClickableSpan concealSpan = new ClickableSpan() {
            @Override
            public void onClick(View view) {
                WebActivity.loadUrl(context, AppConstant.PRIVACY_POLICY_URL);
            }
        };
        style.setSpan(serverSpan, 7, 13, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        style.setSpan(concealSpan, 14, text.length(), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);

        style.setSpan(new ForegroundColorSpan(getValuesColor(R.color.color_3cbf7f)), 7, 13, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        style.setSpan(new ForegroundColorSpan(getValuesColor(R.color.color_3cbf7f)), 14, text.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        binding.tvAgreement.setText(style);
        binding.tvAgreement.setMovementMethod(LinkMovementMethod.getInstance());

        ImageView code = binding.etLoginCode.getRightImageView();
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DensityUtils.dp2px(context, 85), DensityUtils.dp2px(context, 35));
        code.setLayoutParams(params);
        code.setBackground(null);

        presenter.getVerify();
    }

    @Override
    protected void addListener() {
        binding.tvHotline.setOnClickListener(v -> toTel());

        //登录
        binding.btnLogin.setOnClickListener(v -> {
            if (TextUtils.isEmpty(binding.etLoginname.getText())) {
                showToast(getString(R.string.login_input_tel));
                return;
            }
            if (TextUtils.isEmpty(binding.etLoginPwd.getText())) {
                showToast(getString(R.string.login_input_pwd));
                return;
            }
            if (TextUtils.isEmpty(binding.etLoginCode.getText())) {
                showToast(getString(R.string.login_input_verify_code));
                return;
            }
            presenter.login(binding.etLoginname.getText(), binding.etLoginPwd.getText(), binding.etLoginCode.getText(), codeId);
        });
        //游客
        binding.tvLoginVistor.setOnClickListener(v -> ARouter.getInstance().build(ARouterPath.AppStudent.APP_STUDENT_LOGIN_VISTOR).navigation());
        //历史账号
        binding.etLoginname.setButtonClickListener(v -> showAccountDialog());
        //忘记密码
        binding.tvForgetPwd.setOnClickListener(v -> {
            if (TextUtils.isEmpty(binding.etLoginname.getText())) {
                showToast(getString(R.string.login_input_tel));
                return;
            }

            presenter.getTelByName(binding.etLoginname.getText());
        });
        //刷新验证码
        binding.etLoginCode.setButtonClickListener(v -> {
            binding.etLoginCode.clear();
            presenter.getVerify();
        });

        binding.cbAgreement.setOnCheckedChangeListener((buttonView, isChecked) -> binding.btnLogin.setEnabled(isChecked));
    }

    private void toTel() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_DIAL);
        String tel = getString(R.string.hotline);
        intent.setData(Uri.parse("tel:" + tel));
        startActivity(intent);
    }

    private void showAccountDialog() {
        accountAdapter = new BaseVBAdapter<String, BaseDataBindingHolder>(R.layout.item_login_account, UserCommon.getUserList(), false) {
            @Override
            protected void convert(@NotNull BaseDataBindingHolder holder, String s) {
                ItemLoginAccountBinding bi = (ItemLoginAccountBinding) holder.getDataBinding();
                if (bi == null) {
                    return;
                }
                bi.tvAccount.setText(s);
            }
        };
        accountAdapter.addChildClickViewIds(R.id.ll_account_delete);
        accountAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            if (view.getId() == R.id.ll_account_delete) {
                accountAdapter.removeAt(position);
                UserCommon.saveUser(accountAdapter.getData());
            }
        });


        PopLoginAccountBinding popBinding = DataBindingUtil.inflate(LayoutInflater.from(context),
                R.layout.pop_login_account, null, false);

        popBinding.rvAccount.setLayoutManager(new LinearLayoutManager(context));
        popBinding.rvAccount.setAdapter(accountAdapter);

        PopupWindow popupWindow = PopUtils.getPopupWindow(context, popBinding.getRoot(), false);
        popupWindow.showAsDropDown(binding.etLoginname);

        binding.etLoginname.setButtonDrawable(R.mipmap.ic_arrow_up);

        popupWindow.setOnDismissListener(() -> binding.etLoginname.setButtonDrawable(R.mipmap.ic_arrow_down));

        accountAdapter.setItemClickListener((bean, view, position) -> {
            binding.etLoginname.setText(bean);
            popupWindow.dismiss();
        });
    }

    @Override
    public void onLogin(HashMap<String, String> map) {
        if (map != null) {
            showToast(getString(R.string.login_succ));
            UserCommon.addUser(binding.etLoginname.getText());
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
        this.codeId = code_id;
        if (myBitmap != null) {
            binding.etLoginCode.getRightImageView().setImageBitmap(myBitmap);
        }
    }

    @Override
    public void onGetUser(HashMap<String, String> o) {
        if (o != null) {
            String tel = o.get("mobile");
            if (!TextUtils.isEmpty(tel)) {
                ARouter.getInstance().build(ARouterPath.AppStudent.APP_STUDENT_FORGOT_PASSWORD)
                        .withString("tel", tel).navigation();
            } else {
                showToast(getString(R.string.login_not_tel));
            }
        }
    }


    @Override
    public void onSmsCode(String o) {

    }

    @Override
    public void onVisitorMobile(Integer o) {

    }
}

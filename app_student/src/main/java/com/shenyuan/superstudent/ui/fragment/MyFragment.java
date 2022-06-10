package com.shenyuan.superstudent.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.launcher.ARouter;
import com.shenyuan.superapp.base.ARouterPath;
import com.shenyuan.superapp.base.api.common.TokenCommon;
import com.shenyuan.superapp.base.base.BaseFragment;
import com.shenyuan.superapp.base.dialog.BaseDialog;
import com.shenyuan.superapp.base.dialog.SimStudentDialog;
import com.shenyuan.superapp.base.utils.FileUtils;
import com.shenyuan.superapp.base.utils.StrUtils;
import com.shenyuan.superapp.common.utils.GlideUtils;
import com.shenyuan.superstudent.R;
import com.shenyuan.superstudent.api.presenter.UserPresenter;
import com.shenyuan.superstudent.api.view.UserView;
import com.shenyuan.superstudent.bean.IntegralListBean;
import com.shenyuan.superstudent.bean.UserInfoBean;
import com.shenyuan.superstudent.databinding.FmMyBinding;

import java.util.List;

/**
 * @author ch
 * @date 2021/1/4-10:58
 * desc 我的
 */
public class MyFragment extends BaseFragment<FmMyBinding, UserPresenter> implements UserView {

    private final int userinfoRequestCode = 120;

    @Override
    protected void loadData() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fm_my;
    }

    @Override
    protected void initView() {
        if (!TextUtils.isEmpty(TokenCommon.getToken())) {
            presenter.getUserInfo();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        binding.tvCache.setText(FileUtils.getCacheSize(context.getExternalCacheDir()));
    }

    @Override
    protected void addListener() {
        //我的钱包
        binding.clWallet.setOnClickListener(v -> showToast(getString(R.string.next_version)));
        //我的积分
        binding.clMyIntegral.setOnClickListener(v -> ARouterPath.router(ARouterPath.AppStudent.APP_STUDENT_INTEGRAL));
        //个人信息
        binding.clMyInfo.setOnClickListener(v -> ARouter.getInstance().build(ARouterPath.AppStudent.APP_STUDENT_USERINFO)
                .navigation(getActivity(), userinfoRequestCode));
        //我的订单
        binding.clMyOrder.setOnClickListener(v -> showToast(getString(R.string.next_version)));
        //修改密码
        binding.clMyPwd.setOnClickListener(v -> ARouterPath.router(ARouterPath.AppStudent.APP_STUDENT_EDIT_PASSWORD));
        //清理缓存
        binding.clMyClear.setOnClickListener(v -> new SimStudentDialog.Builder(context).title(getString(R.string.clear_cache))
                .submitText(getString(R.string.sure_clear))
                .onBacklistener(new BaseDialog.BaseOnBack() {
                    @Override
                    public void onConfirm() {
                        binding.clMyClear.postDelayed(() -> {
                            FileUtils.deleteFilesByDirectory(context.getExternalCacheDir());
                            binding.tvCache.setText(FileUtils.getCacheSize(context.getExternalCacheDir()));
                            showToast(getString(R.string.succ_clear));
                        }, 1500);
                    }
                }).show());
        //关于我们
        binding.clMyAbout.setOnClickListener(v -> ARouterPath.router(ARouterPath.AppStudent.APP_STUDENT_ABOUTUS));
    }

    @Override
    protected UserPresenter createPresenter() {
        return new UserPresenter(this);
    }


    @Override
    public void onErrorCode(String code, String msg) {
    }

    @Override
    public void onUserInfo(UserInfoBean bean) {
        if (bean != null) {
            binding.tvMyName.setText(bean.getUsername());
            if (!TextUtils.isEmpty(bean.getUserCode())) {
                binding.tvMyNumber.setText(String.format(getString(R.string.user_number), bean.getUserCode()));
            }

            binding.tvIntegral.setText(StrUtils.isEmpty(bean.getTotalPoints()));
            GlideUtils.loadRound(context, bean.getAvatar(), binding.ivMyHead, R.mipmap.ic_default_head);
        }
    }

    @Override
    public void onEditAvatar(String o) {

    }

    @Override
    public void onSmsCode(String msgId) {

    }

    @Override
    public void onModify(String o) {

    }

    @Override
    public void onTotalPoint(Integer o) {

    }

    @Override
    public void onPointList(List<IntegralListBean> o) {

    }

    @Override
    public void onVisitorMobile(Integer o) {

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == userinfoRequestCode && resultCode == Activity.RESULT_OK) {
            presenter.getUserInfo();
        }
    }
}

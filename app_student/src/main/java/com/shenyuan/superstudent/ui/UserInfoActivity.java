package com.shenyuan.superstudent.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.ch.cper.CPermission;
import com.ch.cper.PermissGroup;
import com.ch.cper.listener.PermissListener;
import com.h.cheng.filepicker.PsPickManager;
import com.shenyuan.superapp.base.ARouterPath;
import com.shenyuan.superapp.base.api.BasePresenter;
import com.shenyuan.superapp.base.api.common.TokenCommon;
import com.shenyuan.superapp.base.base.BaseStuActivity;
import com.shenyuan.superapp.base.dialog.BaseDialog;
import com.shenyuan.superapp.base.dialog.SimStudentDialog;
import com.shenyuan.superapp.base.utils.ParseUtils;
import com.shenyuan.superapp.base.utils.StrUtils;
import com.shenyuan.superapp.common.CompressManager;
import com.shenyuan.superapp.common.MyImgLoader;
import com.shenyuan.superapp.common.utils.GlideUtils;
import com.shenyuan.superstudent.R;
import com.shenyuan.superstudent.api.presenter.UserPresenter;
import com.shenyuan.superstudent.api.view.UserView;
import com.shenyuan.superstudent.bean.IntegralListBean;
import com.shenyuan.superstudent.bean.UserInfoBean;
import com.shenyuan.superstudent.databinding.AcUserInfoBinding;

import java.util.List;

/**
 * @author ch
 * @date 2021/1/18-13:13
 * desc 个人信息
 */
@Route(path = ARouterPath.AppStudent.APP_STUDENT_USERINFO)
public class UserInfoActivity extends BaseStuActivity<AcUserInfoBinding, UserPresenter> implements UserView {

    private final int modifyTelRequestCode = 120;

    @Override
    protected UserPresenter createPresenter() {
        return new UserPresenter(this);
    }

    @Override
    protected void setStatusBar() {
        setStatusBarColor(R.color.color_f5f5f5);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.ac_user_info;
    }

    @Override
    protected void initView() {
        presenter.getUserInfo();
    }

    @Override
    protected void addListener() {
        //退出登录
        binding.tvLoginOut.setOnClickListener(v -> {
            new SimStudentDialog.Builder(context).title(getString(R.string.logout_tip)).submitText(getString(R.string.loginout_sure)).onBacklistener(new BaseDialog.BaseOnBack() {
                @Override
                public void onConfirm() {
                    TokenCommon.clearToken();
                    ARouter.getInstance().build(ARouterPath.AppStudent.APP_STUDENT_MAIN).withString("action", "loginOut").navigation();
                    finish();
                }
            }).show();
        });
        binding.clHead.setOnClickListener(v -> openImage());

        //修改手机号
        binding.clEditTel.setOnClickListener(v -> ARouterPath.router(ARouterPath.AppStudent.APP_STUDENT_EDIT_PHONE, this, modifyTelRequestCode));
    }


    private void openImage() {
        CPermission.with(context).permiss().permission(PermissGroup.STORAGE).listener(new PermissListener<String>() {
            @Override
            public void onGranted(List<String> li) {
                new PsPickManager.Buider()
                        .with(UserInfoActivity.this)
                        .max(1)
                        .spanCount(4)
                        .title(getString(R.string.please_select_pic))
                        .backColor(Color.parseColor("#ffffff"))
                        .titleColor(Color.parseColor("#333333"))
                        .rightColor(Color.parseColor("#666666"))
                        .rightBackColor(Color.parseColor("#ffffff"))
                        .imageLoader(new MyImgLoader())
                        .callback(list -> {
                            if (list != null && list.size() > 0) {
                                new CompressManager(context, list.get(0).getPath(), new CompressManager.CompressListener() {
                                    @Override
                                    public void onFinish(List<String> list) {
                                        GlideUtils.loadRound(context, list.get(0), binding.ivInfoHead, R.mipmap.ic_default_head);
                                        presenter.editAvatar(list.get(0));
                                    }

                                    @Override
                                    public void onError(Throwable e) {

                                    }
                                }).start();
                            }
                        })
                        .startImage();
            }

            @Override
            public void onDenied(List<String> list) {

            }
        }).start();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == modifyTelRequestCode && resultCode == Activity.RESULT_OK) {
            presenter.getUserInfo();
        }
    }

    @Override
    public void onUserInfo(UserInfoBean bean) {
        if (bean != null) {
            binding.tvInfoNumber.setText(StrUtils.isEmpty(bean.getUserCode()));
            binding.tvInfoTel.setText(ParseUtils.getPhone(bean.getMobile()));
            GlideUtils.loadRound(context, bean.getAvatar(), binding.ivInfoHead, R.mipmap.ic_default_head);
        }
    }

    @Override
    public void onEditAvatar(String o) {
        showToast(getString(R.string.succ_edit));
        setResult(RESULT_OK);
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
}

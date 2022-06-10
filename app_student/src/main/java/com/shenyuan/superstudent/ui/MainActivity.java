package com.shenyuan.superstudent.ui;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.ch.cper.CPermission;
import com.ch.cper.PermissGroup;
import com.ch.cper.listener.PermissListener;
import com.shenyuan.superapp.base.ARouterPath;
import com.shenyuan.superapp.base.api.BasePresenter;
import com.shenyuan.superapp.base.api.common.TokenCommon;
import com.shenyuan.superapp.base.base.BaseStuActivity;
import com.shenyuan.superapp.base.dialog.BaseDialog;
import com.shenyuan.superapp.base.dialog.SimDialog;
import com.shenyuan.superapp.base.dialog.SimStudentDialog;
import com.shenyuan.superstudent.R;
import com.shenyuan.superstudent.api.presenter.MsgPresenter;
import com.shenyuan.superstudent.api.view.MsgView;
import com.shenyuan.superstudent.bean.MsgListBean;
import com.shenyuan.superstudent.databinding.AcMainBinding;
import com.shenyuan.superstudent.ui.fragment.HomeFragment;
import com.shenyuan.superstudent.ui.fragment.MessageFragment;
import com.shenyuan.superstudent.ui.fragment.MyFragment;
import com.shenyuan.superstudent.ui.fragment.SquareFragment;

import java.util.List;

/**
 * @author ch
 * @date 2021/4/26 10:59
 * desc
 */
@Route(path = ARouterPath.AppStudent.APP_STUDENT_MAIN)
public class MainActivity extends BaseStuActivity<AcMainBinding, MsgPresenter> implements MsgView {

    /**
     * 首页
     */
    private HomeFragment fmHome;
    /**
     * 广场
     */
    private SquareFragment fmSquare;
    /**
     * 消息
     */
    private MessageFragment fmMsg;
    /**
     * 我的
     */
    private MyFragment fmMy;


    @Override
    protected MsgPresenter createPresenter() {
        return new MsgPresenter(this);
    }

    @Override
    protected void setStatusBar() {
        setNoStatusBarByDrak();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.ac_main;
    }

    @Override
    protected void initView() {
        fmHome = new HomeFragment();
        fmSquare = new SquareFragment();
        fmMsg = new MessageFragment();
        fmMy = new MyFragment();

        getSupportFragmentManager().beginTransaction()
                .add(R.id.ll_main, fmHome)
                .add(R.id.ll_main, fmSquare)
                .add(R.id.ll_main, fmMsg)
                .add(R.id.ll_main, fmMy)
                .show(fmHome).hide(fmSquare).hide(fmMsg).hide(fmMy).commit();

        CPermission.with(context).permiss().permission(PermissGroup.STORAGE).listener(new PermissListener<String>() {
            @Override
            public void onGranted(List<String> list) {

            }

            @Override
            public void onDenied(List<String> list) {
                finish();
            }
        }).start();

        if (!TextUtils.isEmpty(TokenCommon.getToken())) {
            presenter.getUnReadMsgCount();
        }
    }

    @Override
    protected void addListener() {
        //首页
        binding.rbMainHome.setOnClickListener(v -> {
            setNoStatusBarByDrak();
            getSupportFragmentManager().beginTransaction().show(fmHome).hide(fmSquare).hide(fmMsg).hide(fmMy).commit();
            fmHome.setUserVisibleHint(true);
        });
        //广场
        binding.rbMainSquare.setOnClickListener(v -> {
            if (TextUtils.isEmpty(TokenCommon.getToken())) {
                showLoginDialog();
                binding.rbMainHome.setChecked(true);
                return;
            }
            setNoStatusBarByLight();
            getSupportFragmentManager().beginTransaction().show(fmSquare).hide(fmHome).hide(fmMsg).hide(fmMy).commit();
            fmSquare.setUserVisibleHint(true);
        });
        //消息
        binding.rbMainMsg.setOnClickListener(v -> {
            if (TextUtils.isEmpty(TokenCommon.getToken())) {
                showLoginDialog();
                binding.rbMainHome.setChecked(true);
                return;
            }
            setNoStatusBarByLight();
            getSupportFragmentManager().beginTransaction().show(fmMsg).hide(fmSquare).hide(fmHome).hide(fmMy).commit();
            fmMsg.setUserVisibleHint(true);
        });
        binding.rbMainMy.setOnClickListener(v -> {
            if (TextUtils.isEmpty(TokenCommon.getToken())) {
                showLoginDialog();
                binding.rbMainHome.setChecked(true);
                return;
            }
            setNoStatusBarByDrak();
            getSupportFragmentManager().beginTransaction().show(fmMy).hide(fmSquare).hide(fmMsg).hide(fmHome).commit();
            fmMy.setUserVisibleHint(true);
        });
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null) {
            String action = intent.getStringExtra("action");
            //退出登录
            if ("loginOut".equals(action)) {
                ARouter.getInstance().build(ARouterPath.AppStudent.APP_STUDENT_LOGIN).navigation();
                finish();
            }
        }
    }


    @Override
    public void onBackPressed() {
        new SimStudentDialog.Builder(context).title(getString(R.string.loginout)).onBacklistener(new BaseDialog.BaseOnBack() {
            @Override
            public void onConfirm() {
                MainActivity.super.onBackPressed();
            }
        }).show();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(new Bundle());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (fmMy != null) {
            fmMy.onActivityResult(requestCode, resultCode, data);
        }
        if (fmHome != null) {
            fmHome.onActivityResult(requestCode, resultCode, data);
        }

        if (requestCode == MessageFragment.REQUEST_MSG_INFO) {
            presenter.getUnReadMsgCount();
        }
    }

    @Override
    public void onNoticeList(List<MsgListBean> o) {

    }

    @Override
    public void onDelMsg(String o, int position) {

    }

    @Override
    public void onUnRead(Integer o) {
        if (o == 0) {
            binding.tvMsgCount.setVisibility(View.GONE);
        } else {
            binding.tvMsgCount.setVisibility(View.VISIBLE);
        }
        binding.tvMsgCount.setText(String.valueOf(o));
    }
}

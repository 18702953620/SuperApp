package com.shenyuan.superapp.ui;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.ch.cper.CPermission;
import com.ch.cper.PermissGroup;
import com.ch.cper.listener.PermissListener;
import com.shenyuan.superapp.R;
import com.shenyuan.superapp.api.presenter.LoginPresenter;
import com.shenyuan.superapp.api.view.LoginView;
import com.shenyuan.superapp.base.ARouterPath;
import com.shenyuan.superapp.base.api.common.PermissionCommon;
import com.shenyuan.superapp.base.base.BaseActivity;
import com.shenyuan.superapp.base.dialog.BaseDialog;
import com.shenyuan.superapp.base.dialog.SimDialog;
import com.shenyuan.superapp.common.api.presenter.CommonPresenter;
import com.shenyuan.superapp.common.api.view.CommonView;
import com.shenyuan.superapp.databinding.AcMainBinding;
import com.shenyuan.superapp.ui.fragment.HomeFragment;
import com.shenyuan.superapp.ui.fragment.MessageFragment;
import com.shenyuan.superapp.ui.fragment.MyFragment;
import com.shenyuan.superapp.ui.fragment.SquareFragment;

import java.util.HashMap;
import java.util.List;

/**
 * @author H.cheng
 * 主页面
 */
@Route(path = ARouterPath.AppTeacher.APP_MAIN)
public class MainActivity extends BaseActivity<AcMainBinding, LoginPresenter> implements LoginView, CommonView {
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

    private CommonPresenter commonPresenter;

    @Override
    protected LoginPresenter createPresenter() {
        return new LoginPresenter(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.ac_main;
    }

    @Override
    protected void setStatusBar() {
        setNoStatusBarByDrak();
    }

    @Override
    protected void initView() {
        presenter.getUnReadMsgCount();

        commonPresenter = new CommonPresenter(this);
        commonPresenter.getUserBtn();

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
            setNoStatusBarByLight();
            getSupportFragmentManager().beginTransaction().show(fmSquare).hide(fmHome).hide(fmMsg).hide(fmMy).commit();
            fmSquare.setUserVisibleHint(true);
        });
        //消息
        binding.rbMainMsg.setOnClickListener(v -> {
            setNoStatusBarByLight();
            getSupportFragmentManager().beginTransaction().show(fmMsg).hide(fmSquare).hide(fmHome).hide(fmMy).commit();
            fmMsg.setUserVisibleHint(true);
        });
        binding.rbMainMy.setOnClickListener(v -> {
            setNoStatusBarByDrak();
            getSupportFragmentManager().beginTransaction().show(fmMy).hide(fmSquare).hide(fmMsg).hide(fmHome).commit();
            fmMy.setUserVisibleHint(true);
        });

        binding.tvBigData.setOnClickListener(v -> ARouterPath.router( ARouterPath.AppTeacher.APP_BIG_DATA));
    }


    @Override
    public void onLogin(HashMap<String, String> map) {

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
    public void onUnRead(Integer o) {
        if (o == 0) {
            binding.tvMsgCount.setVisibility(View.GONE);
        } else {
            binding.tvMsgCount.setVisibility(View.VISIBLE);
        }
        binding.tvMsgCount.setText(String.valueOf(o));
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent != null) {
            String action = intent.getStringExtra("action");
            //退出登录
            if ("loginOut".equals(action)) {
                ARouter.getInstance().build(ARouterPath.AppTeacher.APP_LOGIN).navigation();
                finish();
            }
        }
    }

    @Override
    public void onBackPressed() {
        new SimDialog.Builder(context).title(getString(R.string.loginout)).onBacklistener(new BaseDialog.BaseOnBack() {
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
    protected void onDestroy() {
        super.onDestroy();
        if (commonPresenter != null) {
            commonPresenter.detachView();
        }
    }

    @Override
    public void onUserBtn(List<String> o) {
        //保存权限
        PermissionCommon.savePermission(o);
    }
}
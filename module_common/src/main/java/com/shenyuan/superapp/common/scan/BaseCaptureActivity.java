package com.shenyuan.superapp.common.scan;

import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.view.View;

import com.shenyuan.superapp.base.api.BasePresenter;
import com.shenyuan.superapp.base.base.BaseActivity;
import com.shenyuan.superapp.base.utils.LogUtils;
import com.shenyuan.superapp.common.R;
import com.shenyuan.superapp.common.databinding.AcScanCaptureBinding;

import cn.bingoogolapple.qrcode.core.QRCodeView;

/**
 * @author ch
 * @date 2020/12/28-10:27
 * desc 二维码扫描
 */
public abstract class BaseCaptureActivity extends BaseActivity<AcScanCaptureBinding, BasePresenter> implements QRCodeView.Delegate {

    private static final String TAG = "BaseCaptureActivity";
    /**
     * 播放器
     */
    private SoundPool soundPool;
    /**
     * 声音id
     */
    private int hitsfx;
    /**
     * 是否点亮
     */
    private boolean isFlashLight;

    /**
     * 打开相册
     */
    protected abstract void openAlbum();

    @Override
    protected void setStatusBar() {
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.ac_scan_capture;
    }

    @Override
    protected void initView() {
        binding.zxingview.setDelegate(this);
        soundPool = new SoundPool(5, AudioManager.STREAM_SYSTEM, 5);
        hitsfx = soundPool.load(context, R.raw.beep, 5);
    }

    @Override
    protected void onStart() {
        super.onStart();
        // 打开后置摄像头开始预览，但是并未开始识别
        binding.zxingview.startCamera();
        //显示扫描框，并开始识别
        binding.zxingview.startSpotAndShowRect();
    }

    @Override
    protected void onStop() {
        super.onStop();
        binding.zxingview.stopCamera();
    }

    @Override
    protected void addListener() {
        binding.llFlashlight.setOnClickListener(v -> {
            isFlashLight = !isFlashLight;
            if (isFlashLight) {
                //打开
                binding.zxingview.openFlashlight();
                binding.tvScanLight.setText(getString(R.string.scan_light_close));
            } else {
                //关闭
                binding.zxingview.closeFlashlight();
                binding.tvScanLight.setText(getString(R.string.scan_light_open));
            }
        });

        binding.llBack.setOnClickListener(v -> finish());

        binding.llAlbum.setOnClickListener(v -> openAlbum());
    }


    @Override
    public void onScanQRCodeSuccess(String result) {
        LogUtils.e(TAG, "扫描结果：" + result);
        //播放声音
        soundPool.play(hitsfx, 5, 5, 0, 0, 1);

        Intent intent = new Intent();
        intent.putExtra("result", result);
        setResult(RESULT_OK, intent);

        finish();

    }

    @Override
    public void onCameraAmbientBrightnessChanged(boolean isDark) {
        // 这里是通过修改提示文案来展示环境是否过暗的状态，接入方也可以根据 isDark 的值来实现其他交互效果
        if (isDark) {
            binding.llFlashlight.setVisibility(View.VISIBLE);
            if (isFlashLight) {
                binding.tvScanLight.setText(getString(R.string.scan_light_close));
            } else {
                binding.tvScanLight.setText(getString(R.string.scan_light_open));
            }
        } else {
            binding.llFlashlight.setVisibility(View.GONE);
            if (isFlashLight) {
                //点亮时 一直展示
                binding.llFlashlight.setVisibility(View.VISIBLE);
                binding.tvScanLight.setText(getString(R.string.scan_light_close));
            }else {
                binding.tvScanLight.setText(getString(R.string.scan_light_normal));
            }
        }



    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        LogUtils.e(TAG, "扫描错误");
    }

    @Override
    protected void onDestroy() {
        binding.zxingview.onDestroy();
        soundPool.release();
        super.onDestroy();
    }
}

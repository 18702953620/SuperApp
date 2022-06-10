package com.shenyuan.superapp.ui;

import android.graphics.Color;

import com.ch.cper.CPermission;
import com.ch.cper.PermissGroup;
import com.ch.cper.listener.PermissListener;
import com.h.cheng.filepicker.PsPickManager;
import com.jaeger.library.StatusBarUtil;
import com.shenyuan.superapp.R;
import com.shenyuan.superapp.common.MyImgLoader;
import com.shenyuan.superapp.common.scan.BaseCaptureActivity;

import java.util.List;

/**
 * @author ch
 * @date 2021/1/11-10:54
 * desc 扫码
 */
public class AppCaptureActivity extends BaseCaptureActivity {

    @Override
    protected void setStatusBar() {
        StatusBarUtil.setTranslucentForImageView(this, 0, null);
    }

    @Override
    protected void openAlbum() {
        CPermission.with(context)
                .permiss()
                .permission(PermissGroup.STORAGE)
                .listener(new PermissListener<String>() {
                    @Override
                    public void onGranted(List<String> list) {
                        openImage();
                    }

                    @Override
                    public void onDenied(List<String> list) {
                    }
                }).start();
    }


    private void openImage() {
        new PsPickManager.Buider()
                .with(this)
                .max(1)
                .needCamera(false)
                .spanCount(4)
                .title(getString(R.string.please_select_pic))
                .backColor(Color.parseColor("#ffffff"))
                .titleColor(Color.parseColor("#333333"))
                .rightColor(Color.parseColor("#666666"))
                .rightBackColor(Color.parseColor("#ffffff"))
                .imageLoader(new MyImgLoader())
                .callback(list -> {
                    if (list != null && list.size() > 0 && list.get(0) != null) {
                        binding.zxingview.decodeQRCode(list.get(0).getPath());
                    }
                })
                .startImage();
    }
}

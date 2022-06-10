package com.shenyuan.superapp.common.scan;

import android.app.Activity;
import android.content.Intent;

import androidx.fragment.app.FragmentActivity;

import com.shenyuan.superapp.base.helper.result.ResultHelper;


/**
 * @author ch
 * @date 2020/12/28-9:43
 * desc 二维码扫描
 */
public class BarCodeUtils {
    /**
     * 开始扫描
     *
     * @param fmActivity fmActivity
     * @param callBack   回调
     */
    public static void startScan(FragmentActivity fmActivity, Class<?> cls, ScanCallBack callBack) {
        Intent intent = new Intent(fmActivity, cls);
        ResultHelper.init(fmActivity).startActivityForResult(intent, (resultCode, data) -> {
            if (resultCode == Activity.RESULT_OK && data != null) {
                String result = data.getStringExtra("result");
                if (result != null && result.contains("__")) {
                    String[] datas = result.split("__");
                    if (datas.length != 3) {
                        return;
                    }
                    if ("zhxy-centre-web".equals(datas[0])) {
                        //扫码登录
                        if (callBack != null) {
                            callBack.onResult(new ScanDataBean(datas[0], datas[1], datas[2]));
                        }
                    } else if ("zhxy-wx-offical-accounts".equals(datas[0])) {
                        //考试
                        if (callBack != null) {
                            callBack.onResult(new ScanDataBean(datas[0], datas[1], datas[2]));
                        }
                    } else {
                        if (callBack != null) {
                            callBack.onResult(new ScanDataBean(result));
                        }
                    }
                } else {
                    if (callBack != null) {
                        callBack.onResult(new ScanDataBean(result));
                    }
                }
            }
        });
    }
}

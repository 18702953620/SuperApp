package com.shenyuan.superapp.base.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Camera;

/**
 * @author ch
 * 时间： 2019/1/16 0016-上午 8:56
 * 描述： 相机工具类
 * 来源：
 */

public class CameraUtils {

    /**
     * 是否有摄像头
     *
     * @param context context
     * @return boolean
     */
    public static boolean checkCamera(Context context) {
        return context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY);
    }

    /**
     * 是否有后置摄像头
     *
     * @param context context
     * @return boolean
     */
    public static boolean hasBack(Context context) {
        if (checkCamera(context)) {
            int cameraCount = Camera.getNumberOfCameras();
            Camera.CameraInfo info = new Camera.CameraInfo();
            for (int cameraIndex = 0; cameraIndex < cameraCount; cameraIndex++) {
                Camera.getCameraInfo(cameraIndex, info);
                if (info.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 是否有前置摄像头
     *
     * @param context context
     * @return boolean
     */
    public static boolean hasFront(Context context) {
        if (checkCamera(context)) {
            int cameraCount = Camera.getNumberOfCameras();
            Camera.CameraInfo info = new Camera.CameraInfo();
            for (int cameraIndex = 0; cameraIndex < cameraCount; cameraIndex++) {
                Camera.getCameraInfo(cameraIndex, info);
                if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                    return true;
                }
            }
        }
        return false;
    }
}

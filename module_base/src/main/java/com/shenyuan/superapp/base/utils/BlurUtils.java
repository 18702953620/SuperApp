package com.shenyuan.superapp.base.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;


/**
 * @author ch
 * 时间： 2019/1/16 0016-上午 8:56
 * 描述： 高斯模糊工具类
 * 来源：
 */
public class BlurUtils {
    /**
     * 获取模糊的图片
     *
     * @param context 上下文对象
     * @param bitmap  传入的bitmap图片
     * @param radius  模糊度（Radius最大只能设置25.f）
     * @return Bitmap
     */
    public static Bitmap blurBitmap(Context context, Bitmap bitmap, int radius) {
        return blurBitmap(context, bitmap, 8, radius);
    }

    /**
     * 获取模糊的图片
     *
     * @param context context
     * @param bitmap  bitmap
     * @param scale   缩放比例
     * @param radius  radius
     * @return Bitmap
     */
    public static Bitmap blurBitmap(Context context, Bitmap bitmap, int scale, int radius) {

        int width = Math.round(bitmap.getWidth() / scale);
        int height = Math.round(bitmap.getHeight() / scale);

        //缩小原图
        Bitmap outBitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);
        // 初始化Renderscript，该类提供了RenderScript context，创建其他RS类之前必须先创建这个类，其控制RenderScript的初始化，资源管理及释放
        RenderScript rs = RenderScript.create(context);
        // 创建高斯模糊对象
        ScriptIntrinsicBlur blurScript = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
        // 创建Allocations，此类是将数据传递给RenderScript内核的主要方 法，并制定一个后备类型存储给定类型
        Allocation allIn = Allocation.createFromBitmap(rs, bitmap);
        Allocation allOut = Allocation.createFromBitmap(rs, outBitmap);
        //设定模糊度(注：Radius最大只能设置25.f)
        blurScript.setRadius(radius);
        // Perform the Renderscript
        blurScript.setInput(allIn);
        blurScript.forEach(allOut);
        // Copy the final bitmap created by the out Allocation to the outBitmap
        allOut.copyTo(outBitmap);
        // recycle the original bitmap
        // After finishing everything, we destroy the Renderscript.
        rs.destroy();
        return outBitmap;
    }
}

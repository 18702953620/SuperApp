package com.shenyuan.superapp.common.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

/**
 * @author ch
 * @date 2021/3/4 14:41
 * desc
 */
public class GlideUtils {

    /**
     * 加载图片
     *
     * @param context   context
     * @param url       url
     * @param imageView imageView
     */
    public static void load(Context context, String url, ImageView imageView) {
        load(context, url, imageView, 0);
    }

    /***
     * 加载图片
     * @param context context
     * @param url url
     * @param imageView imageView
     * @param errorRes errorRes
     */
    public static void load(Context context, String url, ImageView imageView, int errorRes) {
        if (errorRes == 0) {
            load(context, url, imageView, null);
        } else {
            load(context, url, imageView, new RequestOptions().error(errorRes).placeholder(errorRes));
        }
    }

    /***
     * 加载图片
     * @param context context
     * @param url url
     * @param imageView imageView
     * @param errorRes errorRes
     */
    public static void loadRound(Context context, String url, ImageView imageView, int errorRes) {
        load(context, url, imageView, new RequestOptions().circleCrop().error(errorRes).placeholder(errorRes));
    }

    /***
     * 加载图片
     * @param context context
     * @param url url
     * @param imageView imageView
     * @param options options
     */
    public static void load(Context context, Object url, ImageView imageView, RequestOptions options) {
        if (context == null || imageView == null) {
            return;
        }

        if (options != null) {
            Glide.with(context).load(url).apply(options).into(imageView);
        } else {
            Glide.with(context).load(url).into(imageView);
        }
    }
}

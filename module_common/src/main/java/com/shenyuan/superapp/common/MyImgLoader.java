package com.shenyuan.superapp.common;

import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.request.RequestOptions;
import com.h.cheng.filepicker.config.ImageLoader;
import com.shenyuan.superapp.common.utils.GlideUtils;

/**
 * @author ch
 * @date 2020/10/10-16:46
 * @desc
 */
public class MyImgLoader implements ImageLoader {
    @Override
    public void loadImage(ImageView imageView, String imagePath) {
        GlideUtils.load(imageView.getContext(), imagePath, imageView, new RequestOptions()
                .override(200, 200).centerCrop());
    }

    @Override
    public void loadImage(ImageView imageView, Uri imageUri) {
        GlideUtils.load(imageView.getContext(), imageUri, imageView, new RequestOptions()
                .override(200, 200).centerCrop());
    }

    @Override
    public void loadPreImage(ImageView imageView, String imagePath) {
        GlideUtils.load(imageView.getContext(), imagePath, imageView, new RequestOptions()
                .centerCrop());
    }

    @Override
    public void loadPreImage(ImageView imageView, Uri imageUri) {
        GlideUtils.load(imageView.getContext(), imageUri, imageView, new RequestOptions()
                .centerCrop());
    }
}

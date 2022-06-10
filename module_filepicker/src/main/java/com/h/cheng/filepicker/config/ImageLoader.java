package com.h.cheng.filepicker.config;

import android.net.Uri;
import android.widget.ImageView;

import java.io.Serializable;

/**
 * @author ch
 * @date 2020/10/10-16:41
 * @desc
 */
public interface ImageLoader extends Serializable {


    /**
     * 缩略图加载方案
     *
     * @param imageView imageView
     * @param imagePath imagePath
     */
    void loadImage(ImageView imageView, String imagePath);

    /**
     * 缩略图加载方案
     *
     * @param imageView imageView
     * @param imageUri  imageUri
     */
    void loadImage(ImageView imageView, Uri imageUri);

    /**
     * 大图加载方案
     *
     * @param imageView imageView
     * @param imagePath imagePath
     */
    void loadPreImage(ImageView imageView, String imagePath);

    /**
     * 大图加载方案
     *
     * @param imageView imageView
     * @param imageUri  imageUri
     */
    void loadPreImage(ImageView imageView, Uri imageUri);


}

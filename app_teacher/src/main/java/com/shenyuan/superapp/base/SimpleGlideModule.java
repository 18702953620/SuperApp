package com.shenyuan.superapp.base;

import android.content.Context;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.Registry;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.cache.DiskLruCacheFactory;
import com.bumptech.glide.module.AppGlideModule;

import java.io.File;

/**
 * @author ch
 * @date 2021/3/30 16:51
 * desc glide 缓存配置
 */
@GlideModule
public class SimpleGlideModule extends AppGlideModule {
    @Override
    public void applyOptions(@NonNull Context context, @NonNull GlideBuilder glideBuilder) {
        if (context.getExternalCacheDir() == null) {
            return;
        }

        String path = context.getExternalCacheDir().getPath();
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        int cacheSize100MegaBytes = 104857600;
        glideBuilder.setDiskCache(new DiskLruCacheFactory(path, cacheSize100MegaBytes));
    }

    @Override
    public void registerComponents(@NonNull Context context, @NonNull Glide glide, @NonNull Registry registry) {

    }

    @Override
    public boolean isManifestParsingEnabled() {
        return false;
    }
}

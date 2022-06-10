package com.h.cheng.filepicker;

import android.content.Intent;
import android.graphics.Color;

import androidx.fragment.app.FragmentActivity;
import androidx.loader.app.LoaderManager;

import com.h.cheng.filepicker.bean.NormalFile;
import com.h.cheng.filepicker.callback.FileLoaderCallbacks;
import com.h.cheng.filepicker.callback.FilterResultCallback;
import com.h.cheng.filepicker.callback.OnLoadResultCallback;
import com.h.cheng.filepicker.config.ImageLoader;
import com.h.cheng.filepicker.config.PickConfig;
import com.h.cheng.filepicker.ui.FilePickActivity;
import com.h.cheng.filepicker.ui.ImgPickActivity;
import com.shenyuan.superapp.base.helper.result.ResultHelper;

import java.util.ArrayList;

/**
 * @author ch
 * @date 2020/8/21-14:39
 * desc
 */
public class PsPickManager {
    /**
     * 图片
     */
    public static final int TYPE_IMAGE = 0;
    /**
     * 视频
     */
    public static final int TYPE_VIDEO = 1;
    /**
     * 音频
     */
    public static final int TYPE_AUDIO = 2;
    /**
     * 文件
     */
    public static final int TYPE_FILE = 3;

    public static final String RESULT_PICK_FILE = "ResultPickFILE";
    /**
     * 配置
     */
    public static final String CONFIG = "CONFIG";


    public static class Buider {
        /**
         * 最大选择数
         */
        private int max;
        /**
         * 标题
         */
        private String title;
        /**
         * 筛选条件
         */
        private String[] suffix;
        /**
         * 返回键
         */
        private int backRes;
        /**
         * 背景颜色
         */
        private int backColor = Color.WHITE;
        /**
         * 标题颜色
         */
        private int titleColor = Color.BLACK;
        /**
         * 右边文字颜色
         */
        private int rightColor;
        /**
         * 右边背景颜色
         */
        private int rightBackColor = Color.WHITE;
        /**
         * 一行展示数目
         * 文件无效
         */
        private int spanCount;

        /**
         * 选中的条目
         */
        private ArrayList<NormalFile> selectList;
        /**
         * 图片加载器
         */
        private ImageLoader imageLoader;
        /**
         * 是否需要相机
         */
        private boolean needCamera = true;
        /**
         * 相机图片
         */
        private int cameraRes;
        /**
         * activity
         */
        private FragmentActivity activity;
        /**
         * 回调
         */
        private OnLoadResultCallback<NormalFile> callback;

        public Buider with(FragmentActivity activity) {
            this.activity = activity;
            return this;
        }

        public Buider callback(OnLoadResultCallback<NormalFile> callback) {
            this.callback = callback;
            return this;
        }

        public Buider max(int max) {
            this.max = max;
            return this;
        }

        public Buider title(String title) {
            this.title = title;
            return this;
        }

        public Buider suffix(String[] suffix) {
            this.suffix = suffix;
            return this;
        }

        public Buider backRes(int backRes) {
            this.backRes = backRes;
            return this;
        }

        public Buider backColor(int backColor) {
            this.backColor = backColor;
            return this;
        }

        public Buider titleColor(int titleColor) {
            this.titleColor = titleColor;
            return this;
        }

        public Buider rightColor(int rightColor) {
            this.rightColor = rightColor;
            return this;
        }

        public Buider rightBackColor(int rightBackColor) {
            this.rightBackColor = rightBackColor;
            return this;
        }

        public Buider spanCount(int spanCount) {
            this.spanCount = spanCount;
            return this;
        }

        public Buider selectList(ArrayList<NormalFile> selectList) {
            this.selectList = selectList;
            return this;
        }

        public Buider imageLoader(ImageLoader imageLoader) {
            this.imageLoader = imageLoader;
            return this;
        }

        public Buider needCamera(boolean needCamera) {
            this.needCamera = needCamera;
            return this;
        }

        public Buider cameraRes(int cameraRes) {
            this.cameraRes = cameraRes;
            return this;
        }

        public PickConfig config() {
            PickConfig pickConfig = new PickConfig();
            pickConfig.setRightBackColor(rightBackColor);
            pickConfig.setBackColor(backColor);
            pickConfig.setRightColor(rightColor);
            pickConfig.setBackRes(backRes);
            pickConfig.setCameraRes(cameraRes);
            pickConfig.setImageLoader(imageLoader);
            pickConfig.setNeedCamera(needCamera);
            pickConfig.setMax(max);
            pickConfig.setSelectList(selectList);
            pickConfig.setSpanCount(spanCount);
            pickConfig.setSuffix(suffix);
            pickConfig.setTitle(title);
            pickConfig.setTitleColor(titleColor);
            return pickConfig;
        }

        public void startImage() {
            if (activity == null) {
                throw new RuntimeException("activity most not be null");
            }
            if (imageLoader == null) {
                throw new RuntimeException("imageLoader most not be null");
            }

            openImagePicker(activity, config(), callback);
        }

        public void startFile() {
            if (activity == null) {
                throw new RuntimeException("activity most not be null");
            }
            openFilePicker(activity, config(), callback);
        }
    }

    /**
     * 选择文件
     *
     * @param activity activity
     * @param config   config
     * @param callback callback
     */
    public static void openFilePicker(FragmentActivity activity, PickConfig config, final OnLoadResultCallback<NormalFile> callback) {
        Intent intent = new Intent(activity, FilePickActivity.class);
        intent.putExtra(CONFIG, config);
        ResultHelper
                .init(activity)
                .startActivityForResult(intent, new ResultHelper.CallBack() {
                    @Override
                    public void onActivityResult(int resultCode, Intent data) {
                        if (resultCode == FragmentActivity.RESULT_OK) {
                            if (data != null) {
                                ArrayList<NormalFile> list = data.getParcelableArrayListExtra(RESULT_PICK_FILE);
                                if (callback != null) {
                                    callback.onResult(list);
                                }
                            }
                        }
                    }
                });
    }

    /**
     * 选择图片
     *
     * @param activity activity
     * @param config   config
     * @param callback callback
     */
    public static void openImagePicker(FragmentActivity activity, PickConfig config, final OnLoadResultCallback<NormalFile> callback) {
        Intent intent = new Intent(activity, ImgPickActivity.class);
        intent.putExtra(CONFIG, config);
        ResultHelper
                .init(activity)
                .startActivityForResult(intent, new ResultHelper.CallBack() {
                    @Override
                    public void onActivityResult(int resultCode, Intent data) {
                        if (resultCode == FragmentActivity.RESULT_OK) {
                            if (data != null) {
                                ArrayList<NormalFile> list = data.getParcelableArrayListExtra(RESULT_PICK_FILE);
                                if (callback != null) {
                                    callback.onResult(list);
                                }
                            }
                        }
                    }
                });
    }

    /**
     * 查询文件
     *
     * @param activity activity
     * @param callback callback
     * @param suffix   suffix
     */
    public static void getFiles(FragmentActivity activity, FilterResultCallback callback, String[] suffix) {
        LoaderManager.getInstance(activity).initLoader(3, null,
                new FileLoaderCallbacks(activity, callback, TYPE_FILE, suffix));
    }

    /**
     * 查询图片
     *
     * @param activity activity
     * @param callback callback
     * @param suffix   suffix
     */
    public static void getImages(FragmentActivity activity, FilterResultCallback callback, String[] suffix) {
        LoaderManager.getInstance(activity).initLoader(2, null,
                new FileLoaderCallbacks(activity, callback, TYPE_IMAGE, suffix));

    }
}

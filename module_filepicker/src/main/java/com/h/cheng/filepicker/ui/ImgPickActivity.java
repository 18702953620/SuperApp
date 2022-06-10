package com.h.cheng.filepicker.ui;

import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.core.os.EnvironmentCompat;
import androidx.recyclerview.widget.GridLayoutManager;

import com.ch.cper.CPermission;
import com.ch.cper.PermissGroup;
import com.ch.cper.listener.PermissListener;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.h.cheng.filepicker.PsPickManager;
import com.h.cheng.filepicker.R;
import com.h.cheng.filepicker.bean.Directory;
import com.h.cheng.filepicker.bean.NormalFile;
import com.h.cheng.filepicker.callback.FilterResultCallback;
import com.h.cheng.filepicker.utils.Utils;
import com.shenyuan.superapp.base.utils.AppUtils;
import com.shenyuan.superapp.base.widget.recy.SpaceDecoration;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * @author ch
 * @date 2020/8/21-18:18
 * desc 选择图片
 */
public class ImgPickActivity extends BasePickActivity {

    private BaseQuickAdapter<NormalFile, BaseViewHolder> fileAdapter;

    private int spanCount = 3;
    private static final int CAMERA_REQUEST_CODE = 1001;
    private String mCameraImagePath;
    private Uri mCameraUri;

    @Override
    protected void setStatusBar() {
        setStatusBarColor();
    }

    @Override
    protected void initView() {
        super.initView();
        fileAdapter = new BaseQuickAdapter<NormalFile, BaseViewHolder>(R.layout.item_pick_img) {
            @Override
            protected void convert(@NonNull BaseViewHolder helper, NormalFile item) {
                if (helper.getLayoutPosition() == 0 && config != null && config.isNeedCamera()) {
                    if (config.getCameraRes() == 0) {
                        helper.setImageResource(R.id.iv_picker_img, R.mipmap.ic_pick_camera);
                    } else {
                        helper.setImageResource(R.id.iv_picker_img, config.getCameraRes());
                    }
                    helper.setGone(R.id.view_picker_shadow, true);
                    helper.setGone(R.id.iv_picker_choose, true);
                } else {
                    helper.setVisible(R.id.view_picker_shadow, true);
                    helper.setVisible(R.id.iv_picker_choose, true);

                    if (item.isSelected()) {
                        helper.setImageResource(R.id.iv_picker_choose, R.mipmap.ic_img_checked);
                        helper.setVisible(R.id.view_picker_shadow, true);
                    } else {
                        helper.setVisible(R.id.view_picker_shadow, false);
                        helper.setImageResource(R.id.iv_picker_choose, R.mipmap.ic_img_uncheck);
                    }
                    if (config != null && config.getImageLoader() != null) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                            //10.0以上需要使用uri加载图片
                            Uri uri = Utils.getImageContentUri(context, item.getPath());
                            config.getImageLoader().loadImage((ImageView) helper.getView(R.id.iv_picker_img), uri);
                        } else {
                            config.getImageLoader().loadImage((ImageView) helper.getView(R.id.iv_picker_img), item.getPath());
                        }
                    }
                }
            }
        };
        fileAdapter. addChildClickViewIds(R.id.ll_picker_choose, R.id.iv_picker_img);
        if (config != null) {
            spanCount = config.getSpanCount();
            if (spanCount <= 1) {
                spanCount = 2;
            }
        }

        binding.rvFilePick.setLayoutManager(new GridLayoutManager(context, spanCount));
        binding.rvFilePick.addItemDecoration(new SpaceDecoration(5, getValuesColor(R.color.color_333333)));
        binding.rvFilePick.setAdapter(fileAdapter);
        //防止闪烁
        if (binding.rvFilePick.getItemAnimator() != null) {
            binding.rvFilePick.getItemAnimator().setChangeDuration(0);
        }

        //加载数据
        PsPickManager.getImages(this, new FilterResultCallback<NormalFile>() {
            @Override
            public void onResult(List<Directory<NormalFile>> list) {
                refreshData(list);
            }
        }, suffix);
    }

    /**
     * 刷新数据
     *
     * @param directories directories
     */
    private void refreshData(List<Directory<NormalFile>> directories) {
        List<NormalFile> list = new ArrayList<>();
        for (Directory<NormalFile> directory : directories) {
            list.addAll(directory.getFiles());
        }

        for (NormalFile file : selectedList) {
            for (NormalFile f : list) {
                if (f.equals(file)) {
                    int index = list.indexOf(file);
                    if (index != -1) {
                        list.get(index).setSelected(true);
                    }
                }
            }
        }
        //如果需要相机拍照
        if (config != null && config.isNeedCamera()) {
            NormalFile normalFile = new NormalFile();
            normalFile.setId(-1);
            list.add(0, normalFile);
        }

        fileAdapter.setNewInstance(list);
    }

    @Override
    protected void addListener() {
        super.addListener();
        fileAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                NormalFile item = fileAdapter.getItem(position);
                if (view.getId() == R.id.ll_picker_choose) {
                    if (item != null) {
                        if (!item.isSelected() && selectedList.size() >= max) {
                            showToast(getString(R.string.picker_most_select));
                            return;
                        }
                        if (item.isSelected()) {
                            item.setSelected(false);
                            selectedList.remove(item);
                        } else {
                            if (!selectedList.contains(item)) {
                                selectedList.add(item);
                                item.setSelected(true);
                            }
                        }
                        fileAdapter.notifyItemChanged(position);
                    }
                    binding.tvPickerSelect.setText("确定(" + (String.format("%d/%d", selectedList.size(), max)) + ")");
                } else if (view.getId() == R.id.iv_picker_img && position == 0 && config.isNeedCamera()) {
                    CPermission.with(context).permiss().permission(PermissGroup.CAMERA).listener(new PermissListener<String>() {
                        @Override
                        public void onGranted(List<String> granted) {
                            openCamera();
                        }

                        @Override
                        public void onDenied(List<String> granted) {

                        }
                    }).start();
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                NormalFile normalFile = new NormalFile();
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    // Android 10 使用图片uri加载
                    String path = Utils.getFilePathFromContentUri(mCameraUri, getContentResolver());
                    normalFile.setPath(path);
                } else {
                    // 使用图片路径加载
                    normalFile.setPath(mCameraImagePath);
                }
                selectedList.add(normalFile);

                callResultBack();
            }
        }
    }

    /**
     * 调起相机拍照
     */
    private void openCamera() {
        Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // 判断是否有相机
        if (captureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            Uri photoUri = null;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                // 适配android 10
                photoUri = createImageUri();
            } else {
                photoFile = createImageFile();
                if (photoFile != null) {
                    mCameraImagePath = photoFile.getAbsolutePath();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        //适配Android 7.0文件权限，通过FileProvider创建一个content类型的Uri
                        photoUri = FileProvider.getUriForFile(this, AppUtils.getMetaDataFromAuthority(context), photoFile);
                    } else {
                        photoUri = Uri.fromFile(photoFile);
                    }
                }
            }

            mCameraUri = photoUri;
            if (photoUri != null) {
                captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
                captureIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
                startActivityForResult(captureIntent, CAMERA_REQUEST_CODE);
            }
        }
    }

    /**
     * 创建图片地址uri,用于保存拍照后的照片 Android 10以后使用这种方法
     */
    private Uri createImageUri() {
        String status = Environment.getExternalStorageState();
        // 判断是否有SD卡,优先使用SD卡存储,当没有SD卡时使用手机存储
        if (status.equals(Environment.MEDIA_MOUNTED)) {
            return getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new ContentValues());
        } else {
            return getContentResolver().insert(MediaStore.Images.Media.INTERNAL_CONTENT_URI, new ContentValues());
        }
    }

    /**
     * 创建保存图片的文件
     */
    private File createImageFile() {
        String imageName = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        if (!storageDir.exists()) {
            storageDir.mkdir();
        }
        File tempFile = new File(storageDir, imageName);
        if (!Environment.MEDIA_MOUNTED.equals(EnvironmentCompat.getStorageState(tempFile))) {
            return null;
        }
        return tempFile;
    }
}

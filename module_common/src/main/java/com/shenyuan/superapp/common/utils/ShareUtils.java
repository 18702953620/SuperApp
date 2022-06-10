package com.shenyuan.superapp.common.utils;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.text.TextUtils;

import androidx.core.content.FileProvider;

import com.shenyuan.superapp.base.utils.AppUtils;
import com.shenyuan.superapp.base.utils.LogUtils;

import java.io.File;

/**
 * @author ch
 * @date 2021/3/31 10:25
 * desc
 */
public class ShareUtils {

    /**
     * 调用系统分享
     *
     * @param context context
     * @param path    path
     */
    public static void shareBySys(Context context, String path) {
        if (TextUtils.isEmpty(path)) {
            return;
        }

        LogUtils.e("cheng", "shareBySys path=" + path);

        File shareFile = new File(path);

        if (!shareFile.exists()) {
            LogUtils.e("cheng", "shareBySys file not fund");
            return;
        }

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);

        //7.0
        Uri uri = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            String authority = AppUtils.getMetaDataFromAuthority(context);
            if (TextUtils.isEmpty(authority)) {
                throw new IllegalArgumentException("请在AndroidManifest.xml 中添加provider标签");
            }
            uri = FileProvider.getUriForFile(context, authority, shareFile);

            // 授予目录临时共享权限
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION
                    | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            //7.0 微信 适配
            if (path.endsWith("mp4")) {
                uri = getVideoContentUri(context, shareFile);
            }
            //7.0 微信 适配
            if (path.endsWith("png") || path.endsWith("jpg") || path.endsWith("jpeg")) {
                uri = getImageContentUri(context, shareFile);
            }

        } else {
            uri = Uri.fromFile(new File(path));
        }

        if (path.endsWith("mp4")) {
            intent.setType("video/*");
        } else if (path.endsWith("png") || path.endsWith("jpg") || path.endsWith("jpeg")) {
            intent.setType("image/*");
        } else {
            intent.setType("*/*");
        }
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        //设置分享列表的标题，并且每次都显示分享列表
        context.startActivity(Intent.createChooser(intent, "分享到"));
    }

    /**
     * Gets the content:// URI from the given corresponding path to a file
     *
     * @param context   context
     * @param videoFile videoFile
     * @return content Uri
     */
    private static Uri getVideoContentUri(Context context, File videoFile) {
        String filePath = videoFile.getAbsolutePath();
        Cursor cursor = context.getContentResolver().query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Video.Media._ID}, MediaStore.Video.Media.DATA + "=? ",
                new String[]{filePath}, null);
        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
            Uri baseUri = Uri.parse("content://media/external/video/media");
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            if (videoFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Video.Media.DATA, filePath);
                return context.getContentResolver().insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }

    /**
     * Gets the content:// URI from the given corresponding path to a file
     *
     * @param context   context
     * @param imageFile imageFile
     * @return content Uri
     */
    private static Uri getImageContentUri(Context context, File imageFile) {
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID}, MediaStore.Images.Media.DATA + "=? ",
                new String[]{filePath}, null);
        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex(MediaStore.MediaColumns._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }

}

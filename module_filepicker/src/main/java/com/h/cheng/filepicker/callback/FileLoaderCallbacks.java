package com.h.cheng.filepicker.callback;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import com.h.cheng.filepicker.PsPickManager;
import com.h.cheng.filepicker.bean.Directory;
import com.h.cheng.filepicker.bean.NormalFile;
import com.h.cheng.filepicker.load.FileLoader;
import com.h.cheng.filepicker.load.ImageLoader;
import com.h.cheng.filepicker.utils.Utils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.provider.BaseColumns._ID;
import static android.provider.MediaStore.Files.FileColumns.MIME_TYPE;
import static android.provider.MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME;
import static android.provider.MediaStore.Images.ImageColumns.BUCKET_ID;
import static android.provider.MediaStore.MediaColumns.DATA;
import static android.provider.MediaStore.MediaColumns.DATE_ADDED;
import static android.provider.MediaStore.MediaColumns.SIZE;
import static android.provider.MediaStore.MediaColumns.TITLE;


/**
 * @author ch
 * @date 2020/8/21-14:42
 * @desc
 */
public class FileLoaderCallbacks implements LoaderManager.LoaderCallbacks<Cursor> {
    private final WeakReference<Context> context;
    private final FilterResultCallback resultCallback;
    /**
     * 类型
     */
    private final int type;
    /**
     * 筛选条件
     */
    private String suffixArgs;
    private CursorLoader loader;

    public FileLoaderCallbacks(Context context, FilterResultCallback callback, int typeFile, String[] suffix) {
        this.context = new WeakReference<>(context);
        this.resultCallback = callback;
        this.type = typeFile;
        if (suffix != null && suffix.length > 0) {
            suffixArgs = obtainSuffixRegex(suffix);
        }
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        Log.e("FileLoaderCallbacks", "onCreateLoader");
        if (type == PsPickManager.TYPE_FILE) {
            loader = new FileLoader(context.get());
        } else if (type == PsPickManager.TYPE_IMAGE) {
            loader = new ImageLoader(context.get());
        }
        return loader;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        Log.e("FileLoaderCallbacks", "onLoadFinished" + "----总共：" + data.getCount() + "条");
        if (type == PsPickManager.TYPE_FILE) {
            onFileResult(data);
        } else if (type == PsPickManager.TYPE_IMAGE) {
            onImageResult(data);
        }

    }


    @Override
    public void onLoaderReset(@NonNull Loader loader) {

    }

    /**
     * 图片结果
     *
     * @param data data
     */
    private void onImageResult(Cursor data) {
        List<Directory<NormalFile>> directories = new ArrayList<>();

        if (data.getPosition() != -1) {
            data.moveToPosition(-1);
        }

        while (data.moveToNext()) {
            //Create a File instance
            NormalFile img = new NormalFile();
            img.setId(data.getLong(data.getColumnIndexOrThrow(_ID)));
            img.setName(data.getString(data.getColumnIndexOrThrow(TITLE)));
            img.setPath(data.getString(data.getColumnIndexOrThrow(DATA)));
            img.setSize(data.getLong(data.getColumnIndexOrThrow(SIZE)));
            img.setBucketId(data.getString(data.getColumnIndexOrThrow(BUCKET_ID)));
            img.setBucketName(data.getString(data.getColumnIndexOrThrow(BUCKET_DISPLAY_NAME)));
            img.setDate(data.getLong(data.getColumnIndexOrThrow(DATE_ADDED)));

            //Create a Directory
            Directory<NormalFile> directory = new Directory<>();
            directory.setId(img.getBucketId());
            directory.setName(img.getBucketName());
            directory.setPath(Utils.extractPathWithoutSeparator(img.getPath()));

            if (!directories.contains(directory)) {
                directory.addFile(img);
                directories.add(directory);
            } else {
                directories.get(directories.indexOf(directory)).addFile(img);
            }
        }

        if (resultCallback != null) {
            resultCallback.onResult(directories);
        }
    }


    /**
     * 文件结果
     *
     * @param data data
     */
    private void onFileResult(Cursor data) {
        List<Directory<NormalFile>> directories = new ArrayList<>();

        if (data.getPosition() != -1) {
            data.moveToPosition(-1);
        }

        while (data.moveToNext()) {
            String path = data.getString(data.getColumnIndexOrThrow(DATA));
            if (path != null && contains(path)) {
                //Create a File instance
                NormalFile file = new NormalFile();
                file.setId(data.getLong(data.getColumnIndexOrThrow(_ID)));
                file.setName(data.getString(data.getColumnIndexOrThrow(TITLE)));
                file.setPath(data.getString(data.getColumnIndexOrThrow(DATA)));
                file.setSize(data.getLong(data.getColumnIndexOrThrow(SIZE)));
                file.setDate(data.getLong(data.getColumnIndexOrThrow(DATE_ADDED)));

                file.setMimeType(data.getString(data.getColumnIndexOrThrow(MIME_TYPE)));

                //Create a Directory
                Directory<NormalFile> directory = new Directory<>();
                directory.setName(Utils.extractFileNameWithSuffix(Utils.extractPathWithoutSeparator(file.getPath())));
                directory.setPath(Utils.extractPathWithoutSeparator(file.getPath()));

                if (!directories.contains(directory)) {
                    directory.addFile(file);
                    directories.add(directory);
                } else {
                    directories.get(directories.indexOf(directory)).addFile(file);
                }
            }
        }

        if (resultCallback != null) {
            resultCallback.onResult(directories);
        }
    }


    private String obtainSuffixRegex(String[] suffixes) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < suffixes.length; i++) {
            if (i == 0) {
                builder.append(suffixes[i].replace(".", ""));
            } else {
                builder.append("|\\.");
                builder.append(suffixes[i].replace(".", ""));
            }
        }
        return ".+(\\." + builder.toString() + ")$";
    }

    /**
     * 是否包含
     *
     * @param path path
     * @return
     */
    private boolean contains(String path) {
        if (suffixArgs == null || suffixArgs.length() == 0) {
            return true;
        }
        String name = Utils.extractFileNameWithSuffix(path);
        Pattern pattern = Pattern.compile(suffixArgs, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }

}

package com.shenyuan.superapp.common;

import android.content.Context;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

/**
 * @author ch
 * @date 2021/3/11 13:29
 * desc 图片压缩
 */
public class CompressManager {

    private Luban.Builder builder;

    public CompressManager(Context context, String path, CompressListener listener) {
        List<String> list = new ArrayList<>();
        list.add(path);
        init(context, list, listener);
    }

    public CompressManager(Context context, List<String> paths, CompressListener listener) {
        init(context, paths, listener);
    }


    private void init(Context context, List<String> paths, CompressListener listener) {
        //压缩图片存储文件夹
        String dirPath = context.getExternalFilesDir(null).getPath() + "/compress/";

        File dir = new File(dirPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        List<String> lists = new ArrayList<>();
        builder = Luban
                .with(context)
                .load(paths)
                .ignoreBy(100)
                .setTargetDir(dirPath)
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onSuccess(File file) {
                        if (file != null && file.exists()) {
                            lists.add(file.getPath());
                        }
                        if (paths.size() == lists.size()) {
                            if (listener != null) {
                                listener.onFinish(removeNull(lists));
                            }
                        }

                    }

                    @Override
                    public void onError(Throwable e) {
                        lists.add(null);
                        if (listener != null) {
                            listener.onError(e);
                        }

                        if (paths.size() == lists.size()) {
                            if (listener != null) {
                                listener.onFinish(removeNull(lists));
                            }
                        }
                    }
                });
    }

    /**
     * 开始
     */
    public void start() {
        if (builder != null) {
            builder.launch();
        }
    }

    /**
     * 移除 null
     *
     * @param list list
     * @return List<String>
     */
    private List<String> removeNull(List<String> list) {
        if (list != null && list.size() > 0) {
            Iterator<String> it = list.iterator();
            while (it.hasNext()) {
                if (it.next() == null) {
                    it.remove();
                }
            }
        }
        return list;
    }

    /**
     * 压缩回调
     */
    public interface CompressListener {
        /**
         * 压缩完成
         *
         * @param list 结果
         */
        void onFinish(List<String> list);

        /**
         * 压缩报错
         *
         * @param e e
         */
        void onError(Throwable e);
    }
}

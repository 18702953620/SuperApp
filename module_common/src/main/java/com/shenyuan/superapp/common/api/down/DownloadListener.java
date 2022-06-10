package com.shenyuan.superapp.common.api.down;

import java.io.File;

/**
 * @author ch
 * @date 2021/3/10 11:58
 * desc
 */
public interface DownloadListener {
    /**
     * 开始下载
     *
     * @param tag 标识
     */
    void onStartDownload(String tag);

    /**
     * 进度
     *
     * @param tag      标识
     * @param progress 进度 0-100
     */
    void onProgress(String tag, int progress, long downSize, long totalSize);

    /**
     * 下载成功
     *
     * @param tag  标识
     * @param file 下载的文件
     */
    void onFinishDownload(String tag, File file);

    /**
     * 下载失败
     *
     * @param tag 标识
     * @param ex  异常
     */
    void onFail(String tag, Throwable ex);
}

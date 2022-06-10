package com.shenyuan.superapp.common.api.down;


import com.shenyuan.superapp.base.api.common.AppConstant;
import com.shenyuan.superapp.base.api.common.BaseCommon;
import com.shenyuan.superapp.base.utils.FileUtils;
import com.shenyuan.superapp.base.utils.LogUtils;
import com.shenyuan.superapp.common.api.CommonApiServer;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * @author ch
 * @date 2021/3/10 11:57
 * desc
 */
public class DownLoadHelper {

    /**
     * 超时15s
     */
    private static final int DEFAULT_TIMEOUT = 15;
    /**
     * 网络工具retrofit
     */
    private Retrofit retrofit;

    private HashMap<String, Disposable> downTaskMp;

    private HashMap<String, DownModel> downModelMp;

    /**
     * 下载进度、完成、失败等的回调事件
     */
    private List<DownloadListener> mDownloadListeners;

    private static DownLoadHelper loadHelper;

    public static DownLoadHelper getInstance() {
        synchronized (Object.class) {
            if (loadHelper == null) {
                loadHelper = new DownLoadHelper();
            }
        }
        return loadHelper;
    }

    public void addDownLoadListener(DownloadListener listener) {
        if (null == mDownloadListeners) {
            mDownloadListeners = new ArrayList<>();
        }
        if (!mDownloadListeners.contains(listener)) {
            mDownloadListeners.add(listener);
        }
    }

    public void removeDownLoadListener(DownloadListener listener) {
        if (null != mDownloadListeners && mDownloadListeners.size() > 0) {
            mDownloadListeners.remove(listener);
        }
    }


    private void initClient(final DownModel downModel) {
        //创建客户端
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .addNetworkInterceptor(chain -> {

                    Request request = chain.request();
                    if (downModel.getDownSize() != 0 && downModel.getTotalSize() != 0) {
                        request = request.newBuilder()
                                .addHeader("RANGE", "bytes=" + downModel.getDownSize() + "-" + downModel.getTotalSize()).build();
                    }
                    Response response = chain.proceed(request);
                    return response.newBuilder().body(new DownloadResponseBody(response.body(),
                            (totalSize, downSize) -> {
                                if (downModel.getTotalSize() == 0) {
                                    downModel.setTotalSize(totalSize);
                                }
                                long currentDown = downSize + downModel.getTotalSize() - totalSize;

                                downModel.setDownSize(currentDown);

                                if (null != mDownloadListeners && mDownloadListeners.size() > 0) {
                                    for (DownloadListener listener : mDownloadListeners) {
                                        listener.onProgress(downModel.getTag(), (int) (currentDown * 100 / downModel.getTotalSize()),
                                                currentDown, downModel.getTotalSize());
                                    }
                                }
                            })).build();
                }).build();

        retrofit = new Retrofit.Builder()
                .baseUrl(AppConstant.BASE_SERVER_URL)
                .client(httpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    private void initClient() {
        //创建客户端
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(AppConstant.BASE_SERVER_URL)
                .client(httpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }


    /**
     * 下载文件
     *
     * @param url      链接
     * @param fileName 文件名
     */
    public void downLoadFile(String url, String fileName, DownloadListener downloadListener) {
        LogUtils.e("DownLoadHelper", "downLoadFile,url=" + url + ",fileName=" + fileName);
        DownModel downModel = new DownModel();
        downModel.setTag("zhxy-" + System.currentTimeMillis());
        downModel.setUrl(url);
        downModel.setName(fileName);
        downModel.setDestDir(BaseCommon.getAppContext().getExternalFilesDir(null).getPath() + "/down/");
        downModel.save();

        addDownLoadListener(downloadListener);


        initClient(downModel);


        downLoad(retrofit.create(CommonApiServer.class)
                .downFiles(url), downModel, downloadListener);
    }

    /**
     * 下载文件
     *
     * @param url 链接
     */
    public void downLoadFile(String url, DownloadListener downloadListener) {
        if (downloadListener != null) {
            downloadListener.onStartDownload("");
        }

        initClient();

        String fileName = "super_teacher_app.apk";
        String dir = BaseCommon.getAppContext().getExternalFilesDir(null).getPath() + "/down/";

        //请求网络 在调度者的io线程
        retrofit.create(CommonApiServer.class)
                .downFiles(url).subscribeOn(Schedulers.io())
                //指定线程保存文件
                .observeOn(Schedulers.io())
                .map(responseBody -> FileUtils.saveFile(responseBody.byteStream(), dir, fileName))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new FileDownLoadSubscriber<File>() {
                    @Override
                    public void onDownLoadSuccess(File file) {
                        if (downloadListener != null) {
                            downloadListener.onFinishDownload("", file);
                        }
                    }

                    @Override
                    public void onDownLoadFail(Throwable throwable) {
                        if (downloadListener != null) {
                            downloadListener.onFail("", throwable);
                        }
                    }
                });
    }


    /**
     * @param downModel 下载实体类
     */
    public void downLoad(Flowable<ResponseBody> flowable, final DownModel downModel, DownloadListener downloadListener) {
        if (null != mDownloadListeners && mDownloadListeners.size() > 0) {
            for (DownloadListener listener : mDownloadListeners) {
                listener.onStartDownload(downModel.getTag());
            }
        }

        //请求网络 在调度者的io线程
        Disposable disposable = flowable.subscribeOn(Schedulers.io())
                //指定线程保存文件
                .observeOn(Schedulers.io())
                .map(responseBody -> FileUtils.saveFile(responseBody.byteStream(), downModel.getDestDir(), downModel.getName()))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new FileDownLoadSubscriber<File>() {
                    @Override
                    public void onDownLoadSuccess(File file) {

                        cancelTask(downModel.getTag());

                        downModel.setDownState(DownModel.DOWN_FINISH);
                        downModel.setFileSize(FileUtils.getFormatSize(file.length()));
                        downModel.update();

                        if (null != mDownloadListeners && mDownloadListeners.size() > 0) {
                            for (DownloadListener listener : mDownloadListeners) {
                                listener.onFinishDownload(downModel.getTag(), file);
                            }
                        }

                        if (downloadListener != null && mDownloadListeners != null) {
                            mDownloadListeners.remove(downloadListener);
                        }
                    }

                    @Override
                    public void onDownLoadFail(Throwable throwable) {

                        cancelTask(downModel.getTag());

                        downModel.setDownState(DownModel.DOWN_FAIL);
                        downModel.update();

                        if (null != mDownloadListeners && mDownloadListeners.size() > 0) {
                            for (DownloadListener listener : mDownloadListeners) {
                                listener.onFail(downModel.getTag(), throwable);
                            }
                        }
                        if (downloadListener != null && mDownloadListeners != null) {
                            mDownloadListeners.remove(downloadListener);
                        }
                    }
                });

        addTaskDisposable(downModel.getTag(), disposable);

        addTaskModel(downModel);
    }


    /**
     * @param downModel 下载实体类
     */
    public void downLoadModel(final DownModel downModel) {

        initClient(downModel);

        if (null != mDownloadListeners && mDownloadListeners.size() > 0) {
            for (DownloadListener listener : mDownloadListeners) {
                listener.onStartDownload(downModel.getTag());
            }
        }
        retrofit.create(CommonApiServer.class)
                .downFiles(downModel.getUrl())
                //请求网络 在调度者的io线程
                .subscribeOn(Schedulers.io())
                //指定线程保存文件
                .observeOn(Schedulers.io())
                .map(responseBody -> {
                    return FileUtils.saveFile(responseBody.byteStream(), downModel.getDownSize(), downModel.getDestDir(), downModel.getName());
                    //文件不存在
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new FileDownLoadSubscriber<File>() {
                    @Override
                    public void onDownLoadSuccess(File file) {
                        cancelTask(downModel.getTag());

                        downModel.setDownState(DownModel.DOWN_FINISH);
                        downModel.setFileSize(FileUtils.getFormatSize(file.length()));
                        downModel.update();

                        if (null != mDownloadListeners && mDownloadListeners.size() > 0) {
                            for (DownloadListener listener : mDownloadListeners) {
                                listener.onFinishDownload(downModel.getTag(), file);
                            }
                        }
                    }

                    @Override
                    public void onDownLoadFail(Throwable throwable) {
                        cancelTask(downModel.getTag());

                        downModel.setDownState(DownModel.DOWN_FAIL);
                        downModel.update();
                        if (null != mDownloadListeners && mDownloadListeners.size() > 0) {
                            for (DownloadListener listener : mDownloadListeners) {
                                listener.onFail(downModel.getTag(), throwable);
                            }
                        }
                    }
                });
    }

    /**
     * 添加
     *
     * @param tag        tag
     * @param disposable disposable
     */
    private void addTaskDisposable(String tag, Disposable disposable) {
        if (downTaskMp == null) {
            downTaskMp = new HashMap<>();
        }
        downTaskMp.put(tag, disposable);
    }

    /**
     * 添加
     *
     * @param model model
     */
    private void addTaskModel(DownModel model) {
        if (downModelMp == null) {
            downModelMp = new HashMap<>();
        }
        downModelMp.put(model.getTag(), model);
    }

    /**
     * 移除
     *
     * @param tag tag
     */
    private void removeDisposable(String tag) {
        if (downTaskMp != null) {
            Disposable dis = downTaskMp.remove(tag);
            if (dis != null) {
                dis.dispose();
            }
        }
    }

    /**
     * 移除
     *
     * @param tag tag
     */
    private void removeModel(String tag) {
        if (downModelMp != null) {
            downModelMp.remove(tag);
        }
    }

    /**
     * 取消任务
     *
     * @param tag tag
     */
    public void cancelTask(String tag) {
        removeDisposable(tag);
        removeModel(tag);
    }

    /**
     * 暂停
     *
     * @param tag tag
     */
    public DownModel pauseTask(String tag) {
        removeDisposable(tag);
        if (downModelMp != null) {
            DownModel model = downModelMp.remove(tag);
            if (model != null) {
                model.setDownState(DownModel.DOWN_PAUSE);
                model.update();
            }
            return model;
        }
        return null;
    }
}

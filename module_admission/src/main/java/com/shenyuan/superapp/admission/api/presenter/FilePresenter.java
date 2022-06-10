package com.shenyuan.superapp.admission.api.presenter;

import com.alibaba.fastjson.JSON;
import com.shenyuan.superapp.admission.api.view.FileView;
import com.shenyuan.superapp.admission.bean.FileTreeBean;
import com.shenyuan.superapp.base.api.BaseSubscriber;
import com.shenyuan.superapp.base.api.JsonRequestBody;
import com.shenyuan.superapp.base.utils.LogUtils;
import com.shenyuan.superapp.common.api.down.DownModel;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * @author ch
 * @date 2021/3/9 13:38
 * desc
 */
public class FilePresenter extends BaseSchoolPresenter<FileView> {
    public FilePresenter(FileView baseView) {
        super(baseView);
    }

    /**
     * 获取文件结构列表
     *
     * @param name name
     */
    public void getFileList(String name) {
        addDisposable(schoolApiServer.getFileList(name), new BaseSubscriber<List<FileTreeBean>>(baseView) {
            @Override
            public void onSuccess(List<FileTreeBean> o) {
                baseView.onFileList(o);
            }
        });
    }

    /**
     * 添加文件夹
     *
     * @param map name
     */
    public void addDir(HashMap<String, Object> map) {
        LogUtils.e("addDir----" + JSON.toJSONString(map));
        addDisposable(schoolApiServer.addDir(new JsonRequestBody(map)), new BaseSubscriber<String>(baseView, true) {
            @Override
            public void onSuccess(String o) {
                baseView.addDir(o);
            }
        });
    }

    /**
     * 添加文件夹
     *
     * @param map name
     */
    public void updateDir(HashMap<String, Object> map) {
        LogUtils.e("addDir----" + JSON.toJSONString(map));
        addDisposable(schoolApiServer.updateDir(new JsonRequestBody(map)), new BaseSubscriber<String>(baseView, true) {
            @Override
            public void onSuccess(String o) {
                baseView.addDir(o);
            }
        });
    }

    /**
     * 删除
     *
     * @param id id
     */
    public void deleteFile(int id) {
        addDisposable(schoolApiServer.deleteFile(id), new BaseSubscriber<String>(baseView, true) {
            @Override
            public void onSuccess(String o) {
                baseView.onDelete(o);
            }
        });
    }

    /**
     * 上传文件
     *
     * @param path path
     * @param id   id
     */
    public void addFile(String path, int id) {
        if (path == null) {
            return;
        }
        File file = new File(path);

        DownModel model = new DownModel();
        model.setTag("zhxy-" + System.currentTimeMillis());
        model.setUrl(path);
        model.setType(1);
        model.setTotalSize(file.length());
        model.setName(file.getName());

        model.save();
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);

        MultipartBody.Part filePart = null;
        try {

            filePart = MultipartBody.Part.createFormData("file", URLEncoder.encode(file.getName(), "UTF-8"), requestFile);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        addDisposable(schoolApiServer.addFile(filePart, id), new BaseSubscriber<String>(baseView) {
            @Override
            public void onSuccess(String o) {
                model.setUploadState(DownModel.UPLOAD_FINISH);
                model.update();
                baseView.onAddFile(o);
            }
        });
    }
}

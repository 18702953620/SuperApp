package com.shenyuan.superapp.common.api.down;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.shenyuan.superapp.base.api.common.AppConstant;
import com.shenyuan.superapp.base.api.common.BaseCommon;
import com.tencent.mmkv.MMKV;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ch
 * @date 2021/3/11 17:30
 * desc
 */
public class DownCommon extends BaseCommon {

    /**
     * 添加
     *
     * @param model model
     */
    public static void add(DownModel model) {
        if (model == null) {
            return;
        }

        List<DownModel> list = getDownModels();
        int index = isContains(model.getTag(), list);
        if (index == -1) {
            list.add(0, model);
        }
        saveModel(list);
    }

    /**
     * 更新完成
     *
     * @param tag tag
     */
    public static void updateFinish(String tag) {
        List<DownModel> list = getDownModels();
        int index = isContains(tag, list);
        if (index != -1) {
            list.get(index).setDownState(DownModel.DOWN_FINISH);
        }
        saveModel(list);
    }

    /**
     * 更新状态
     *
     * @param tag tag
     */
    public static void updateFail(String tag) {
        List<DownModel> list = getDownModels();
        int index = isContains(tag, list);
        if (index != -1) {
            list.get(index).setDownState(DownModel.DOWN_FAIL);
        }
        saveModel(list);
    }

    /**
     * 删除
     *
     * @param tag tag
     */
    public static void delete(String tag) {
        List<DownModel> list = getDownModels();
        int index = isContains(tag, list);
        if (index != -1) {
            DownModel model = list.remove(index);
            //删除本地文件
            if (model != null) {
                String path = model.getDestDir() + model.getName();
                File file = new File(path);
                if (file.exists()) {
                    file.delete();
                }
            }
        }
        saveModel(list);
    }

    /**
     * 更新
     *
     * @param model model
     */
    public static void update(DownModel model) {
        List<DownModel> list = getDownModels();
        int index = isContains(model.getTag(), list);
        if (index == -1) {
            list.add(model);
        } else {
            list.remove(index);
            list.add(index, model);
        }
        saveModel(list);
    }


    /**
     * 获取下载中
     *
     * @return List<DownModel>
     */
    public static List<DownModel> getLoadingModels(int type) {
        List<DownModel> list = getDownModels();
        List<DownModel> loading = new ArrayList<>();
        if (list.size() > 0) {
            for (DownModel model : list) {
                if (type == model.getType()) {
                    if (type == 0) {
                        if (model.getDownState() == DownModel.DOWN_DEFAULT) {
                            loading.add(model);
                        }
                    } else {
                        if (model.getUploadState() == DownModel.UPLOAD_DEFAULT) {
                            loading.add(model);
                        }
                    }
                }
            }
        }
        return loading;
    }

    /**
     * 已完成
     *
     * @return List<DownModel>
     */
    public static List<DownModel> getFinishModels(int type) {
        List<DownModel> list = getDownModels();
        List<DownModel> loading = new ArrayList<>();
        if (list.size() > 0) {
            for (DownModel model : list) {
                if (type == model.getType()) {
                    if (type == 0) {
                        if (model.getDownState() == DownModel.DOWN_FINISH) {
                            loading.add(model);
                        }
                    } else {
                        if (model.getUploadState() == DownModel.UPLOAD_FINISH) {
                            loading.add(model);
                        }
                    }
                }
            }
        }

        return loading;
    }

    public static List<DownModel> getDownModels() {
        String jsonList = getDownString();
        List<DownModel> list;
        if (!TextUtils.isEmpty(jsonList)) {
            list = JSONObject.parseArray(jsonList, DownModel.class);
        } else {
            list = new ArrayList<>();
        }
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }

    /**
     * 保存
     *
     * @param list list
     */
    private static void saveModel(List<DownModel> list) {
        MMKV kv = MMKV.defaultMMKV();
        if (kv != null) {
            kv.encode(AppConstant.APP_DOWN_MODEL, JSON.toJSONString(list));
        }
    }

    /**
     * 是否包含该任务
     *
     * @param tag  tag
     * @param list list
     * @return boolean
     */
    private static int isContains(String tag, List<DownModel> list) {
        if (TextUtils.isEmpty(tag)) {
            return -1;
        }

        if (list != null && list.size() > 0) {
            for (DownModel m : list) {
                if (tag.equals(m.getTag())) {
                    return list.indexOf(m);
                }
            }
        }
        return -1;
    }

    public static String getDownString() {
        MMKV kv = MMKV.defaultMMKV();
        if (kv != null) {
            return kv.decodeString(AppConstant.APP_DOWN_MODEL);
        }
        return "";
    }
}

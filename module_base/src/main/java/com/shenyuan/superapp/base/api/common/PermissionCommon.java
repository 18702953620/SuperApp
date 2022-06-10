package com.shenyuan.superapp.base.api.common;

import android.text.TextUtils;

import com.alibaba.fastjson.JSONObject;
import com.tencent.mmkv.MMKV;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ch
 * @date 2021/4/28 9:36
 * desc
 */
public class PermissionCommon extends BaseCommon {

    public static String getPermissionString() {
        MMKV kv = MMKV.defaultMMKV();
        if (kv != null) {
            return kv.decodeString(AppConstant.APP_PERMISSION);
        }
        return "";
    }

    /**
     * 获取权限列表
     *
     * @return List<String>
     */
    public static List<String> getPermissionList() {
        String jsonList = getPermissionString();
        if (!TextUtils.isEmpty(jsonList)) {
            return JSONObject.parseArray(jsonList, String.class);
        }
        return new ArrayList<>();
    }

    /**
     * 保存所有权限
     *
     * @param list list
     */
    public static void savePermission(List<String> list) {
        MMKV kv = MMKV.defaultMMKV();
        if (kv != null) {
            kv.encode(AppConstant.APP_PERMISSION, JSONObject.toJSONString(list));
        }
    }

    /**
     * 学校库-添加
     *
     * @return boolean
     */
    public static boolean hasSchoolAdd() {
        List<String> permission = getPermissionList();
        if (permission == null || permission.size() == 0) {
            return false;
        }
        return permission.contains("/school/classSchool/add");
    }

    /**
     * 学校库-编辑
     *
     * @return boolean
     */
    public static boolean hasSchoolUpdate() {
        List<String> permission = getPermissionList();
        if (permission == null || permission.size() == 0) {
            return false;
        }
        return permission.contains("/school/classSchool/update");
    }

    /**
     * 学校库-删除
     *
     * @return boolean
     */
    public static boolean hasSchoolRemove() {
        List<String> permission = getPermissionList();
        if (permission == null || permission.size() == 0) {
            return false;
        }
        return permission.contains("/school/classSchool/remove");
    }

    /**
     * 学校库-重新分配
     *
     * @return boolean
     */
    public static boolean hasSchoolDistribution() {
        List<String> permission = getPermissionList();
        if (permission == null || permission.size() == 0) {
            return false;
        }
        return permission.contains("/school/classSchool/distribution");
    }

    /**
     * 目标学校-退回
     *
     * @return boolean
     */
    public static boolean hasTargetSchoolReturn() {
        List<String> permission = getPermissionList();
        if (permission == null || permission.size() == 0) {
            return false;
        }
        return permission.contains("/school/targetSchool/returnBack");
    }

    /**
     * 退回库-重新分配
     *
     * @return boolean
     */
    public static boolean hasReturnSchoolDistribution() {
        List<String> permission = getPermissionList();
        if (permission == null || permission.size() == 0) {
            return false;
        }
        return permission.contains("/school/returnSchool/distribution");
    }

    /**
     * 生源池-添加
     *
     * @return boolean
     */
    public static boolean hasStudentAdd() {
        List<String> permission = getPermissionList();
        if (permission == null || permission.size() == 0) {
            return false;
        }
        return permission.contains("/student/srcStudent/add");
    }

    /**
     * 生源池-编辑
     *
     * @return boolean
     */
    public static boolean hasStudentUpdate() {
        List<String> permission = getPermissionList();
        if (permission == null || permission.size() == 0) {
            return false;
        }
        return permission.contains("/student/srcStudent/update");
    }

    /**
     * 生源池-删除
     *
     * @return boolean
     */
    public static boolean hasStudentRemove() {
        List<String> permission = getPermissionList();
        if (permission == null || permission.size() == 0) {
            return false;
        }
        return permission.contains("/student/srcStudent/remove");
    }

    /**
     * 生源池-分配
     *
     * @return boolean
     */
    public static boolean hasStudentDistribution() {
        List<String> permission = getPermissionList();
        if (permission == null || permission.size() == 0) {
            return false;
        }
        return permission.contains("/student/srcStudent/distribution");
    }

    /**
     * 退回池-重新分配
     *
     * @return boolean
     */
    public static boolean hasReturnStudentDistribution() {
        List<String> permission = getPermissionList();
        if (permission == null || permission.size() == 0) {
            return false;
        }
        return permission.contains("/student/returnStu/distribution");
    }

    /**
     * 我的生源-退回
     *
     * @return boolean
     */
    public static boolean hasMyStudentReturn() {
        List<String> permission = getPermissionList();
        if (permission == null || permission.size() == 0) {
            return false;
        }
        return permission.contains("/student/myStudent/returnBack");
    }

    /**
     * 生源池-编辑
     *
     * @return boolean
     */
    public static boolean hasMyStudentUpdate() {
        List<String> permission = getPermissionList();
        if (permission == null || permission.size() == 0) {
            return false;
        }
        return permission.contains("/student/myStudent/update");
    }

    /**
     * 招生方案-添加
     *
     * @return boolean
     */
    public static boolean hasPlanAdd() {
        List<String> permission = getPermissionList();
        if (permission == null || permission.size() == 0) {
            return false;
        }
        return permission.contains("/assist/zsxtPropgtPlan/add");
    }

    /**
     * 招生方案-编辑
     *
     * @return boolean
     */
    public static boolean hasPlanUpdate() {
        List<String> permission = getPermissionList();
        if (permission == null || permission.size() == 0) {
            return false;
        }
        return permission.contains("/assist/zsxtPropgtPlan/update");
    }

    /**
     * 招生方案-审核
     *
     * @return boolean
     */
    public static boolean hasPlanAduit() {
        List<String> permission = getPermissionList();
        if (permission == null || permission.size() == 0) {
            return false;
        }
        return permission.contains("/assist/zsxtPropgtPlan/aduit");
    }


    /**
     * 招生反馈-添加
     *
     * @return boolean
     */
    public static boolean hasFeedAdd() {
        List<String> permission = getPermissionList();
        if (permission == null || permission.size() == 0) {
            return false;
        }
        return permission.contains("/assist/zsxtPropgtFeedback/add");
    }

    /**
     * 招生反馈-编辑
     *
     * @return boolean
     */
    public static boolean hasFeedUpdate() {
        List<String> permission = getPermissionList();
        if (permission == null || permission.size() == 0) {
            return false;
        }
        return permission.contains("/assist/zsxtPropgtFeedback/update");
    }

    /**
     * 招生反馈-删除
     *
     * @return boolean
     */
    public static boolean hasFeedDelete() {
        List<String> permission = getPermissionList();
        if (permission == null || permission.size() == 0) {
            return false;
        }
        return permission.contains("/assist/zsxtPropgtFeedback/delete");
    }

    /**
     * 招生报账-添加
     *
     * @return boolean
     */
    public static boolean hasClaimAdd() {
        List<String> permission = getPermissionList();
        if (permission == null || permission.size() == 0) {
            return false;
        }
        return permission.contains("/assist/zsxtPropgtClaim/add");
    }

    /**
     * 招生报账-编辑
     *
     * @return boolean
     */
    public static boolean hasClaimUpdate() {
        List<String> permission = getPermissionList();
        if (permission == null || permission.size() == 0) {
            return false;
        }
        return permission.contains("/assist/zsxtPropgtClaim/update");
    }

    /**
     * 招生报账-删除
     *
     * @return boolean
     */
    public static boolean hasClaimDelete() {
        List<String> permission = getPermissionList();
        if (permission == null || permission.size() == 0) {
            return false;
        }
        return permission.contains("/assist/zsxtPropgtClaim/delete");
    }

    /**
     * 招生报账-审核
     *
     * @return boolean
     */
    public static boolean hasClaimAduit() {
        List<String> permission = getPermissionList();
        if (permission == null || permission.size() == 0) {
            return false;
        }
        return permission.contains("/assist/zsxtPropgtClaim/aduit");
    }

    /**
     * 招生资料-新建文件夹
     *
     * @return boolean
     */
    public static boolean hasCreateFolder() {
        List<String> permission = getPermissionList();
        if (permission == null || permission.size() == 0) {
            return false;
        }
        return permission.contains("/data/zsxtDataStore/createFolder");
    }

    /**
     * 招生资料-上传文件
     *
     * @return boolean
     */
    public static boolean hasAddFile() {
        List<String> permission = getPermissionList();
        if (permission == null || permission.size() == 0) {
            return false;
        }
        return permission.contains("/data/zsxtDataStore/addFile");
    }

    /**
     * 招生资料-删除文件
     *
     * @return boolean
     */
    public static boolean hasDeleteFile() {
        List<String> permission = getPermissionList();
        if (permission == null || permission.size() == 0) {
            return false;
        }
        return permission.contains("/data/zsxtDataStore/deleted");
    }

    /**
     * 招生资料-修改文件夹
     *
     * @return boolean
     */
    public static boolean hasUpdateDir() {
        List<String> permission = getPermissionList();
        if (permission == null || permission.size() == 0) {
            return false;
        }
        return permission.contains("/data/zsxtDataStore/update");
    }
}

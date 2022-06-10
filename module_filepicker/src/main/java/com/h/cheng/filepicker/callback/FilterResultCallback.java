package com.h.cheng.filepicker.callback;

import com.h.cheng.filepicker.bean.Directory;

import java.util.List;

/**
 * @author ch
 * @date 2020/8/21-14:44
 * @desc 回调
 */
public interface FilterResultCallback<T> {
    /**
     * 结果
     *
     * @param directories directories
     */
    void onResult(List<Directory<T>> directories);
}

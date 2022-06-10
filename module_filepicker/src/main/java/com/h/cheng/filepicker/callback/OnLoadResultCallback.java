package com.h.cheng.filepicker.callback;

import java.util.ArrayList;

/**
 * @author ch
 * @date 2020/8/21-15:53
 * @desc 选择结果
 */
public interface OnLoadResultCallback<T> {
    /**
     * 选择结果
     *
     * @param list list
     */
    void onResult(ArrayList<T> list);
}

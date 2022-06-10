package com.shenyuan.superapp.base.preview;

import android.content.Intent;

import com.chad.library.adapter.base.BaseQuickAdapter;

/**
 * @author ch
 * @date 2020/7/29-16:07
 * desc 处理 onActivityReenter 回调
 */
public interface OnActivityReenter {

    /**
     * onActivityReenter 回调处理
     *
     * @param resultCode   resultCode
     * @param intent       intent
     * @param quickAdapter quickAdapter
     * @param idRes        idRes
     */
    void onActivityReenter(int resultCode, Intent intent, BaseQuickAdapter quickAdapter, int idRes);
}

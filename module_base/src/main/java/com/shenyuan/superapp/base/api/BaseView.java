package com.shenyuan.superapp.base.api;

/**
 * @author ch
 * @date 2020/4/21-10:20
 * desc  BaseView
 */
public interface BaseView {
    /**
     * 显示dialog
     */
    void showLoading();

    /**
     * 隐藏 dialog
     */

    void hideLoading();

    /**
     * 显示错误信息
     *
     * @param msg msg
     */
    void showError(String msg);


    /**
     * @param code code
     * @param msg  msg
     */
    void onErrorCode(String code, String msg);
}

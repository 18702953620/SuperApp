package com.shenyuan.superapp.common.scan;

/**
 * @author ch
 * @date 2021/1/4-11:47
 * desc
 */
public interface ScanCallBack {

    /**
     * 扫描结果
     *
     * @param result result
     */
    void onResult(ScanDataBean result);
}

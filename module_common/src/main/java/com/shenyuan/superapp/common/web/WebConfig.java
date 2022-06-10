package com.shenyuan.superapp.common.web;

import com.tencent.smtt.export.external.TbsCoreSettings;
import com.tencent.smtt.sdk.QbSdk;

import java.util.HashMap;

/**
 * @author ch
 * @date 2020/8/4-13:46
 * @desc 首次初始化冷启动优化
 */
public class WebConfig {
    /**
     * TBS内核首次使用和加载时，ART虚拟机会将Dex文件转为Oat，
     * 该过程由系统底层触发且耗时较长，很容易引起anr问题，
     * 解决方法是使用TBS的 ”dex2oat优化方案“。
     */
    public static void init() {
        // 在调用TBS初始化、创建WebView之前进行如下配置
        HashMap<String, Object> map = new HashMap();
        map.put(TbsCoreSettings.TBS_SETTINGS_USE_SPEEDY_CLASSLOADER, true);
        map.put(TbsCoreSettings.TBS_SETTINGS_USE_DEXLOADER_SERVICE, true);
        QbSdk.initTbsSettings(map);
    }
}

package com.shenyuan.module_mvvm.ui.login;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.shenyuan.superapp.base.BuildConfig;
import com.shenyuan.superapp.base.api.common.BaseCommon;
import com.tencent.mmkv.MMKV;


/**
 * @author ch
 * @date 2020/12/15-16:55
 * desc 程序入口
 */
public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        // 这两行必须写在init之前，否则这些配置在init过程中将无效
        if (BuildConfig.DEBUG) {
            // 打印日志
            ARouter.openLog();
            // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
            ARouter.openDebug();
        }
        // 尽可能早，推荐在Application中初始化
        ARouter.init(this);


        BaseCommon.init(this);
        //本地存储
        MMKV.initialize(this);

    }

}

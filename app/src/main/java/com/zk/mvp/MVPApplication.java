package com.zk.mvp;

import android.app.Application;
import android.content.Context;

import com.blankj.utilcode.util.Utils;
import com.tencent.bugly.Bugly;

public class MVPApplication extends Application {
    private static Context context;
    public static final String TAG_ALL = "TAG_ALL";

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        //如果不需要阿里热修复  注释这行   并且在AndroidManifest切换Application！
//        SophixManager.getInstance().queryAndLoadNewPatch();
        //bugly初始化
//        CrashReport.initCrashReport(getApplicationContext(),"e425870bea",false);
        //腾讯应用升级初始化   如果你只需要使用bugly那么注释这句话并且在build.gradle修改依赖，如果你需要使用应用升级功能，注释bugly初始化代码，使用这行代码统一初始化
        //这个是APP更新升级   阿里是热修复
        Bugly.init(getApplicationContext(), "e425870bea", false);
        //工具类
        Utils.init(this);

//        TUIKitConfigs configs = TUIKit.getConfigs();
//        configs.setSdkConfig(new TIMSdkConfig(SDKAPPID));
//        configs.setCustomFaceConfig(new CustomFaceConfig());
//        configs.setGeneralConfig(new GeneralConfig());
//
//        TUIKit.init(this, SDKAPPID, configs);
    }

    public static Context getContext() {
        return context;
    }
}

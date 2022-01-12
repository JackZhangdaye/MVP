package com.zk.mvp;

import android.app.Application;
import android.content.Context;

import com.zk.mvp.utils.MeUtils.LogUtils;
import com.zk.mvp.utils.MeUtils.SizeUtils;

public class MVPApplication extends Application {
    private static Context context;
    public static final String TAG_ALL = "TAG_ALL";

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        //工具类
        LogUtils.getInstance().init(true);
        SizeUtils.getInstance().init(this);
    }

    public static Context getContext() {
        return context;
    }
}

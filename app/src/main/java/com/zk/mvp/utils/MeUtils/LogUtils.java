package com.zk.mvp.utils.MeUtils;

import android.util.Log;

public class LogUtils {
    private static LogUtils instance = null;
    private static boolean isDebug = true;

    private LogUtils(){
    }

    public static LogUtils getInstance() {
        if (instance == null) {
            synchronized (LogUtils.class) {
                if (instance == null) {
                    instance = new LogUtils();
                }
            }
        }
        return instance;
    }

    public void init(boolean isDebug){
        LogUtils.isDebug = isDebug;
    }

    public void i(String TAG,String msg){
        if (isDebug) Log.i(TAG, msg);
    }

    private void e(String TAG,String msg){
        if (isDebug) Log.e(TAG, msg);
    }
}

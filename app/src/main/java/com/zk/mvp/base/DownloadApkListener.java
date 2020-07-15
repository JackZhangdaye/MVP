package com.zk.mvp.base;

public interface DownloadApkListener {
    void onDownStart();
    void onDownProgress(int p,Long max,Long min);
    void onDownFinish(String path);
    void onDownError(String msg);
}

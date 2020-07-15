package com.zk.mvp;

import android.content.Context;
import android.util.Log;

import androidx.annotation.Keep;

import com.taobao.sophix.PatchStatus;
import com.taobao.sophix.SophixApplication;
import com.taobao.sophix.SophixEntry;
import com.taobao.sophix.SophixManager;

public class SophixStubApplication extends SophixApplication {
    public static final String TAG = "ALiApplication";
    // 此处SophixEntry应指定真正的Application，并且保证RealApplicationStub类名不被混淆。
    @Keep
    @SophixEntry(MVPApplication.class)
    static class RealApplicationStub  {}
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        initSophix();
    }
    private void initSophix() {
        Log.i(TAG, "initSophix: ");
        String appVersion = "0.0.0";
        try {
            appVersion = this.getPackageManager()
                    .getPackageInfo(this.getPackageName(), 0)
                    .versionName;
        } catch (Exception e) {
            Log.i(TAG, "initSophix: "+e.getMessage());
        }
        final SophixManager instance = SophixManager.getInstance();
        instance.setContext(this).setAppVersion(appVersion).setSecretMetaData(null, null, null).setEnableDebug(true).setEnableFullLog()
                .setPatchLoadStatusStub((mode, code, info, handlePatchVersion) -> {
                    if (code == PatchStatus.CODE_LOAD_SUCCESS) {
                        Log.i(TAG, "sophix load patch success!");
                    } else if (code == PatchStatus.CODE_LOAD_RELAUNCH) {
                        // 如果需要在后台重启，建议此处用SharePreference保存状态。
                        Log.i(TAG, "sophix preload patch success. restart app to make effect.");
                    }
                }).initialize();
    }
}

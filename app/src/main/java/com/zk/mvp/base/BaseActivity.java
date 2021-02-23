package com.zk.mvp.base;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.AdaptScreenUtils;
import com.blankj.utilcode.util.PermissionUtils;
import com.blankj.utilcode.util.SPUtils;
import com.blankj.utilcode.util.ScreenUtils;
import com.zk.mvp.http.RxPresenter;
import com.zk.mvp.mvp.view.dialog.ActingDialog;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity<V, P extends BaseContract.BasePresenter<V>>  extends AppCompatActivity {
    protected abstract int getLayoutId();
    protected abstract void initWindow();
    protected abstract P initPresenter();
    protected abstract void initViews();
    protected abstract void initDatas();

    private static final String TAG = "BaseActivity";
    private Unbinder unbinder;
    public ActingDialog actingDialog;
    protected P mPresenter;

    /**
     * 权限组
     */
    private static final String[] permissionsGroup = new String[]{
            Manifest.permission.WRITE_SETTINGS//允许程序读取或写入系统设置
            ,Manifest.permission.WRITE_EXTERNAL_STORAGE//允许程序写入外部存储,如SD卡上写文件
            ,Manifest.permission.READ_EXTERNAL_STORAGE
            ,Manifest.permission.ACCESS_NETWORK_STATE
            ,Manifest.permission.ACCESS_WIFI_STATE
            ,Manifest.permission.REQUEST_INSTALL_PACKAGES
            };

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置软键盘  沉浸式
        initWindow();
        //设置屏幕为竖屏
        ScreenUtils.setPortrait(this);
        AdaptScreenUtils.adaptWidth(getResources(), 375);
        //布局文件
        setContentView(getLayoutId());
        unbinder = ButterKnife.bind(this);
        //初始化
        mPresenter = initPresenter();
        initViews();
        initDatas();
        PermissionUtils.permission(permissionsGroup).request();
    }

    /**
     * 禁止app字体跟随系统
     * @return
     */
    @Override
    public Resources getResources() {
        Resources resources = super.getResources();
        Configuration configuration = new Configuration();
        configuration.setToDefaults();
        resources.updateConfiguration(configuration, resources.getDisplayMetrics());
        return resources;
    }

    private long mExitTime;
    public void showDialog(){
        //防止重复触发
        if ((System.currentTimeMillis() - mExitTime) > 500) {
            mExitTime = System.currentTimeMillis();
        }else {
            return;
        }
        if(actingDialog == null){
            actingDialog = new ActingDialog();
            actingDialog.setCallBack(() -> ((RxPresenter)mPresenter).cancelCurrent());
        }
        if (!actingDialog.isVisible()) {
            actingDialog.show(getSupportFragmentManager(),"ActingDialog");
        }
    }

    public void disMissDialog(){
        if (actingDialog != null){
            actingDialog.dismiss();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (this.isFinishing()) {
            unbinder.unbind();
            if (mPresenter != null) {
                mPresenter.detachView();
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(SPUtils.getInstance().getBoolean("isNight")){
            night();
        }else{
            day();
        }
    }

    private TextView mNightView;
    private WindowManager mWindowManager;
    private void night() {
        if (mWindowManager == null) {
            mWindowManager = getWindowManager();
            mNightView = new TextView(this);
            mNightView.setBackgroundColor(0xaa000000);
        }
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.TYPE_APPLICATION,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE | WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);
        lp.gravity = Gravity.BOTTOM;
        lp.y = 10;
        try {
            mWindowManager.addView(mNightView, lp);
        } catch (Exception ex) {
        }
        Window window = getWindow();
        //After LOLLIPOP not translucent status bar
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //Then call setStatusBarColor.
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(0xaa000000);
    }
    private void day() {
        try {
            if (mWindowManager != null) {
                mWindowManager.removeView(mNightView);
            }
        } catch (Exception ex) {
        }
    }

}

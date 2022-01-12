package com.zk.mvp.base;

import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.zk.mvp.http.RxPresenter;
import com.zk.mvp.mvp.view.dialog.ActingDialog;

public abstract class BaseActivity<V, P extends BaseContract.BasePresenter<V>>  extends AppCompatActivity {
    protected abstract int getLayoutId();
    protected abstract void initWindow();
    protected abstract P initPresenter();
    protected abstract void initViews();
    protected abstract void initData();

    private static final String TAG = "BaseActivity";
    public ActingDialog actingDialog;
    protected P mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置软键盘  沉浸式
        initWindow();
        //布局文件
        setContentView(getLayoutId());
        //初始化
        mPresenter = initPresenter();
        initViews();
        initData();

        localDeviceLauncher = registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), result -> {
                    boolean grant = true;
                    for (int i = 0; i < result.size(); i++) { if (!result.get(permissionsGroup[i])) grant = false; }
                    permissionsCallBack(grant,type);
                });
    }

    /**
     * 禁止app字体跟随系统
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
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

//      ,Manifest.permission.WRITE_EXTERNAL_STORAGE//允许程序写入外部存储,如SD卡上写文件
//            ,Manifest.permission.READ_EXTERNAL_STORAGE
    //申请权限
    private ActivityResultLauncher<String[]> localDeviceLauncher;
    private String[] permissionsGroup;
    private int type;
    protected void applyPermissions(String[] permissionsGroup,int type){
        this.permissionsGroup = permissionsGroup;
        this.type = type;
        localDeviceLauncher.launch(permissionsGroup);
    }

    //获取权限回调
    protected void permissionsCallBack(boolean isAll,int type) {

    }
}

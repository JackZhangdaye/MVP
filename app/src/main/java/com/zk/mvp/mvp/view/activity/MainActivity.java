package com.zk.mvp.mvp.view.activity;

import android.annotation.SuppressLint;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import com.zk.mvp.R;
import com.zk.mvp.base.BaseActivity;
import com.zk.mvp.base.BaseContract;
import com.zk.mvp.base.DownloadApkListener;
import com.zk.mvp.http.HttpManager;
import com.zk.mvp.http.HttpService;
import com.zk.mvp.http.ObserverImp;
import com.zk.mvp.mvp.view.activity.login.LoginActivity;
import com.zk.mvp.mvp.view.dialog.DefaultDialog;
import com.zk.mvp.mvp.view.dialog.ProgressDialog;
import com.zk.mvp.mvp.view.dialog.UpAppDialog;
import com.zk.mvp.utils.FileDownUtils;
import com.zk.mvp.utils.Utils;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

import static com.blankj.utilcode.util.AppUtils.getAppName;

public class MainActivity extends BaseActivity implements DownloadApkListener {
    private static final String TAG = "MainActivity";
    private UpAppDialog upAppDialog = null;
    private ProgressDialog progressDialog = null;
    private String filePath;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initWindow() {

    }

    @Override
    protected BaseContract.BasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initDatas() {
        upAppDialog = new UpAppDialog();
        upAppDialog.setListener(() -> {
            if (progressDialog == null) {
                progressDialog = new ProgressDialog();
                progressDialog.setOnOpenAPKListener(() -> {
                    FileDownUtils.getInstance().installApkO(MainActivity.this,filePath);
                    progressDialog.dismiss();
                });
            }
            progressDialog.show(getSupportFragmentManager(),"");
            FileDownUtils.getInstance().downApkFile("https://wutong-apk.cdn.bcebos.com/","wt/pkg/pub/online/10012125", MainActivity.this);
        });

        Log.i(TAG, "initDatas: "+getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS));
//        upAppDialog.show(getSupportFragmentManager(),"");
    }

    @Override
    public void onDownStart() {
        Log.i(TAG, "onDownStart: ");
    }

    @Override
    public void onDownProgress(int p, Long max, Long min) {
        new Handler(MainActivity.this.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                progressDialog.setProgress(p,max,min);
            }
        });
    }

    @Override
    public void onDownFinish(String path) {
        filePath = path;
        new Handler(MainActivity.this.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                progressDialog.onDownFinish();
            }
        });
    }

    @Override
    public void onDownError(String msg) {
        Log.i(TAG, "onDownError: "+msg);
    }
}
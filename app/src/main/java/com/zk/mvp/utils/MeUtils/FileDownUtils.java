package com.zk.mvp.utils.MeUtils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;

import androidx.core.content.FileProvider;

import com.zk.mvp.base.BaseActivity;
import com.zk.mvp.base.DownloadApkListener;
import com.zk.mvp.http.HttpService;
import com.zk.mvp.mvp.view.dialog.DefaultDialog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

import static com.zk.mvp.MVPApplication.getContext;

/**}
 * 文件下载
 */
public class FileDownUtils {
    private static final String TAG = "FileDownUtils";
    private static FileDownUtils instance = null;

    private FileDownUtils(){}

    public static FileDownUtils getInstance(){
        if (instance == null) {
            instance = new FileDownUtils();
        }
        return instance;
    }

    /**
     * 下载apk
     * @param downloadApkListener 下载回调
     */
    public void downApkFile(String baseUrl,String url,DownloadApkListener downloadApkListener) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        HttpService httpService = retrofit.create(HttpService.class);
        Call<ResponseBody> call = httpService.down(url);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Log.i(TAG, "onResponse: "+response.body());
                new Thread(new FileDownloadRun(response,downloadApkListener)).start();
            }
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i(TAG, "onFailure: "+t.getMessage());
            }
        });
    }

    private class FileDownloadRun implements Runnable{
        Response<ResponseBody> mResponseBody;
        DownloadApkListener mDownloadApkListener;
        public FileDownloadRun(Response<ResponseBody> responseBody,DownloadApkListener downloadApkListener){
            Log.i(TAG, "FileDownloadRun: 11111111111111111111111"   +responseBody);
            mResponseBody = responseBody;
            mDownloadApkListener = downloadApkListener;
        }

        @Override
        public void run() {
            Log.i(TAG, "run: 222222222222222");
            writeResponseBodyToDisk(mResponseBody.body(),mDownloadApkListener);
        }
    }

    /**
     *
     * @param body
     * @param downloadListener
     * @return
     */
    private boolean writeResponseBodyToDisk(ResponseBody body,DownloadApkListener downloadListener) {
        if (downloadListener!=null)
            downloadListener.onDownStart();
        try {
            String path = "";
            // 改成自己需要的存储位置
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
//                path = getContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS) +"/"+ getAppName() +".apk";
//            }else {
//                path = getContext().getExternalCacheDir() +"/"+ getAppName() +".apk";
//            }
//            path = getContext().getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS) +"/"+ getAppName() +".apk";

//            Utils.deleteSingleFile(path);
            File file = new File(path);
            Log.e(TAG,"writeResponseBodyToDisk() file="+file.getPath());
            if (file.exists()) {
                file.delete();
            }
            InputStream inputStream = null;
            OutputStream outputStream = null;
            try {
                byte[] fileReader = new byte[4096];
                Log.i(TAG, "writeResponseBodyToDisk-----------------------------: "+body);
                if (body == null) {
                    if (downloadListener != null) {
                        downloadListener.onDownError("下载异常！");
                    }
                    return false;
                }
                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;
                inputStream = body.byteStream();
                outputStream = new FileOutputStream(file);
                while (true) {
                    int read = inputStream.read(fileReader);
                    if (read == -1) {
                        break;
                    }
                    outputStream.write(fileReader, 0, read);
                    fileSizeDownloaded += read;
                    //计算当前下载百分比，并经由回调传出
                    int progress = (int) (100 * fileSizeDownloaded / fileSize);
                    if (downloadListener!=null)
                        downloadListener.onDownProgress(progress,fileSize,fileSizeDownloaded);
//                    progressView.setMax(fileSize);
//                    progressView.setProgressAndText(fileSizeDownloaded,progress+" %");
                    Log.d(TAG, "file download: " + fileSizeDownloaded + " of " + fileSize);
                }
                if (downloadListener!=null)
                    downloadListener.onDownFinish(file.getPath());
                outputStream.flush();

                return true;
            } catch (IOException e) {
                if (downloadListener!=null)
                    downloadListener.onDownError(""+e.getMessage());
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }

    // 下载成功，开始安装,兼容8.0安装位置来源的权限
    public void installApkO(BaseActivity activity, String downloadApkPath) {
        Log.i(TAG, "installApkO: ");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //是否有安装位置来源的权限
            boolean haveInstallPermission = activity.getPackageManager().canRequestPackageInstalls();
            if (haveInstallPermission) {
                Log.i(TAG,"8.0手机已经拥有安装未知来源应用的权限，直接安装！");
                installApk(activity,downloadApkPath);
            } else {
                Log.i(TAG, "installApkO: 无权限");
                new DefaultDialog.Builder().tipsContent("更新APP需要开启安装权限，请手动打开！").title("权限申请").onSureClickListener(new DefaultDialog.OnSureClickListener() {
                    @Override
                    public void sure() {
                        toInstallPermissionSettingIntent(activity);
                    }

                    @Override
                    public void cancel() {

                    }
                }).build().show(activity.getSupportFragmentManager(),"");
            }
        } else {
            installApk(activity,downloadApkPath);
        }
    }

    //开启安装未知来源权限
    private void toInstallPermissionSettingIntent(BaseActivity context) {
        Uri packageURI = Uri.parse("package:"+context.getPackageName());
        Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES,packageURI);
        //系统将打开是未知来源应用的管理列表，需要用户手动设置未知来源应用安装权限
        context.startActivityForResult(intent, 11);
    }

    //安装
    private void installApk(Context context,String downloadApk) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        File file = new File(downloadApk);
        Log.i(TAG, "installApk: "+downloadApk);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Uri apkUri = FileProvider.getUriForFile(context, AppUtils.getAppPackageName()+".fileProvider", file);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
        } else {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Uri uri = Uri.fromFile(file);
            intent.setDataAndType(uri, "application/vnd.android.package-archive");
        }
        context.startActivity(intent);
    }

}

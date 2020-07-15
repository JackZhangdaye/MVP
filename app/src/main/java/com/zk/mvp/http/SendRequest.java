package com.zk.mvp.http;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zk.mvp.base.Base;
import com.zk.mvp.base.BaseCallBack;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rx.Observable;

/**
 * 简单的post get 请求
 *
 */
public class SendRequest {
    private static final String TAG = "SendRequest";

    public static void PostRequest(String url, String [] names , String [] data, BaseCallBack callBack){
//        RequestBody imageBody = new MultipartBody.Builder()
//                .setType(MultipartBody.FORM)
//                .addFormDataPart("avatar", fileImg.getName(), RequestBody.create(MediaType.parse("multipart/form-data"), fileImg))
//                .build();
        OkHttpClient client = new OkHttpClient();
        //表单添加数据
        FormBody.Builder formBody = new FormBody.Builder();
        for (int i = 0; i < data.length; i++) {
            formBody.add(names[i],data[i]);
        }
        Request request = new Request
                .Builder()
                .post(formBody.build())
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callBack.onErr(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String data = response.body().string();
                Type type = new TypeToken<Base>() {}.getType();
                Base base = new Gson().fromJson(data, type);
                Log.i(TAG, "PostOnResponse: "+data);
                if (base.getCode() == 200) {
                    callBack.onSus(data);
                }else {
                    callBack.onErr(base.getMessage());
                }
            }
        });
    }


    public static void GetRequest(String url,BaseCallBack callBack){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request
                .Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callBack.onErr(e.getMessage());
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String data = response.body().string();
                Type type = new TypeToken<Base>() {}.getType();
                Base base = new Gson().fromJson(data, type);
                Log.i(TAG, "GetOnResponse: "+data);
                if (base.getCode() == 0) {
                    callBack.onSus(data);
                }else {
                    callBack.onErr(base.getMessage());
                }
            }
        });
    }



}

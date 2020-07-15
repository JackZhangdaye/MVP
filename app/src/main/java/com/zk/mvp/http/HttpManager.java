package com.zk.mvp.http;

import androidx.multidex.BuildConfig;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class HttpManager {
    private final static Long OUT_TIME = 10000l;
    private static final String TAG = "HttpManage";
    private  static OkHttpClient client;
    private static HttpService httpService;
    private static Retrofit retrofit;

    public static HttpService getHttpService(){
        httpService = getRetrofit().create(HttpService.class);
        return httpService;
    }

    public static Retrofit getRetrofit(){
        if (retrofit == null) {
            synchronized (HttpManager.class){
                if (retrofit == null) {
                    //添加一个log拦截器,打印log
                    HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
                    if (BuildConfig.DEBUG) {//开发模式中记录整个body的日志
                        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                    } else {//开发模式中记录基本的一些日志，如状态值返回200
                        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
                    }


                    //添加请求头
//                    Interceptor interceptor = chain -> {
//                        Request request = chain.request();
//                        Request.Builder builder1 = request.newBuilder();
//                        Request build = builder1.addHeader("apikey", "ac7c302dc489a69082cbee6********").build();
//                        return chain.proceed(build);
//                    };

                    OkHttpClient client = new OkHttpClient().newBuilder()
                            .readTimeout(OUT_TIME, TimeUnit.SECONDS)//设置读取超时时间
                            .connectTimeout(OUT_TIME, TimeUnit.SECONDS)//设置请求超时时间
                            .writeTimeout(OUT_TIME,TimeUnit.SECONDS)//设置写入超时时间
                            .addInterceptor(httpLoggingInterceptor)//添加打印拦截器
//                            .addInterceptor(interceptor)
                            .retryOnConnectionFailure(true)//设置出现错误进行重新连接。
                            .build();
                    // 获取retrofit的实例
                    retrofit = new Retrofit
                            .Builder()
                            .baseUrl(HttpService.BASE_URL)
                            .client(client)
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                }
            }
        }
        return retrofit;
    }
}

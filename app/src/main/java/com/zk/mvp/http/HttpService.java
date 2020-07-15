package com.zk.mvp.http;

import com.zk.mvp.base.Base;
import com.zk.mvp.mvp.model.LoginModel;

import okhttp3.FormBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

public interface HttpService {
    String BASE_URL = "http://103.107.236.126/";

    @FormUrlEncoded
    @POST("zucai/user/login")
    Observable<LoginModel> login(@Field("mobile")String mobile, @Field("sign")String sign, @Field("type")String type, @Field("password")String password, @Field("code")String code);

    @Streaming
    @GET
    Call<ResponseBody> down(@Url String url);


}

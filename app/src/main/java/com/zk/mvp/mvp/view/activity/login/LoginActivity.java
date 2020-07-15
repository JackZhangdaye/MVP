package com.zk.mvp.mvp.view.activity.login;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.AppUtils;
import com.zk.mvp.R;
import com.zk.mvp.base.BaseActivity;
import com.zk.mvp.base.DownloadApkListener;
import com.zk.mvp.http.HttpService;
import com.zk.mvp.mvp.contract.LoginContract;
import com.zk.mvp.mvp.model.LoginModel;
import com.zk.mvp.mvp.persenter.LoginPresenter;
import com.zk.mvp.utils.FileDownUtils;
import com.zk.mvp.utils.ToastUtils;
import com.zk.mvp.utils.ui.CircleProgressView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.http.Url;

import static com.blankj.utilcode.util.AppUtils.getAppName;
import static com.zk.mvp.utils.Utils.deleteSingleFile;

public class LoginActivity extends BaseActivity<LoginContract.View, LoginContract.Presenter> implements LoginContract.View {
    private static final String TAG = "LoginActivity";
    @BindView(R.id.tv_test)
    TextView tv;
    @BindView(R.id.pv)
    CircleProgressView progressView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    protected void initWindow() {
    }

    @Override
    protected LoginContract.Presenter initPresenter() {
        mPresenter = new LoginPresenter();
        mPresenter.attachView(this);
        return mPresenter;
    }

    @Override
    protected void initViews() {

    }


    private List<String> datas;
    private String url = "wt/pkg/pub/online/10012125";
    @Override
    protected void initDatas() {
        datas = new ArrayList<>();
        datas.add("渣渣");
        datas.add("hh");
        datas.add("痛痛痛");
        datas.add("嗷嗷嗷");
        datas.add("滚滚滚");
        datas.add("这些事");
        datas.add("爱是");
        datas.add("是");
        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < datas.size(); i++) {
            buffer.append(datas.get(i)+"、");
        }
        SpannableStringBuilder spannable = new SpannableStringBuilder(buffer.toString());
        tv.setMovementMethod(LinkMovementMethod.getInstance());
        int start = 0;
        int end = 0;
        for (int i = 0; i < datas.size(); i++) {
            if (i != 0) {
                start += datas.get(i - 1).length() + 1;
            }
            end += datas.get(i).length() + 1;
            spannable.setSpan(new ClickableSpan() {
                @Override
                public void onClick(@NonNull View widget) {
                    avoidHintColor(widget);
                }
            }, start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        tv.setText(spannable);

    }

    private void avoidHintColor(View view){
        if(view instanceof TextView)
            ((TextView)view).setHighlightColor(getColor(android.R.color.transparent));
    }

    @Override
    public void loginSus(LoginModel model) {
        disMissDialog();
        ToastUtils.showToast(model.getMessage());
    }

    @Override
    public void showError(int code, String message) {
        disMissDialog();
        ToastUtils.showToast(message);
    }
}

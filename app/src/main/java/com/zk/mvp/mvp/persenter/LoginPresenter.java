package com.zk.mvp.mvp.persenter;

import com.zk.mvp.http.ObserverImp;
import com.zk.mvp.http.RxPresenter;
import com.zk.mvp.http.HttpManager;
import com.zk.mvp.mvp.contract.LoginContract;
import com.zk.mvp.mvp.model.LoginModel;

import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LoginPresenter extends RxPresenter<LoginContract.View> implements LoginContract.Presenter {

    @Override
    public void login(String mobile, String sign, String type, String password, String code) {
        Subscription subscription = HttpManager.getHttpService().login(mobile,sign,type,password,code)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new ObserverImp<LoginModel>() {
                    @Override
                    protected void onErr(int errCode, String str) {
                        mView.showError(errCode,str);
                    }

                    @Override
                    protected void doNext(LoginModel model) {
                        mView.loginSus(model);
                    }
                });
        addSubscribe(subscription);
    }
}

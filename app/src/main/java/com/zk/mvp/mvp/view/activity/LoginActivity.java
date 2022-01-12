package com.zk.mvp.mvp.view.activity;

import com.zk.mvp.R;
import com.zk.mvp.base.BaseActivity;
import com.zk.mvp.mvp.contract.LoginContract;
import com.zk.mvp.mvp.model.LoginModel;
import com.zk.mvp.mvp.persenter.LoginPresenter;

public class LoginActivity extends BaseActivity<LoginContract.View, LoginContract.Presenter> implements LoginContract.View {
    private static final String TAG = "LoginActivity";

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


    @Override
    protected void initData() {
    }

    @Override
    public void loginSus(LoginModel model) {
        disMissDialog();
    }

    @Override
    public void showError(int code, String message) {
        disMissDialog();
    }
}

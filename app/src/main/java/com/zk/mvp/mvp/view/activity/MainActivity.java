package com.zk.mvp.mvp.view.activity;

import com.zk.mvp.R;
import com.zk.mvp.base.BaseActivity;
import com.zk.mvp.base.BaseContract;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";

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
    protected void initData() {
    }
}
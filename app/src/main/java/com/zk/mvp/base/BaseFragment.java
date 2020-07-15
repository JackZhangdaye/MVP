package com.zk.mvp.base;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.zk.mvp.http.RxPresenter;
import com.zk.mvp.mvp.view.dialog.ActingDialog;

import butterknife.ButterKnife;
import butterknife.Unbinder;


public abstract class BaseFragment<V, P extends BaseContract.BasePresenter<V>> extends Fragment {
    protected abstract int getLayoutResId();
    protected abstract void initViews(View view);
    protected abstract void initDatas();
    protected abstract P initPresenter();
    protected ActingDialog actingDialog;
    protected P mPresenter;

    protected FragmentActivity activity;
    private Unbinder unbinder;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = (FragmentActivity) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle state) {
        View view = inflater.inflate(getLayoutResId(), container, false);
        activity = getSupportActivity();
        initViews(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        unbinder = ButterKnife.bind(this, view);
        mPresenter = initPresenter();
        initDatas();
    }

    private long mExitTime;
    public void showDialog(){
        //防止重复点击
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
            actingDialog.show(activity.getSupportFragmentManager(),"ActingDialog");
        }
    }

    public void disMissDialog(){
        if (actingDialog != null){
            actingDialog.dismiss();
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        this.activity = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        if (mPresenter!=null) {
            mPresenter.detachView();
        }
    }

    private FragmentActivity getSupportActivity() {
        return super.getActivity();
    }
}

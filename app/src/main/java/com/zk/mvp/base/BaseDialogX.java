package com.zk.mvp.base;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseDialogX extends DialogFragment {
    private static final String TAG = "BaseDialogX";
    Unbinder unbinder;

    @Override
    public void onStart() {
        super.onStart();
        initOnStart();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutId(), container, false);
        unbinder = ButterKnife.bind(this,view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //设置dialog的样式
        Dialog dialog = new Dialog(getActivity(),setStyle()) {
            @Override
            public void onBackPressed() {
                super.onBackPressed();
            }

        };
        config(dialog);
        return dialog;
    }

    @Override
    public void onStop() {
        super.onStop();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    /**
     * 设置弹窗的样式
     * @param
     */
    protected abstract int setStyle();

    /**
     * 设置布局
     * @return
     */
    public abstract int getLayoutId();

    /**
     * 初始化控件
     * @param view
     */
    protected abstract void initView(View view);

    /**
     * 设置dialog全屏显示
     * @param dialog
     */
    protected abstract void config(Dialog dialog);

    /**
     * 窗口的基本设置，包括宽高、动画、渐变、是否有navigationBar等
     */
    protected abstract void initOnStart();


}

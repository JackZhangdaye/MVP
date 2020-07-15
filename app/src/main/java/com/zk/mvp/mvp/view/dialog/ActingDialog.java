package com.zk.mvp.mvp.view.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.zk.mvp.R;
import com.zk.mvp.base.BaseDialogX;

public class ActingDialog extends BaseDialogX {

    @Override
    public int getLayoutId() {
        return R.layout.dialog_acting;
    }

    @Override
    protected void initView(View view) {

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);
    }

    @Override
    protected int setStyle() {
        return R.style.DialogFragment;
    }


    @Override
    protected void config(Dialog dialog) {
        setCancelable(false);//不会关闭
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }

    @Override
    protected void initOnStart() {
        if (getDialog() == null) {
            return;
        }
        Window window = getDialog().getWindow();
        window.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = lp.height;
        lp.height = lp.width;
        lp.dimAmount = 0;
        window.setAttributes(lp);
    }
    public interface OnDisMissCallBack{
        void disMiss();
    }
    private OnDisMissCallBack callBack;

    public void setCallBack(OnDisMissCallBack callBack) {
        this.callBack = callBack;
    }
}

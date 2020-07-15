package com.zk.mvp.mvp.view.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.zk.mvp.R;
import com.zk.mvp.base.BaseDialogX;

import butterknife.BindView;
import butterknife.OnClick;


public class UpAppDialog extends BaseDialogX {
    @BindView(R.id.iv_file_cancel)
    ImageView ivCancel;
    @BindView(R.id.tv_dialog_down)
    TextView tvDown;
    @BindView(R.id.tv_up_content_dialog)
    TextView tvUpContent;

    private String upContent;

    public void setUpContent(String upContent) {
        this.upContent = upContent;
    }

    @Override
    protected int setStyle() {
        return R.style.DialogFragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.dialog_up_app;
    }

    @Override
    protected void initView(View view) {
        tvUpContent.setText(upContent);
    }

    @Override
    protected void config(Dialog dialog) {
        setCancelable(true);
    }

    @Override
    protected void initOnStart() {
        WindowManager.LayoutParams layoutParams = getDialog().getWindow().getAttributes();
        layoutParams.dimAmount = 0.5f;
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.gravity = Gravity.CENTER;
        getDialog().getWindow().setAttributes(layoutParams);
    }

    @OnClick({R.id.iv_file_cancel, R.id.tv_dialog_down})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_file_cancel:
                dismiss();
                break;
            case R.id.tv_dialog_down:
                if (listener != null) {
                    dismiss();
                    listener.down();
                }
                break;
        }
    }

    public interface OnDownListener {
        void down();
    }

    private OnDownListener listener;

    public void setListener(OnDownListener listener) {
        this.listener = listener;
    }
}

package com.zk.mvp.mvp.view.dialog;

import android.app.Dialog;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.zk.mvp.R;
import com.zk.mvp.base.BaseDialogX;
import com.zk.mvp.utils.ui.CircleProgressView;

import butterknife.BindView;

public class ProgressDialog extends BaseDialogX {
    @BindView(R.id.view_dialog_progress)
    CircleProgressView viewDialogProgress;
    @BindView(R.id.tv_dialog_progress)
    TextView tvDialogProgress;

    public void setProgress(int progress,Long max,Long min){
        if (viewDialogProgress.getMax() == 1L) {
            viewDialogProgress.setMax(max);
        }
        viewDialogProgress.setProgressAndText(min,progress+"%");
    }

    public void onDownFinish(){
        tvDialogProgress.setText("安装");
    }

    @Override
    protected int setStyle() {
        return R.style.DialogFragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.dialog_progress;
    }

    @Override
    protected void initView(View view) {
        tvDialogProgress.setOnClickListener(v -> {
            if (tvDialogProgress.getText().toString().equals("安装")) {
                if (onOpenAPKListener != null) {
                    onOpenAPKListener.open();
                }
            }
        });
    }

    @Override
    protected void config(Dialog dialog) {
        setCancelable(false);
    }

    @Override
    protected void initOnStart() {
        if (getDialog() == null) {
            return;
        }
//        Window window = getDialog().getWindow();
//        if (window != null)
//            window.setGravity(Gravity.CENTER);
//        WindowManager.LayoutParams lp = window.getAttributes();
//        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
//        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
//        lp.dimAmount = 0.5f;
//        window.setAttributes(lp);

        WindowManager.LayoutParams layoutParams = getDialog().getWindow().getAttributes();
        layoutParams.dimAmount = 0.5f;
        layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT;
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        layoutParams.gravity = Gravity.CENTER;
        getDialog().getWindow().setAttributes(layoutParams);
    }

    public interface OnOpenAPKListener{
        void open();
    }

    private OnOpenAPKListener onOpenAPKListener;

    public void setOnOpenAPKListener(OnOpenAPKListener onOpenAPKListener) {
        this.onOpenAPKListener = onOpenAPKListener;
    }
}

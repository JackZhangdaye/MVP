package com.zk.mvp.mvp.view.dialog;

import android.app.Dialog;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.zk.mvp.R;
import com.zk.mvp.base.BaseDialogX;
import com.zk.mvp.utils.ui.CircleProgressView;

public class ProgressDialog extends BaseDialogX {
    private CircleProgressView viewDialogProgress;
    private TextView tvDialogProgress;

    public void setProgress(int progress,Long max,Long min){
        if (viewDialogProgress != null) {
            if (viewDialogProgress.getMax() == 1L) {
                viewDialogProgress.setMax(max);
            }
            viewDialogProgress.setProgressAndText(min,progress+"%");
        }
    }

    public void setTipsText(int textID){
        if (tvDialogProgress != null)
            tvDialogProgress.setText(textID);
    }

    public void openClick(){
        if (tvDialogProgress != null)
            tvDialogProgress.setOnClickListener(v -> {
                if (onOpenAPKListener != null) {
                    onOpenAPKListener.open();
                }
            });
    }

    public void closeClick(){
        if (tvDialogProgress != null)
            tvDialogProgress.setOnClickListener(null);
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
        tvDialogProgress = view.findViewById(R.id.tv_dialog_progress);
        viewDialogProgress = view.findViewById(R.id.view_dialog_progress);
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

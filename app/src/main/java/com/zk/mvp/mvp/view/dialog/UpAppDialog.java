package com.zk.mvp.mvp.view.dialog;

import android.app.Dialog;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.zk.mvp.R;
import com.zk.mvp.base.BaseDialogX;

public class UpAppDialog extends BaseDialogX {
    private TextView tvUpContent;

    public void setUpContent(String upContent) {
        if (tvUpContent != null)
            tvUpContent.setText(upContent);
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
        tvUpContent = view.findViewById(R.id.tvUpContent);

        view.findViewById(R.id.iv_file_cancel).setOnClickListener(v -> dismiss());
        view.findViewById(R.id.tvUpDown).setOnClickListener(v -> {
            if (listener != null) {
                dismiss();
                listener.down();
            }
        });
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

    public interface OnDownListener {
        void down();
    }

    private OnDownListener listener;

    public void setListener(OnDownListener listener) {
        this.listener = listener;
    }
}

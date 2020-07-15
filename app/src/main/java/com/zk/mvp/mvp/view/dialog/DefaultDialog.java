package com.zk.mvp.mvp.view.dialog;

import android.app.Dialog;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.zk.mvp.R;
import com.zk.mvp.base.BaseDialogX;

/**
 * 共用dialog    背景白   圆角8dp
 * 距离左右44dp
 */
public class DefaultDialog extends BaseDialogX {
    TextView tvContent,tvCancel,tvSure,tvTitle;

    //可更改文本内容   不设置就使用默认文本
    private String tipsContent;
    private String cancelText;
    private String nextText;
    private String title;

    public DefaultDialog(Builder builder){
        this.title = builder.title;
        this.tipsContent = builder.tipsContent;
        this.nextText = builder.nextText;
        this.cancelText = builder.cancelText;
        this.listener = builder.onSureClickListener;
    }



    @Override
    protected int setStyle() {
        return R.style.DialogFragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.dialog_default;
    }

    @Override
    protected void initView(View view) {
        tvContent = view.findViewById(R.id.tv_default_content);
        tvCancel = view.findViewById(R.id.tv_default_cancel);
        tvSure = view.findViewById(R.id.tv_default_sure);
        tvTitle = view.findViewById(R.id.tv_default_title);

        tvTitle.setText(title);
        tvContent.setText(tipsContent);
        tvCancel.setText(cancelText);
        tvSure.setText(nextText);

        tvCancel.setOnClickListener(v -> {
            if (listener != null) {
                listener.cancel();
                DefaultDialog.this.dismiss();
            }
        });

        if (TextUtils.isEmpty(cancelText)) {
            tvCancel.setVisibility(View.GONE);
        }
        if (TextUtils.isEmpty(title)) {
            tvTitle.setVisibility(View.GONE);
        }

        tvSure.setOnClickListener(v -> {
            if (listener != null) {
                listener.sure();
                DefaultDialog.this.dismiss();
            }
        });
    }

    @Override
    protected void config(Dialog dialog) {
        setCancelable(false);//点击空白处不可退出
    }

    @Override
    protected void initOnStart() {
        if (getDialog() == null) {
            return;
        }
        Window window = getDialog().getWindow();
        if (window != null)
            window.setGravity(Gravity.CENTER);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = lp.height;
        lp.height = lp.width;
        lp.dimAmount = 0.5f;
        window.setAttributes(lp);
    }

    public interface OnSureClickListener{
        void sure();
        void cancel();
    }

    private OnSureClickListener listener;

    public void setListener(OnSureClickListener listener) {
        this.listener = listener;
    }

    public static class Builder{
        //可更改文本内容   不设置就使用默认文本
        private String tipsContent;
        private String cancelText = "取消";
        private String nextText = "确认";
        private String title;
        //回调
        private OnSureClickListener onSureClickListener;

        public Builder onSureClickListener(OnSureClickListener onSureClickListener){
            this.onSureClickListener = onSureClickListener;
            return this;
        }

        public Builder title(String title) {
            this.title = title;
            return this;
        }

        public Builder cancelText(String cancelText) {
            this.cancelText = cancelText;
            return this;
        }

        public Builder nextText(String nextText) {
            this.nextText = nextText;
            return this;
        }

        public Builder tipsContent(String tipsContent) {
            this.tipsContent = tipsContent;
            return this;
        }

        public DefaultDialog build(){
            return new DefaultDialog(this);
        }
    }
}

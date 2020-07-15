package com.zk.mvp.utils;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zk.mvp.MVPApplication;
import com.zk.mvp.R;

public class ToastUtils extends Toast {
    private static final String TAG = "ToastUtils";
    private static ToastUtils toast;
    private static TextView textView;
    private static ImageView iv;

    public ToastUtils(Context context) {
        super(context);
    }

    /**
     * 隐藏当前Toast
     */
    private static void cancelToast() {
        if (toast != null) {
            toast.cancel();
        }
    }

    /**
     * 不显示图片
     * @param text
     */
    public static void showToast(String text) {
        showToast(text,false,false);
    }

    /**
     * 不显示文字
     * @param isSus
     */
    public static void showToast(boolean isSus) {
        showToast("",true,isSus);
    }

        /**
         * 显示Toast
         * @param text    显示的文本
         * @param isShowIV  是否显示图片
         * @param isSus 图标状态
         */
    public static void showToast(String text,boolean isShowIV,boolean isSus) {
        // 初始化一个新的Toast对象
        initToast(MVPApplication.getContext(), text);
        if (toast == null) {
            return;
        }
        toast.setDuration(Toast.LENGTH_SHORT);

        // 判断图标是否显示
        if (isShowIV) {
            iv.setVisibility(View.VISIBLE);
            if (isSus) {
//                iv.setBackgroundResource(R.drawable.toast_sus);
            } else {
//                iv.setBackgroundResource(R.drawable.toast_err);
            }
            // 动画
            ObjectAnimator.ofFloat(iv, "rotationY", 0, 360).setDuration(1700).start();
        } else {
            iv.setVisibility(View.GONE);
        }
        // 显示Toast
        toast.show();
    }

    /**
     * 初始化Toast
     *
     * @param context 上下文
     * @param text    显示的文本
     */
    private static void initToast(Context context, String text) {
        try {
            cancelToast();
            toast = new ToastUtils(context);
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // 由layout文件创建一个View对象
            View layout = inflater.inflate(R.layout.toast_layout, null);
            textView = layout.findViewById(R.id.toast_text);
            iv = layout.findViewById(R.id.toast_img);
            textView.setText(text);
            if (TextUtils.isEmpty(text)) {
                textView.setVisibility(View.GONE);
            }else {
                textView.setVisibility(View.VISIBLE);
            }
            toast.setView(layout);
            toast.setGravity(Gravity.CENTER, 0, 70);
        } catch (Exception e) {
            e.printStackTrace();
            Log.i(TAG, "initToast: "+e.getMessage());
        }
    }
}

package com.zk.mvp.utils.MeUtils;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zk.mvp.R;

public class ShowToast {
    private static ShowToast instance = null;
    private Context mContext;
    private Toast toast;
    private ImageView iv;

    private ShowToast(){
    }

    public static ShowToast getInstance() {
        if (instance == null) {
            synchronized (ShowToast.class) {
                if (instance == null) {
                    instance = new ShowToast();
                }
            }
        }
        return instance;
    }

    public void init(Context mContext){
        this.mContext = mContext;
    }

    /**
     * 不显示图片
     * @param text
     */
    public void i(String text) {
        showToast(text,false,false);
    }

    /**
     * 不显示文字
     * @param isSus
     */
    public void i(boolean isSus) {
        showToast("",true,isSus);
    }

    /**
     * 显示Toast
     * @param text    显示的文本
     * @param isShowIV  是否显示图片
     * @param isSus 图标状态
     */
    private void showToast(String text,boolean isShowIV,boolean isSus) {
        // 初始化一个新的Toast对象
        if (mContext == null) {
            throw new NullPointerException("mContext == null");
        }
        initToast(text);
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
     * @param text    显示的文本
     */
    private void initToast(String text) {//1078   +2 = 2156
        try {
            cancelToast();
            toast = new Toast(mContext);
            LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            // 由layout文件创建一个View对象
            View layout = inflater.inflate(R.layout.toast_layout, null);
            TextView textView = layout.findViewById(R.id.toast_text);
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
        }
    }

    /**
     * 隐藏当前Toast
     */
    private void cancelToast() {
        if (toast != null) {
            toast.cancel();
        }
    }
}

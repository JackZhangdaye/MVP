package com.zk.mvp.utils.MeUtils;

import android.content.Context;

public class SizeUtils {
    private static SizeUtils instance = null;
    private Context mContext;

    private SizeUtils(){
    }

    public static SizeUtils getInstance() {
        if (instance == null) {
            synchronized (SizeUtils.class) {
                if (instance == null) {
                    instance = new SizeUtils();
                }
            }
        }
        return instance;
    }

    public void init(Context mContext){
        this.mContext = mContext;
    }

    //dp to px
    public int dp2px(int dpValue) {
        float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    //px to dp
    public int px2dip(int pxValue) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    //px to sp
    public int px2sp(float pxValue) {
        final float fontScale = mContext.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    //sp to px
    public int sp2px( float spValue) {
        final float fontScale = mContext.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}

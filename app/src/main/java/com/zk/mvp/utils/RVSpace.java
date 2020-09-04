package com.zk.mvp.utils;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.blankj.utilcode.util.Utils;

/**
 * RecyclerView  边距
 */

public class RVSpace extends RecyclerView.ItemDecoration {
    private int l;
    private int t;
    private int r;
    private int b;

    public RVSpace(int l,int t,int r,int b) {
        this.l = dp2px(l);
        this.t = dp2px(t);
        this.r = dp2px(r);
        this.b = dp2px(b);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {
        outRect.bottom = b;
        outRect.left = l;
        outRect.top = t;
        outRect.right = r;
    }

    private int dp2px(int dpValue) {
        float scale = Utils.getApp().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}

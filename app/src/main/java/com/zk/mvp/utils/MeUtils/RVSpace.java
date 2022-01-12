package com.zk.mvp.utils.MeUtils;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.zk.mvp.utils.MeUtils.SizeUtils;

/**
 * RecyclerView  边距
 */

public class RVSpace extends RecyclerView.ItemDecoration {
    private int l;
    private int t;
    private int r;
    private int b;

    public RVSpace(int l,int t,int r,int b) {
        this.l = SizeUtils.getInstance().dp2px(l);
        this.t = SizeUtils.getInstance().dp2px(t);
        this.r = SizeUtils.getInstance().dp2px(r);
        this.b = SizeUtils.getInstance().dp2px(b);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {
        outRect.bottom = b;
        outRect.left = l;
        outRect.top = t;
        outRect.right = r;
    }
}

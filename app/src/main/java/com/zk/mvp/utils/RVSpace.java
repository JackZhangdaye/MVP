package com.zk.mvp.utils;

import android.graphics.Rect;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * RecyclerView  边距
 */

public class RVSpace extends RecyclerView.ItemDecoration {
    private int space;

    public RVSpace(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {
        outRect.bottom = space;
        outRect.left = space;
        outRect.top = space;
        outRect.right = space;
    }
}

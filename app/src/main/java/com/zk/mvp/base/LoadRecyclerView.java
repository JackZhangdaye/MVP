package com.zk.mvp.base;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

public class LoadRecyclerView extends RecyclerView {
    private boolean isLoad = true;//是否可以加载更多
    private boolean dataIsNull = false;

    public LoadRecyclerView(@NonNull Context context) {
        this(context,null);
    }

    public LoadRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public LoadRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOverScrollMode(View.OVER_SCROLL_NEVER);//去掉拉痕
    }

    //必须要当rv能够滚动的时候才会走此回调
    @Override
    public void onScrolled(int dx, int dy) {
        super.onScrolled(dx, dy);
        if (!canScrollVertically(1) && onRefreshListener != null && isLoad && !dataIsNull) {
            isLoad = false;
            onRefreshListener.footRefresh();
        }
    }

    public interface OnRefreshListener{
        void footRefresh();
    }

    private OnRefreshListener onRefreshListener = null;

    public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
        this.onRefreshListener = onRefreshListener;
    }

    //加载完成或加载失败后通知
    public void loadSuccess() {
        isLoad = true;
    }

    public void dataIsNull(boolean dataIsNull){
        this.dataIsNull = dataIsNull;
    }
}

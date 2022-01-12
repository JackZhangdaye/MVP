package com.zk.mvp.base;

import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public abstract class BaseAdapter extends RecyclerView.Adapter<BaseViewHolder> {
    private static final int ITEM_TYPE_FOOT = 2;//加载更多的布局类型

    protected abstract int getItemSize();
    public abstract BaseViewHolder footerViewHolder(ViewGroup parent, int viewType);//加载更多的布局
    public abstract void loadData(BaseViewHolder holder, int position);//加载更多布局内的控件
    public abstract BaseViewHolder contextViewHolder(ViewGroup parent, int viewType);//列表布局的内容布局
    public abstract void contentData(BaseViewHolder holder, int position);//列表内容内的控件

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE_FOOT){
            return footerViewHolder(parent,viewType);
        }else {
            return contextViewHolder(parent,viewType);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        if (getItemViewType(position) != ITEM_TYPE_FOOT){
            contentData(holder, position);
        }else {
            loadData(holder,position);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return ITEM_TYPE_FOOT;
        }else {
            return 1;
        }
    }

    @Override
    public int getItemCount() {
        return getItemSize() + 1;
    }
}

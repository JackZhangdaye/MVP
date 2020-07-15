package com.zk.mvp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.zk.mvp.R;

import java.util.ArrayList;
import java.util.List;

public class GuideViewPagerAdapter extends RecyclerView.Adapter<GuideViewPagerAdapter.ViewHolder> {
    private Context mContext;
    private List<Integer> imgs;
    public GuideViewPagerAdapter(Context context){
        mContext = context;
        imgs = new ArrayList<>();
    }

    public void setImgs(List<Integer> imgs) {
        this.imgs = imgs;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_guide_viewpager,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(mContext).load(imgs.get(position)).into(holder.iv);
    }

    @Override
    public int getItemCount() {
        return imgs.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView iv;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iv = itemView.findViewById(R.id.item_guide);
        }
    }
}

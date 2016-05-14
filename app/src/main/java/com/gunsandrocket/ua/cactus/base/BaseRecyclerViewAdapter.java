package com.gunsandrocket.ua.cactus.base;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseRecyclerViewAdapter<T, VH extends BaseViewHolder<T>> extends RecyclerView.Adapter<VH>{

    private List<T> items = new ArrayList<>();
    private Context context;

    @LayoutRes
    private int layoutRes;

    public void addItems(List<T> newItems) {
        items.addAll(newItems);
        notifyDataSetChanged();
    }

    public BaseRecyclerViewAdapter(@LayoutRes int layoutRes){
        this.layoutRes = layoutRes;
    }

    protected abstract VH viewHolder(View view);

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        return viewHolder(LayoutInflater.from(parent.getContext()).inflate(layoutRes, parent, false));
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        holder.bindView(items.get(position), context);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        context = recyclerView.getContext();
        super.onAttachedToRecyclerView(recyclerView);
    }


}

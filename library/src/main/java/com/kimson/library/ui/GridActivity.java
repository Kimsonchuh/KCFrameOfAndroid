package com.kimson.library.ui;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kimson.library.R;

/**
 * Created by zhujianheng on 5/26/16.
 */
public abstract class GridActivity<VH extends RecyclerView.ViewHolder, Item, Result> extends RecyclerActivity<VH, Item, Result> {

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, getSpanCount()));
    }

    protected int getSpanCount() {
        return 2;
    }

    @Override
    protected int getRecyclerViewId() {
        return R.id.grid;
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {
        holder.itemView.setOnClickListener(v -> {
            onListItemClick(getRecyclerView(), holder.itemView, position, getItemId(position));
        });
    }

    protected void onListItemClick(RecyclerView rv, View v, int position, long id) {
    }
}
package com.kimson.library.ui;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import com.kimson.library.R;

/**
 * Created by zhujianheng on 2/24/16.
 */
public abstract class ListActivity<VH extends RecyclerView.ViewHolder, Item, Result> extends RecyclerActivity<VH, Item, Result> {


    @Override
    public void onContentChanged() {
        super.onContentChanged();
        setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected int getRecyclerViewId() {
        return R.id.list;
    }

    @Override
    public void onBindViewHolder(final VH holder, final int position) {
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onListItemClick(getRecyclerView(), holder.itemView, position, getItemId(position));
            }
        });
    }

    protected void onListItemClick(RecyclerView rv, View v, int position, long id) {
    }
}

package com.kimson.library.widget;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.kimson.library.bindview.Bind;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhujianheng on 2/24/16.
 */
public abstract class ListAdapter<VH extends ListAdapter.ViewHolder, Data> extends BaseAdapter {
    public static final int NO_POSITION = -1;
    public static final long NO_ID = -1;
    public static final int INVALID_TYPE = -1;

    protected List<Data> mData = new ArrayList<>();

    public abstract VH onCreateViewHolder(ViewGroup parent, int viewType);

    public abstract void onBindViewHolder(VH holder, int position);

    public int getItemCount() {
        return mData.size();
    }

    /**
     * This method calls {@link #onCreateViewHolder(ViewGroup, int)} to create a new
     * {@link ViewHolder} and initializes some private fields to be used by RecyclerView.
     *
     * @see #onCreateViewHolder(ViewGroup, int)
     */
    public final VH createViewHolder(ViewGroup parent, int viewType) {
        final VH holder = onCreateViewHolder(parent, viewType);
        holder.mItemViewType = viewType;
        return holder;
    }

    /**
     * This method internally calls {@link #onBindViewHolder(ViewHolder, int)} to update the
     * {@link ViewHolder} contents with the item at the given position and also sets up some
     * private fields to be used by RecyclerView.
     *
     * @see #onBindViewHolder(ViewHolder, int)
     */
    public final void bindViewHolder(VH holder, int position) {
        holder.mPosition = position;
        if (hasStableIds()) {
            holder.mItemId = getItemId(position);
        }
        onBindViewHolder(holder, position);
    }

    @Override
    public int getCount() {
        return getItemCount();
    }

    @Override
    public Data getItem(int position) {
        return mData.get(position);
    }

    public List<Data> getData() {
        return mData;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        VH holder;
        if (convertView == null) {
            holder = createViewHolder(parent, getItemViewType(position));
            convertView = holder.itemView;
            convertView.setTag(holder);
        } else {
            holder = (VH) convertView.getTag();
        }
        // 绑定数据
        if (holder != null) {
            bindViewHolder(holder, position);
            return holder.itemView;
        }
        return convertView;
    }

    public abstract static class ViewHolder {
        public View itemView;
        int mPosition = NO_POSITION;
        long mItemId = NO_ID;
        int mItemViewType = INVALID_TYPE;

        public ViewHolder(View itemView) {
            if (itemView == null) {
                throw new IllegalArgumentException("itemView may not be null");
            }
            this.itemView = itemView;
            Bind.inject(this, this.itemView);
        }

        public final int getPosition() {
            return mPosition;
        }

        public final long getItemId() {
            return mItemId;
        }

        public final int getItemViewType() {
            return mItemViewType;
        }

    }
}
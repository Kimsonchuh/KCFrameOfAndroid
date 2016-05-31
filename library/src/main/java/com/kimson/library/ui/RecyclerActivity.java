package com.kimson.library.ui;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.kimson.library.R;
import com.kimson.library.widget.PullToRefreshLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhujianheng on 5/26/16.
 */
public abstract class RecyclerActivity<VH extends RecyclerView.ViewHolder, Item, Result> extends LoaderActivity<Result> {

    protected PullToRefreshLayout mPullToRefreshLayout;
    protected RecyclerView mRecyclerView;

    protected PullToRefreshLayout.OnRefreshListener mOnRefreshListener = new PullToRefreshLayout.OnRefreshListener() {
        @Override
        public void onRefresh() {
            RecyclerActivity.this.onRefresh();
        }
    };

    protected RecyclerView.Adapter<VH> mAdapter = new RecyclerView.Adapter<VH>() {
        @Override
        public VH onCreateViewHolder(ViewGroup parent, int viewType) {
            return RecyclerActivity.this.onCreateViewHolder(parent, viewType);
        }

        @Override
        public void onBindViewHolder(VH holder, int position) {
            RecyclerActivity.this.onBindViewHolder(holder, position);
        }

        @Override
        public int getItemCount() {
            return RecyclerActivity.this.getItemCount();
        }

        @Override
        public long getItemId(int position) {
            return RecyclerActivity.this.getItemId(position);
        }

        @Override
        public int getItemViewType(int position) {
            return RecyclerActivity.this.getItemViewType(position);
        }
    };

    private List<Item> mItemsSource;

    @Override
    public void onContentChanged() {
        super.onContentChanged();
        View view = this.getWindow().getDecorView();
        mPullToRefreshLayout = (PullToRefreshLayout) view.findViewById(R.id.pull_to_refresh);
        if (mPullToRefreshLayout != null) {
            mPullToRefreshLayout.setOnRefreshListener(mOnRefreshListener);
        }
        mRecyclerView = (RecyclerView) view.findViewById(this.getRecyclerViewId());
        mRecyclerView.setAdapter(mAdapter);
    }

    protected PullToRefreshLayout getPullToRefreshLayout() {
        return mPullToRefreshLayout;
    }

    public abstract void onRefresh();

    public void onRefreshComplete() {
        if (mPullToRefreshLayout == null) return;
        mPullToRefreshLayout.setRefreshing(false);
    }

    protected abstract int getRecyclerViewId();

    protected RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    protected void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        mRecyclerView.setLayoutManager(layoutManager);
    }

    public RecyclerView.Adapter<VH> getAdapter() {
        return mAdapter;
    }

    public abstract VH onCreateViewHolder(ViewGroup parent, int viewType);

    public abstract void onBindViewHolder(VH holder, int position);

    public int getItemCount() {
        if (mItemsSource != null) {
            return mItemsSource.size();
        }
        return 0;
    }

    public long getItemId(int position) {
        return 0;
    }

    public int getItemViewType(int position) {
        return 0;
    }

    public boolean isEmpty() {
        return mItemsSource == null || mItemsSource.size() == 0;
    }

    public List<Item> getItemsSource() {
        if (mItemsSource == null) {
            mItemsSource = new ArrayList<>();
        }
        return mItemsSource;
    }
}

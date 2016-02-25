package com.kimson.library.ui.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kimson.library.R;
import com.kimson.library.widget.ListAdapter;
import com.kimson.library.widget.PullToRefreshLayout;

import java.util.List;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.header.MaterialHeader;

/**
 * Created by zhujianheng on 2/24/16.
 */
public abstract class GridFragment<VH extends ListAdapter.ViewHolder, Data, Progress, Result> extends LoaderFragment<Progress, Result> {

    private ListAdapter<VH, Data> mAdapter = new ListAdapter<VH, Data>() {
        @Override
        public VH onCreateViewHolder(ViewGroup parent, int viewType) {
            return GridFragment.this.onCreateViewHolder(parent, viewType);
        }

        @Override
        public void onBindViewHolder(VH holder, int position) {
            GridFragment.this.onBindViewHolder(holder, position);
        }

        @Override
        public long getItemId(int position) {
            return GridFragment.this.getItemId(position);
        }

        @Override
        public int getItemViewType(int position) {
            return GridFragment.this.getItemViewType(position);
        }

        @Override
        public int getViewTypeCount() {
            return GridFragment.this.getViewTypeCount();
        }

        @Override
        public int getItemCount() {
            return GridFragment.this.getItemCount();
        }

        @Override
        public Data getItem(int position) {
            return GridFragment.this.getItem(position);
        }

        @Override
        public boolean isEmpty() {
            return GridFragment.this.isEmpty();
        }
    };

    private PullToRefreshLayout mPullToRefreshLayout;
    private GridView mGridView;

    // 上拉自动加载更多
    private boolean mEnableLoadMore = false;
    private boolean mIsLoadingMore = false;
    private int mCurrentScrollState;
    private PullToRefreshLayout.OnLoadMoreListener mOnLoadMoreListener;
    private AbsListView.OnScrollListener mOnListScrollListener;

    protected LinearLayout mLoadMoreView;

    private Handler mHandler = new Handler();

    private Runnable mRequestFocus = new Runnable() {
        public void run() {
            mGridView.focusableViewAvailable(mGridView);
        }
    };

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mPullToRefreshLayout = (PullToRefreshLayout) view.findViewById(R.id.pull_to_refresh);
        if (mPullToRefreshLayout != null) {
            View headerView = createHeaderView();
            if (headerView != null) {
                mPullToRefreshLayout.setHeaderView(headerView);
            }
        }
        mGridView = (GridView) view.findViewById(R.id.grid);
        if (mGridView == null) {
            throw new RuntimeException("Your content must have a GridView whose id attribute is "
                    + "'R.id.grid'");
        }
        // 设置事件
        mGridView.setOnItemClickListener(mOnItemClickListener);
        mGridView.setOnScrollListener(mOnScrollListener);
        mGridView.setAdapter(mAdapter);
        mHandler.post(mRequestFocus);
        mLoadMoreView = (LinearLayout) LayoutInflater.from(getActivity()).inflate(R.layout.simple_list_footer, null);
    }

    public View createHeaderView() {
        MaterialHeader headerView = new MaterialHeader(getActivity());
        headerView.setPtrFrameLayout(getPullToRefreshLayout());
        int[] colors = new int[]{
                Color.parseColor("#008744"),
                Color.parseColor("#0057e7"),
                Color.parseColor("#d62d20"),
                Color.parseColor("#ffa700")};
        headerView.setColorSchemeColors(colors);
        headerView.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
        headerView.setPadding(0, 30, 0, 20);
        return headerView;
    }

    // 默认Adapter处理 BEGIN
    public abstract VH onCreateViewHolder(ViewGroup parent, int viewType);

    public abstract void onBindViewHolder(VH holder, int position);

    public abstract long getItemId(int position);

    public int getItemCount() {
        return getData().size();
    }

    public Data getItem(int position) {
        return getData().get(position);
    }

    public List<Data> getData() {
        return mAdapter.getData();
    }

    public int getItemViewType(int position) {
        return 0;
    }

    public int getViewTypeCount() {
        return 1;
    }

    public boolean isEmpty() {
        return getData().size() == 0;
    }

    public void notifyDataSetChanged() {
        mAdapter.notifyDataSetChanged();
    }
    // 默认Adapter处理 END

    public GridView getGridView() {
        return mGridView;
    }

    public PullToRefreshLayout getPullToRefreshLayout() {
        return mPullToRefreshLayout;
    }

    public android.widget.ListAdapter getListAdapter() {
        return mAdapter;
    }

    public boolean isRefreshing() {
        return mPullToRefreshLayout.isRefreshing();
    }

    public void setRefreshing(boolean refreshing) {
        if (mPullToRefreshLayout != null) {
            mPullToRefreshLayout.setRefreshing(refreshing);
        }
    }

    public void setOnRefreshListener(PullToRefreshLayout.OnRefreshListener onRefreshListener) {
        if (mPullToRefreshLayout != null) {
            mPullToRefreshLayout.setOnRefreshListener(onRefreshListener);
        }
    }

    public void loadMoreComplete() {
        mIsLoadingMore = false;
        if (mLoadMoreView != null) {
//            mLoadMoreView.setVisibility(View.GONE);
        }
    }

    public void allHasLoaded(boolean hasMore) {
        allHasLoaded(hasMore, "正在加载...", "已加载全部");
    }

    public void allHasLoaded(boolean hasMore, String loading_msg, String loaded_msg) {
        if (mLoadMoreView != null) {
            mLoadMoreView.setVisibility(View.VISIBLE);
            mLoadMoreView.findViewById(R.id.load_more_progress_bar).setVisibility(hasMore ? View.VISIBLE : View.GONE);
            TextView progress_text = (TextView) mLoadMoreView.findViewById(R.id.load_more_progress_text);
            progress_text.setText(hasMore ? loading_msg : loaded_msg);
            if (mOnLoadMoreListener != null) {
                mEnableLoadMore = hasMore;
            }
        }
    }

    public void setOnLoadMoreListener(PullToRefreshLayout.OnLoadMoreListener onLoadMoreListener) {
        this.mOnLoadMoreListener = onLoadMoreListener;
        if (mGridView != null) {
            if (mOnLoadMoreListener != null) {
                mEnableLoadMore = true;
                if (mLoadMoreView != null) {
//                    mGridView.addFooterView(mLoadMoreView);
                }
            } else {
                mEnableLoadMore = false;
                if (mLoadMoreView != null) {
                    android.widget.ListAdapter tmpAdapter = mGridView.getAdapter();
                    mGridView.setAdapter(null);
//                    mGridView.removeFooterView(mLoadMoreView);
                    mGridView.setAdapter(tmpAdapter);
                }
            }
        }
    }

    public void setOnListScrollListener(AbsListView.OnScrollListener listener) {
        this.mOnListScrollListener = listener;
    }

    public AbsListView.OnScrollListener getOnListScrollListener() {
        return mOnScrollListener;
    }

    /**
     * @param g
     * @param v
     * @param position
     * @param id
     */
    protected void onGridItemClick(GridView g, View v, int position, long id) {
    }

    private AdapterView.OnItemClickListener mOnItemClickListener = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
            if (v == mLoadMoreView) return;
            onGridItemClick((GridView) parent, v, position, id);
        }
    };

    protected AbsListView.OnScrollListener mOnScrollListener = new AbsListView.OnScrollListener() {

        @Override
        public void onScrollStateChanged(AbsListView view, int scrollState) {
            //bug fix: listview was not clickable after scroll
            if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                view.invalidateViews();
            }

            mCurrentScrollState = scrollState;

            if (mOnListScrollListener != null) {
                mOnListScrollListener.onScrollStateChanged(view, scrollState);
            }
        }

        @Override
        public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
            if (mOnListScrollListener != null) {
                mOnListScrollListener.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
            }
            if (!mEnableLoadMore) {
                return;
            }
            if (mOnLoadMoreListener != null) {
                if (visibleItemCount == totalItemCount) {
//                    mLoadMoreView.setVisibility(View.GONE);
                    return;
                }

                boolean loadMore = firstVisibleItem + visibleItemCount >= totalItemCount;

                if (!mIsLoadingMore && loadMore && mCurrentScrollState != SCROLL_STATE_IDLE) {
//                    mLoadMoreView.setVisibility(View.VISIBLE);
                    mLoadMoreView.findViewById(R.id.load_more_progress_bar).setVisibility(View.VISIBLE);
                    mIsLoadingMore = true;
                    mOnLoadMoreListener.onLoadMore();
                }

            }
        }
    };
}

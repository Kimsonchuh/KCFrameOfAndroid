package com.kimson.kcframeofandroid.ui.base;

import android.app.ProgressDialog;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.kimson.kcframeofandroid.R;
import com.kimson.kcframeofandroid.api.ApiClient;
import com.kimson.kcframeofandroid.api.ApiService;
import com.kimson.kcframeofandroid.ui.util.ErrorUtils;
import com.kimson.library.ui.fragment.RecyclerFragment;
import com.kimson.library.widget.PullToRefreshMode;


/**
 * Created by zhujianheng on 8/5/16.
 */
public abstract class GridFragment<VH extends RecyclerView.ViewHolder, Item, Result> extends com.kimson.library.ui.fragment.GridFragment<VH, Item, Result> implements SwipeRefreshLayout.OnRefreshListener, RecyclerFragment.OnLoadMoreListener {
    public static final String TAG = GridFragment.class.getSimpleName();

    protected final ApiService API = ApiClient.getApiService();

    protected ProgressDialog mProgressDialog;

    private View mLoadingView;
    private View mEmptyView;
    private View mErrorView;

    private boolean isLoadMore = false;
    private boolean mFirstLoaded = false;


    /**
     * Setting the mode of refresh grid
     *
     * @param mode
     */
    public void setMode(PullToRefreshMode mode) {
        if (getPullToRefreshLayout() == null) {
            return;
        }
        if (mode == PullToRefreshMode.PULL_FROM_START) {
            getPullToRefreshLayout().setEnabled(true);
            getPullToRefreshLayout().setOnRefreshListener(this);
            setOnLoadMoreListener(null);
        } else if (mode == PullToRefreshMode.PULL_FROM_END) {
            getPullToRefreshLayout().setEnabled(false);
            getPullToRefreshLayout().setOnRefreshListener(null);
            setOnLoadMoreListener(this);
        } else if (mode == PullToRefreshMode.BOTH) {
            getPullToRefreshLayout().setEnabled(true);
            getPullToRefreshLayout().setOnRefreshListener(this);
            setOnLoadMoreListener(this);
        } else {
            getPullToRefreshLayout().setEnabled(false);
        }
    }


    @Override
    public void onRefresh() {
        isLoadMore = false;
        forceLoad();
    }


    @Override
    public void onLoadStart() {

    }

    @Override
    public Result onLoadInBackground() throws Exception {
        return null;
    }

    @Override
    public void onLoadComplete(Result data) {
    }

    public void retryRefresh() {
        getItemsSource().clear();
        getAdapter().notifyDataSetChanged();
        mFirstLoaded = false;
        onRefresh();
    }

    @Override
    public void onLoadError(Exception e) {
        onRefreshComplete();
        if (!isEmpty()) {
            ErrorUtils.show(getActivity(), e);
        } else {
            ensureView();
            if (mLoadingView != null) {
                mLoadingView.setVisibility(View.INVISIBLE);
            }
            if (mEmptyView != null) {
                mEmptyView.setVisibility(View.INVISIBLE);
            }
            if (mErrorView != null) {
                mErrorView.setVisibility(View.VISIBLE);
                mErrorView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        retryRefresh();
                    }
                });
            }
        }
    }

    //该方法需放在onLoadComplete的最后调用
    @Override
    public void onRefreshComplete() {
        super.onRefreshComplete();
        isLoadMore = false;
        if (!mFirstLoaded) {
            ensureView();
            if (mLoadingView != null) {
                mLoadingView.setVisibility(View.INVISIBLE);
            }
            if (mEmptyView != null) {
                if (getItemsSource().size() == 0) {
                    mEmptyView.setVisibility(View.VISIBLE);
                }
            }
            if (mErrorView != null) {
                mErrorView.setVisibility(View.INVISIBLE);
            }
            getRecyclerView().setVisibility(View.VISIBLE);
        }
        mFirstLoaded = true;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onLoadMore() {
        isLoadMore = true;
        forceLoad();
    }

    public boolean isLoadMore() {
        return isLoadMore;
    }

    public void ensureView() {
        View view = getView();
        if (view == null) {
            return;
        }
        if (mLoadingView == null) {
            mLoadingView = view.findViewById(R.id.loading);
        }
        if (mEmptyView == null) {
            mEmptyView = view.findViewById(R.id.empty);
        }
        if (mErrorView == null) {
            mErrorView = view.findViewById(R.id.error);
        }
    }

    protected void showProgressDialog(int resId) {
        showProgressDialog(getString(resId));
    }

    protected void showProgressDialog(String message) {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(getActivity());
        }
        mProgressDialog.setCancelable(false);
        mProgressDialog.setMessage(message);
        mProgressDialog.show();
    }

    protected void dismissProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }
}

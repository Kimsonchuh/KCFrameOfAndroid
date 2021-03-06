package com.kimson.kcframeofandroid.ui.base;

import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.kimson.kcframeofandroid.R;
import com.kimson.kcframeofandroid.api.ApiClient;
import com.kimson.kcframeofandroid.api.ApiService;
import com.kimson.kcframeofandroid.ui.holder.ViewHolder;
import com.kimson.kcframeofandroid.ui.util.ErrorUtils;
import com.kimson.kcframeofandroid.util.ActivityUtils;
import com.kimson.library.ui.RecyclerActivity;
import com.kimson.library.widget.PullToRefreshMode;

/**
 * Created by zhujianheng on 6/2/16.
 */
public abstract class ListActivity<VH extends ViewHolder, Item, Result> extends com.kimson.library.ui.ListActivity<VH, Item, Result> implements RecyclerActivity.OnLoadMoreListener, SwipeRefreshLayout.OnRefreshListener {
    private final String TAG = this.getClass().getSimpleName();

    protected final ApiService API = ApiClient.getApiService();

    protected ProgressDialog mProgressDialog;

    private View mLoadingView;
    private View mEmptyView;
    private View mErrorView;

    private boolean isLoadMore = false;
    private boolean mFirstLoaded = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityUtils.addActivity(this);
        // 禁止横屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    /**
     * Setting the mode of refresh list
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
        Log.e(TAG, ">>>onRefresh");
        isLoadMore = false;
        forceLoad();

        // 首次加载处理
        if (!mFirstLoaded) {
            ensureView();
            if (mLoadingView != null) {
                mLoadingView.setVisibility(View.VISIBLE);
            }
            if (mEmptyView != null) {
                mEmptyView.setVisibility(View.INVISIBLE);
            }
            if (mErrorView != null) {
                mErrorView.setVisibility(View.INVISIBLE);
            }
            getRecyclerView().setVisibility(View.INVISIBLE);
        }
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
            ErrorUtils.show(this, e);
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
                } else {
                    mEmptyView.setVisibility(View.INVISIBLE);
                }
            }
            if (mErrorView != null) {
                mErrorView.setVisibility(View.INVISIBLE);
            }
            getRecyclerView().setVisibility(View.VISIBLE);
        } else {
            if (getItemsSource().size() == 0) {
                mEmptyView.setVisibility(View.VISIBLE);
            } else {
                mEmptyView.setVisibility(View.INVISIBLE);
            }
        }
        mFirstLoaded = true;
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
        View view = getWindow().getDecorView().getRootView();
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
            mProgressDialog = new ProgressDialog(this);
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

    protected void setLoadingShow(boolean isShow) {
        if (mLoadingView != null) {
            if (mLoadingView instanceof TextView) {
                mLoadingView.setVisibility(isShow ? View.VISIBLE : View.GONE);
            }
        }
    }

    protected void setEmptyShow(boolean isShow) {
        if (mEmptyView != null) {
            if (mEmptyView instanceof TextView) {
                mEmptyView.setVisibility(isShow ? View.VISIBLE : View.GONE);
            }
        }
    }

    protected void setEmptyText(String text) {
        if (mEmptyView != null) {
            if (mEmptyView instanceof TextView) {
                ((TextView) mEmptyView).setText(text);
            }
        }
    }
}

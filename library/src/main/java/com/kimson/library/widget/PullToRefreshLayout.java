package com.kimson.library.widget;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AbsListView;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.PtrUIHandler;
import in.srain.cube.views.ptr.header.MaterialHeader;
import in.srain.cube.views.ptr.indicator.PtrIndicator;


/**
 * Created by zhujianheng on 2/24/16.
 */
public class PullToRefreshLayout extends PtrFrameLayout {

    private View mTarget; // the target of the gesture

    private boolean mRefreshing = false;

    private OnRefreshListener mOnRefreshListener;
    private CheckCanDoRefreshImpl checkCanDoRefresh;

    public PullToRefreshLayout(Context context) {
        this(context, null, 0);
    }

    public PullToRefreshLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PullToRefreshLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        if (isInEditMode()) return;
        // 设置开始事件
        setPtrHandler(ptrHandler);
    }

    private void ensureTarget() {
        // Don't bother getting the parent height if the parent hasn't been laid
        // out yet.
        if (mTarget == null) {
            for (int i = 0; i < getChildCount(); i++) {
                View child = getChildAt(i);
                if (!child.equals(getHeaderView())) {
                    mTarget = child;
                    break;
                }
            }
        }
    }

    /**
     *
     * @param view
     */
    public void setHeaderView(final View view) {
        super.setHeaderView(view);
        // 如果是MaterialHeader，则设置HeaderView的PtrFrameLayout
        if (view instanceof MaterialHeader) {
            ((MaterialHeader) view).setPtrFrameLayout(this);
        }
        if (view instanceof PtrUIHandler) {
            addPtrUIHandler((PtrUIHandler) view);
        } else {
            addPtrUIHandler(new PtrUIHandler() {
                @Override
                public void onUIReset(PtrFrameLayout ptrFrameLayout) {

                }

                @Override
                public void onUIRefreshPrepare(PtrFrameLayout ptrFrameLayout) {

                }

                @Override
                public void onUIRefreshBegin(PtrFrameLayout ptrFrameLayout) {
                    view.setVisibility(VISIBLE);
                }

                @Override
                public void onUIRefreshComplete(PtrFrameLayout ptrFrameLayout) {
                    view.setVisibility(GONE);
                }

                @Override
                public void onUIPositionChange(PtrFrameLayout ptrFrameLayout, boolean b, byte b1, PtrIndicator ptrIndicator) {

                }
            });
        }
    }

    private boolean checkCanDoRefresh() {
        if (checkCanDoRefresh != null) {
            return checkCanDoRefresh.checkCanDoRefresh();
        }
        return canChildScrollUp();
    }

    /**
     * 检查是否可以下拉
     * @return
     */
    public boolean canChildScrollUp() {
        if (mTarget == null) {
            ensureTarget();
        }
        if (mTarget == null) {
            return false;
        }
        if (android.os.Build.VERSION.SDK_INT < 14) {
            if (mTarget instanceof AbsListView) {
                final AbsListView absListView = (AbsListView) mTarget;
                return !(absListView.getChildCount() > 0
                        && (absListView.getFirstVisiblePosition() > 0 || absListView.getChildAt(0)
                        .getTop() < absListView.getPaddingTop()));
            } else {
                return !(ViewCompat.canScrollVertically(mTarget, -1) || mTarget.getScrollY() > 0);
            }
        } else {
            return !(ViewCompat.canScrollVertically(mTarget, -1));
        }
    }

    public boolean isRefreshing() {
        return mRefreshing;
    }

    public void setRefreshing(boolean refreshing) {
        if (refreshing) {
            autoRefresh();
        } else {
            refreshComplete();
        }
    }

    public void setOnRefreshListener(OnRefreshListener listener) {
        this.mOnRefreshListener = listener;
    }

    public void setCheckCanDoRefresh(CheckCanDoRefreshImpl checkCanDoRefresh) {
        this.checkCanDoRefresh = checkCanDoRefresh;
    }

    private PtrHandler ptrHandler = new PtrHandler() {
        @Override
        public boolean checkCanDoRefresh(PtrFrameLayout ptrFrameLayout, View view, View view1) {
            // 子视图可以滚动
            return PullToRefreshLayout.this.checkCanDoRefresh();
        }

        @Override
        public void onRefreshBegin(PtrFrameLayout ptrFrameLayout) {
            mRefreshing = true;
            if (mOnRefreshListener != null) {
                mOnRefreshListener.onRefresh();
            }
        }
    };

    public interface OnRefreshListener {
        void onRefresh();
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }

    public interface CheckCanDoRefreshImpl {
        boolean checkCanDoRefresh();
    }
}

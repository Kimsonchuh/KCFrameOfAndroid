package com.kimson.library.loaderController;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

/**
 * Created by zhujianheng on 2/23/16.
 */
public class AsyncLoader<Progress, Result> extends AsyncTaskLoader<Result> {
    private final String TAG = AsyncLoader.class.getSimpleName();

    private Callback<Progress, Result> mCallback;
    private Exception mError;
    private Result mData;
    private boolean ignoreOnce = false;

    public AsyncLoader(Context context, Callback<Progress, Result> callback) {
        super(context);
        mCallback = callback;
    }

    @Override
    public void deliverResult(final Result data) {
        if (isReset()) {
            // An async query came in while the loader is stopped
            return;
        }
        // 错误发生，执行错误回调
        if (mError != null) {
            if (mCallback != null) mCallback.onLoadFailure(mError);
        }
        this.mData = data;
        super.deliverResult(data);
    }

    @Override
    protected void onStartLoading() {
        if (isIgnoreOnce()) {
            return;
        }
        if (mData != null)
            deliverResult(mData);
        if (takeContentChanged() || mData == null) {
            forceLoad();
            if (mCallback != null) mCallback.onLoadStart();
        }
    }

    @Override
    protected void onStopLoading() {
        // Attempt to cancel the current load task if possible.
        cancelLoad();
    }

    @Override
    protected void onReset() {
        super.onReset();

        // Ensure the loader is stopped
        onStopLoading();

        mData = null;
        mError = null;
    }

    @Override
    public Result loadInBackground() {
        try {
            if (mCallback != null) {
                return mCallback.loadInBackground();
            }
        } catch (final Exception e) {
            // 执行出现错误
            mError = e;
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 判断是否出现了异常！！！
     *
     * @return
     */
    public boolean isFailed() {
        return mError != null;
    }

    public boolean isIgnoreOnce() {
        return ignoreOnce;
    }

    public void setIgnoreOnce(boolean ignoreOnce) {
        this.ignoreOnce = ignoreOnce;
    }

    public interface Callback<Progress, Result> {

        void onLoadStart();

        Result loadInBackground() throws Exception;

        void onLoadComplete(Result data);

        void onLoadFailure(Exception e);

        void onLoadProgressUpdate(Progress... values);
    }
}

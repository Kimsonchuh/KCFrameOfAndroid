package com.kimson.library.loaderController;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;

import java.util.Random;

/**
 * Created by zhujianheng on 2/23/16.
 */
public class BlankLoader<Progress, Result> implements LoaderManager.LoaderCallbacks<Result> {
    private final String TAG = BlankLoader.class.getSimpleName();

    private final int LOADER_ID = new Random().nextInt();

    private Context mContext;
    private LoaderManager mLoaderManager;
    private AsyncLoader.Callback mCallback;

    public void onResume() {
        AsyncLoader<Progress, Result> asyncLoader = (AsyncLoader<Progress, Result>) mLoaderManager.getLoader(LOADER_ID);
        asyncLoader.setIgnoreOnce(false);
    }

    public void onPause() {
        AsyncLoader<Progress, Result> asyncLoader = (AsyncLoader<Progress, Result>) mLoaderManager.getLoader(LOADER_ID);
        asyncLoader.setIgnoreOnce(true);
    }

    public BlankLoader(Context context, LoaderManager loaderManager, AsyncLoader.Callback callback) {
        this.mContext = context;
        this.mLoaderManager = loaderManager;
        this.mCallback = callback;
    }

    public void restartLoader() {
        Log.e(TAG, String.format(">>>restartLoader(%d)", LOADER_ID));
        mLoaderManager.restartLoader(LOADER_ID, null, this);
    }

    /**
     * 销毁 Loader
     */
    public void destroyLoader() {
        Log.e(TAG, String.format(">>>destroyLoader(%d)", LOADER_ID));
        mLoaderManager.destroyLoader(LOADER_ID);
    }

    @Override
    public final Loader<Result> onCreateLoader(int id, Bundle args) {
        return new AsyncLoader<Progress, Result>(mContext, mCallback);
    }

    @Override
    public final void onLoadFinished(Loader<Result> loader, Result data) {
        AsyncLoader<Progress, Result> asyncLoader = ((AsyncLoader<Progress, Result>) loader);
        // 只有在正常执行的情况下才调用
        if (!asyncLoader.isFailed()) {
            mCallback.onLoadComplete(data);
        }
    }

    @Override
    public final void onLoaderReset(Loader<Result> loader) {
    }
}

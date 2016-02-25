package com.kimson.library.ui.fragment;

import com.kimson.library.loaderController.AsyncLoader;
import com.kimson.library.loaderController.BlankLoader;

/**
 * Created by zhujianheng on 2/24/16.
 */
public abstract class LoaderFragment<Progress, Result> extends BaseFragment implements AsyncLoader.Callback<Progress, Result> {
    private BlankLoader mBlankLoader;

    @Override
    public void onResume() {
        super.onResume();
        if (mBlankLoader != null) {
            mBlankLoader.onResume();
        }
    }

    @Override
    public void onPause() {
        if (mBlankLoader != null) {
            mBlankLoader.onPause();
        }
        super.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        destroyLoader();
    }

    protected final void restartLoader() {
        if (mBlankLoader == null) {
            mBlankLoader = new BlankLoader(getActivity(), getActivity().getSupportLoaderManager(), this);
        }
        mBlankLoader.restartLoader();
    }

    protected final void destroyLoader() {
        if (mBlankLoader != null) {
            mBlankLoader.destroyLoader();
        }
    }

    protected final void publishLoadProgress(final Progress... values) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                onLoadProgressUpdate(values);
            }
        });
    }

    @Override
    public void onLoadStart() {

    }

    @Override
    public abstract Result loadInBackground() throws Exception;

    @Override
    public abstract void onLoadComplete(Result data);

    @Override
    public void onLoadFailure(Exception e) {

    }

    @Override
    public void onLoadProgressUpdate(Progress... values) {

    }
}


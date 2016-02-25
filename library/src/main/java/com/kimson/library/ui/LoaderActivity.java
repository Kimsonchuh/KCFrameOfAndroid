package com.kimson.library.ui;

import com.kimson.library.loaderController.AsyncLoader;
import com.kimson.library.loaderController.BlankLoader;

/**
 * Created by zhujianheng on 2/24/16.
 */
public abstract class LoaderActivity<Progress, Result> extends BaseActivity  implements AsyncLoader.Callback<Progress, Result> {
    private BlankLoader mBlankLoader;


    @Override
    protected void onResume() {
        super.onResume();
        if (mBlankLoader != null) {
            mBlankLoader.onResume();
        }
    }

    @Override
    protected void onPause() {
        if (mBlankLoader != null) {
            mBlankLoader.onPause();
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroyLoader();
    }

    protected final void restartLoader() {
        if (mBlankLoader == null) {
            mBlankLoader = new BlankLoader(this, getSupportLoaderManager(), this);
        }
        mBlankLoader.restartLoader();
    }

    protected final void destroyLoader() {
        if (mBlankLoader != null) {
            mBlankLoader.destroyLoader();
        }
    }

    protected final void publishLoadProgress(final Progress... values) {
        runOnUiThread(new Runnable() {
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

package com.kimson.kcframeofandroid.ui.base;

import android.app.ProgressDialog;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import com.kimson.kcframeofandroid.api.ApiClient;
import com.kimson.kcframeofandroid.api.ApiService;
import com.kimson.kcframeofandroid.util.ActivityUtils;


/**
 * Created by zhujianheng on 6/1/16.
 */
public class LoaderActivity<Item> extends com.kimson.library.ui.LoaderActivity<Item> {


    protected final ApiService API = ApiClient.getApiService();

    private final String TAG = this.getClass().getSimpleName();

    protected ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityUtils.addActivity(this);
        // 禁止横屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    @Override
    public void onLoadStart() {

    }

    @Override
    public Item onLoadInBackground() throws Exception {
        return null;
    }

    @Override
    public void onLoadComplete(Item data) {

    }

    @Override
    public void onLoadError(Exception e) {

    }
}

package com.kimson.kcframeofandroid.ui.base;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.ViewGroup;

import com.kimson.kcframeofandroid.ui.holder.ViewHolder;
import com.kimson.kcframeofandroid.util.ActivityUtils;

/**
 * Created by zhujianheng on 5/31/16.
 */
public abstract class ListActivity<VH extends ViewHolder, Item, Result> extends com.kimson.library.ui.ListActivity<VH, Item, Result> {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityUtils.addActivity(this);
        // 禁止横屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }


    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(VH holder, int position) {

    }

    @Override
    public Result onLoadInBackground() throws Exception {
        return null;
    }

    @Override
    public void onLoadComplete(Result data) {

    }

    @Override
    public void onLoadError(Exception e) {

    }

}

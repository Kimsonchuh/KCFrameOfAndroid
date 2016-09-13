package com.kimson.kcframeofandroid.ui.base;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.animation.Animation;

import com.kimson.kcframeofandroid.api.ApiClient;
import com.kimson.kcframeofandroid.api.ApiService;
import com.kimson.kcframeofandroid.util.ActivityUtils;


/**
 * Created by zhujianheng on 6/2/16.
 */
public class StartUpActivity extends com.kimson.library.ui.StartUpActivity{


    protected final ApiService API = ApiClient.getApiService();

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



    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {

    }
}

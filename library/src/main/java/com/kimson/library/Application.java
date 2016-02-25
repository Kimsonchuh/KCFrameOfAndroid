package com.kimson.library;

/**
 * Created by zhujianheng on 2/24/16.
 */
public class Application extends android.app.Application{
    @Override
    public void onCreate() {
        super.onCreate();
        KCFrame.initialize(getApplicationContext());
    }
}

package com.kimson.library.ui;

import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;

import com.kimson.library.bind.Bind;

/**
 * Created by zhujianheng on 2/24/16.
 */
public class BaseActivity extends AppCompatActivity {

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        Bind.inject(this);
    }
}

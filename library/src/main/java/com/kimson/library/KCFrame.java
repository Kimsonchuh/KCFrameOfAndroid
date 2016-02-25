package com.kimson.library;

import android.content.Context;

import com.kimson.library.util.PreferenceUtils;

/**
 * Created by zhujianheng on 2/16/16.
 */
public class KCFrame {
    public static final boolean DEBUG = true;

    public static void initialize(Context context) {
        // KCFrame
        PreferenceUtils.initialize(context);
    }

}

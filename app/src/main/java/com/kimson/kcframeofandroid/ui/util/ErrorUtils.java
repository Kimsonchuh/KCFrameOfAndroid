package com.kimson.kcframeofandroid.ui.util;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.kimson.kcframeofandroid.R;
import com.kimson.library.util.TextUtils;

import retrofit2.Response;

/**
 * Created by zhujianheng on 6/2/16.
 */
public class ErrorUtils {
    public static boolean isSuccessful(Context context, Response response) {
        if (response == null) {
            return false;
        }
        if (!response.isSuccessful()) {
            show(context, context.getString(R.string.http_status_code_error, response.code()));
            return false;
        }
        return true;
    }

    /**
     * @param context
     * @param exception
     */
    public static void show(Context context, Exception exception) {
        if (exception == null) {
            return;
        }
        String messages = exception.getLocalizedMessage();
        Log.e("HAHA", messages);
        show(context, messages);
    }

    /**
     * @param context
     * @param t
     */
    public static void show(Context context, Throwable t) {
        if (t == null) {
            return;
        }
        t.printStackTrace();
        String messages = t.getLocalizedMessage();
        show(context, messages);
    }

    /**
     * NetWork Connect Error
     * @param context
     * @param messages
     */
    public static void show(Context context, String messages) {
        if (context == null) {
            return;
        }
        if (TextUtils.isEmpty(messages)) {
            return;
        }
        Toast.makeText(context, messages, Toast.LENGTH_SHORT).show();
    }

}

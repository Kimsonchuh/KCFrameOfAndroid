package com.kimson.kcframeofandroid.ui.util;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.kimson.kcframeofandroid.R;


/**
 * Created by zhujianheng on 7/18/16.
 */
public class ViewUtils {

    /**
     *
     * @param view
     * @param error
     * @param indicator_input_error
     */
    public static void setError(TextView view, CharSequence error, int indicator_input_error) {
        if (error == null) {
            view.setError(null, null);
        } else {
            if (indicator_input_error == 0) {
                view.setError(error);
            } else {
                Drawable dr = view.getContext().getResources().getDrawable(indicator_input_error);
                dr.setBounds(0, 0, dr.getIntrinsicWidth(), dr.getIntrinsicHeight());
                view.setError(error, dr);
            }
        }
    }

    /**
     *
     * @param view
     * @param error
     */
    public static void setError(TextView view, CharSequence error) {
        setError(view, error, 0);
    }

    public static void setImageURI(Context context, String url, ImageView imageView) {
        Glide
                .with(context)
                .load(url)
                .asBitmap()
                .placeholder(R.mipmap.ic_launcher)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(new GlideBitmapImageViewTarget(imageView));

    }

    public static void setAvatarURI(Context context, String url, ImageView imageView) {
        Glide
                .with(context)
                .load(url)
                .asBitmap()
                .placeholder(R.mipmap.ic_launcher)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(new GlideBitmapImageViewTarget(imageView));
    }

    /**
     * 设置焦点
     *
     * @param view
     */
    public static void focus(View view) {
        if (view == null) {
            return;
        }
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        view.requestFocus();
    }


}

package com.kimson.kcframeofandroid.ui.util;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

public class GlideBitmapImageViewTarget extends BitmapImageViewTarget {

    public GlideBitmapImageViewTarget(ImageView view) {
        super(view);
    }

    @Override
    public void onResourceReady(Bitmap bitmap, GlideAnimation anim) {
        if (bitmap != null && view.getScaleType() != ScaleType.FIT_XY) {
            view.setScaleType(ScaleType.FIT_XY);
        }
        super.onResourceReady(bitmap, anim);
    }

    @Override
    protected void setResource(Bitmap resource) {
        super.setResource(resource);
    }

    @Override
    public void onLoadFailed(Exception e, Drawable errorDrawable) {
        if (errorDrawable != null && view != null && view.getScaleType() != ScaleType.CENTER_CROP) {
            view.setScaleType(ScaleType.CENTER_CROP);
        }
        super.onLoadFailed(e, errorDrawable);
    }

    @Override
    public void onLoadStarted(Drawable placeholder) {
        if (placeholder != null && placeholder != null && view != null && view.getScaleType() != ScaleType.CENTER_CROP) {
            view.setScaleType(ScaleType.CENTER_CROP);
        }
        super.onLoadStarted(placeholder);
    }

    @Override
    public void onLoadCleared(Drawable placeholder) {
        if (placeholder != null && placeholder != null && view != null && view.getScaleType() != ScaleType.CENTER_CROP) {
            view.setScaleType(ScaleType.CENTER_CROP);
        }
        super.onLoadCleared(placeholder);
    }

}

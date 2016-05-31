package com.kimson.kcframeofandroid.ui.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.kimson.library.bind.Bind;
import com.kimson.library.util.ViewUtils;

/**
 * Created by zhujianheng on 5/30/16.
 */
public class ViewHolder extends RecyclerView.ViewHolder{
    public ViewHolder(View itemView) {
        super(itemView);
        //这里做bindId的初始化
        Bind.inject(this, this.itemView);
    }
}

package com.kimson.kcframeofandroid.model;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * Created by zhujianheng on 5/30/16.
 */
public class Model {

    public Model() {
        super();
    }

    protected int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String toJSONString() {
        return JSON.toJSONString(this, new SerializerFeature[]{SerializerFeature.SkipTransientField});
    }

    public static <T extends Model> T parseObject(String json, Class<T> clazz) {
        return TextUtils.isEmpty(json) ? null : JSON.parseObject(json, clazz);
    }
}
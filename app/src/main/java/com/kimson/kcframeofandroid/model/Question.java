package com.kimson.kcframeofandroid.model;

/**
 * Created by zhujianheng on 5/30/16.
 */
public class Question extends Model {
    private String title;
    private String content;
    private String tag;
    private boolean showing = false;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public boolean isShowing() {
        return showing;
    }

    public void setShowing(boolean showing) {
        this.showing = showing;
    }

    public static Question parseObject(String json) {
        return Model.parseObject(json, Question.class);
    }

}

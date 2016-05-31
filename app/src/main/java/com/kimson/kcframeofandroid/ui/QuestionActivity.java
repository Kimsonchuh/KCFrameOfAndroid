package com.kimson.kcframeofandroid.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.fastjson.JSON;
import com.kimson.kcframeofandroid.R;
import com.kimson.kcframeofandroid.logic.QuestionLogic;
import com.kimson.kcframeofandroid.model.Question;
import com.kimson.kcframeofandroid.model.Result;
import com.kimson.kcframeofandroid.ui.holder.QuestionViewHolder;
import com.kimson.library.ui.ListActivity;
import com.kimson.library.widget.DividerItemDecoration;

import java.util.ArrayList;

/**
 * Created by zhujianheng on 5/26/16.
 */
public class QuestionActivity extends ListActivity<QuestionViewHolder, Question, Result<ArrayList<Question>>> {
    public static final String TAG = QuestionActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        getRecyclerView().addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        onRefresh();
    }

    @Override
    public void onRefresh() {
        restartLoader();
    }

    @Override
    public QuestionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.e(TAG, ">>>onCreateViewHolder");
        View currentView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_question_list_item, parent, false);
        return new QuestionViewHolder(currentView);
    }

    @Override
    public void onBindViewHolder(QuestionViewHolder holder, int position) {
        Log.e(TAG, ">>>onBindViewHolder:" + position);
        holder.bind(getItemsSource().get(position));
    }

    @Override
    public void onLoadStart() {
    }

    @Override
    public Result<ArrayList<Question>> onLoadInBackground() throws Exception {
        Log.e(TAG, ">>>onLoadInBackground");
        return QuestionLogic.getQuestionList();
    }

    @Override
    public void onLoadComplete(Result<ArrayList<Question>> data) {
        Log.e(TAG, ">>>onLoadComplete");
        Log.e(TAG, JSON.toJSONString(data));
        if (data != null) {
            if (data.isSuccess()) {
                getItemsSource().addAll(data.getData());
            }
        }
        onRefreshComplete();
        getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onLoadError(Exception e) {
        Log.e(TAG, ">>>onLoadError");
    }
}

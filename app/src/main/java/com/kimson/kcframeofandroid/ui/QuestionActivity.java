package com.kimson.kcframeofandroid.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kimson.kcframeofandroid.R;
import com.kimson.kcframeofandroid.model.Question;
import com.kimson.kcframeofandroid.model.Result;
import com.kimson.kcframeofandroid.ui.base.ListActivity;
import com.kimson.kcframeofandroid.ui.holder.QuestionViewHolder;
import com.kimson.library.widget.DividerItemDecoration;
import com.kimson.library.widget.PullToRefreshMode;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;

/**
 * Created by zhujianheng on 5/26/16.
 */
public class QuestionActivity extends ListActivity<QuestionViewHolder, Question, Result<ArrayList<Question>>> {
    public static final String TAG = QuestionActivity.class.getSimpleName();

    private int currentPage = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        getRecyclerView().addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST));
        getPullToRefreshLayout().setProgressViewOffset(false, 0, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));
        setMode(PullToRefreshMode.BOTH);
        initLoader();
    }

    @Override
    public QuestionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View currentView = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_question_list_item, parent, false);
        return new QuestionViewHolder(currentView);
    }

    @Override
    public void onBindViewHolder(QuestionViewHolder holder, int position) {
        holder.bind(getItemsSource().get(position));
    }


    @Override
    public Result<ArrayList<Question>> onLoadInBackground() throws Exception {
        Log.e(TAG, ">>>onLoadInBackground");
        int page = 0;
        if (isLoadMore()) {
            page = currentPage + 1;
        } else {
            page = 1;
        }
        Call<Result<ArrayList<Question>>> resultCall = API.questions("0755", page);
        Result<ArrayList<Question>> result = null;
        try {
            result = resultCall.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public void onLoadComplete(Result<ArrayList<Question>> data) {
        Log.e(TAG, ">>>onLoadComplete");
        if (data != null) {
            if (data.isSuccess()) {
                if (!isLoadMore()) {
                    getItemsSource().clear();
                    currentPage = 1;
                } else {
                    currentPage++;
                }
                getItemsSource().addAll(data.getData());
            }
        }
        getAdapter().notifyDataSetChanged();
        onRefreshComplete();
    }

    @Override
    public void onLoadError(Exception e) {
        Log.e(TAG, ">>>onLoadError");
    }
}

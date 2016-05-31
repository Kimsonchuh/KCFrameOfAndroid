package com.kimson.kcframeofandroid.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.kimson.kcframeofandroid.R;
import com.kimson.library.bind.ViewById;
import com.kimson.library.ui.BaseActivity;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    @ViewById(R.id.coorLayout)
    private Button mCoorLayout;
    @ViewById(R.id.collapLayout)
    private Button mCollapLayout;
    @ViewById(R.id.recyclerview)
    private Button mRecyclerView;
    @ViewById(R.id.retrofit2)
    private Button mRetrofit2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mCoorLayout.setOnClickListener(this);
        mCollapLayout.setOnClickListener(this);
        mRecyclerView.setOnClickListener(this);
        mRetrofit2.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.coorLayout:

                break;
            case R.id.collapLayout:
                break;
            case R.id.recyclerview:
                break;
            case R.id.retrofit2:
                Intent intent = new Intent();
                intent.setClass(this, QuestionActivity.class);
                startActivity(intent);
                break;
        }
    }
}

package com.kimson.kcframeofandroid.ui.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.kimson.kcframeofandroid.R;
import com.kimson.kcframeofandroid.model.Question;
import com.kimson.library.bind.ViewById;

/**
 * Created by zhujianheng on 5/30/16.
 */
public class QuestionViewHolder extends ViewHolder {

    @ViewById(R.id.question)
    private TextView question;
    @ViewById(R.id.question_content)
    private TextView question_content;

    public QuestionViewHolder(View itemView) {
        super(itemView);
    }


    public void bind(Question data) {
        question.setText(data.getTitle());
        question_content.setText(data.getContent());
    }
}

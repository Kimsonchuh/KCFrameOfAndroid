package com.kimson.kcframeofandroid.logic;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.kimson.kcframeofandroid.api.ApiClient;
import com.kimson.kcframeofandroid.model.Question;
import com.kimson.kcframeofandroid.model.Result;

import java.io.IOException;
import java.util.ArrayList;

import retrofit2.Call;


/**
 * Created by zhujianheng on 5/30/16.
 */
public class QuestionLogic {

    /**
     * 获取问题
     * @return
     */
    public static Result<ArrayList<Question>> getQuestionList() {
        Call<Result<ArrayList<Question>>> resultCall = ApiClient.getApiService().questions("0755");
        Result<ArrayList<Question>> result = null;
        try {
            result = resultCall.execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }


}

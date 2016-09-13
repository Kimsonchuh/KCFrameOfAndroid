package com.kimson.kcframeofandroid.api;

import com.kimson.kcframeofandroid.model.Question;
import com.kimson.kcframeofandroid.model.Result;
import static com.kimson.kcframeofandroid.api.URLs.QUESTIONS;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


/**
 * Created by zhujianheng on 5/30/16.
 */
public interface ApiService {

    @GET(QUESTIONS)
    Call<Result<ArrayList<Question>>> questions(@Query("city") String city,
                                                @Query("page") int page);

}

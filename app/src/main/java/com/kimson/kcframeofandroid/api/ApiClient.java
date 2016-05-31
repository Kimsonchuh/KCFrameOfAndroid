package com.kimson.kcframeofandroid.api;


import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by zhujianheng on 5/30/16.
 */
public class ApiClient {
    private static ApiService apiService;

    private static OkHttpClient client = new OkHttpClient();

    static {


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URLs.API_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);
    }

    public static ApiService getApiService() {
        return apiService;
    }
}
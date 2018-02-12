package com.abdulrahmanjavanrd.newsappchallenge.api;

import com.abdulrahmanjavanrd.newsappchallenge.model.ArticlesWithRetroFit;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface ApiClient {


    @GET("search?q=/tags")
    Call<ArticlesWithRetroFit> getArticles(@QueryMap HashMap<String,String> filter);
}

package com.abdulrahmanjavanrd.newsappchallenge.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.abdulrahmanjavanrd.newsappchallenge.R;
import com.abdulrahmanjavanrd.newsappchallenge.adapter.RecyclerAdapter;
import com.abdulrahmanjavanrd.newsappchallenge.api.ApiClient;
import com.abdulrahmanjavanrd.newsappchallenge.model.ArticlesWithRetroFit;

import java.util.HashMap;
import java.util.Timer;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

/**
 * Created by nfs05 on 29/01/2018.
 */

public class PopularNewsFragment extends Fragment{

    // recycler view
    RecyclerView mRecyclerView ;
    // recyclerAdapter
    RecyclerAdapter mAdapter ;
    // layoutManager ..
    RecyclerView.LayoutManager mLayoutManager ;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Timber.v("Create new fragment");
        View v = inflater.inflate(R.layout.popular_articles_fragment,container,false);
        // init Recycler view and set LinearLayout for it .
        mRecyclerView = v.findViewById(R.id.recycler);
        mLayoutManager= new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        // initializing myRecyclerAdapter ..
        mAdapter = new RecyclerAdapter();
        setupRetrofit();

        return v;

    }

    private void setupRetrofit(){
        // create retrofit builder ...
        Retrofit.Builder builder = new Retrofit.Builder().baseUrl("https://content.guardianapis.com/")
                .addConverterFactory(GsonConverterFactory.create());
        // instance of RetroFit ..
        Retrofit retrofit = builder.build();
        // Create instance of ApiClient interface ..
        ApiClient apiClient = retrofit.create(ApiClient.class);
        // Now create callBack , to response and request ..
        HashMap<String,String> filter = new HashMap<>();
        filter.put("api-key","test");
        filter.put("order-by", "newest");
        filter.put("show-fields","thumbnail");
        filter.put("page-size","50");
        filter.put("show-blocks","body");
        filter.put("show-tags","contributor");
        Call<ArticlesWithRetroFit> mCall = apiClient.getArticles(filter);

        mCall.enqueue(new Callback<ArticlesWithRetroFit>() {
            @Override
            public void onResponse(Call<ArticlesWithRetroFit> call, Response<ArticlesWithRetroFit> response) {
                Timber.v("Response is okay "+response.toString());
            }

            @Override
            public void onFailure(Call<ArticlesWithRetroFit> call, Throwable t) {

                    Timber.e("Error:"+t.getMessage());
            }
        });

    }
}

package com.abdulrahmanjavanrd.newsappchallenge.loader;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

import com.abdulrahmanjavanrd.newsappchallenge.model.News;
import com.abdulrahmanjavanrd.newsappchallenge.utilties.QueryUtils;

import java.util.List;

import timber.log.Timber;

/**
 * Created by nfs05 on 29/01/2018.
 */

public class NewsLoader extends AsyncTaskLoader<List<News>> {

    private String uri ;
    public NewsLoader(Context context,String uri) {
        super(context);
        new QueryUtils(context);
        this.uri = uri ;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    public List<News> loadInBackground() {
        Timber.v("Start fetch data ");
        if (uri == null){
            return null ;
        }
        return QueryUtils.fetchNewsData(uri);
    }
}

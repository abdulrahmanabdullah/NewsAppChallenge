package com.abdulrahmanjavanrd.newsappchallenge.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.abdulrahmanjavanrd.newsappchallenge.R;
import com.abdulrahmanjavanrd.newsappchallenge.adapter.NewsAdapter;
import com.abdulrahmanjavanrd.newsappchallenge.loader.NewsLoader;
import com.abdulrahmanjavanrd.newsappchallenge.model.News;

import java.util.List;

import timber.log.Timber;

/**
 * @author Abdurlahman.A on 29/01/2018.
 */

public class LastNewsFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<News>>{


    ListView listView ;
    NewsAdapter adapter ;
    private static final String REQUEST_URL = "https://content.guardianapis.com/search?q=/tags";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.last_news_layout,container,false);
         listView  = v.findViewById(R.id.last_news_list_view);
         getLoaderManager().initLoader(0,null,this);
        // TODO:: create new Model for last news..
        Timber.v("Here start Fragment LastNews");
        return v;
    }
    @Override
    public Loader<List<News>> onCreateLoader(int id, Bundle args) {
        Uri uri = Uri.parse(REQUEST_URL);
        Uri.Builder uriBuilder = uri.buildUpon();
        uriBuilder.appendQueryParameter("order-by", "newest");
        uriBuilder.appendQueryParameter("page-size","50");
        uriBuilder.appendQueryParameter("show-fields", "thumbnail");
        uriBuilder.appendQueryParameter("show-blocks", "body");
        uriBuilder.appendQueryParameter("show-tags", "contributor");
        uriBuilder.appendQueryParameter("api-key", "test");
        return new NewsLoader(getContext(), uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(Loader<List<News>> loader, List<News> data) {
        if (data !=null && !data.isEmpty()){
            updateUI(data);
        }else{
            Toast.makeText(getContext(),"Here data is = "+data,Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onLoaderReset(Loader<List<News>> loader) {
         loader.cancelLoad();
    }

    private void updateUI(final List<News> news){
        adapter = new NewsAdapter(getActivity(),news);
        listView.setAdapter(adapter);
        // create onClick item Listener .
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                News currentObject = news.get(position);
                Uri uri = Uri.parse(currentObject.getArticleUri());
                Intent mIntent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(mIntent);
            }
        });
    }
}

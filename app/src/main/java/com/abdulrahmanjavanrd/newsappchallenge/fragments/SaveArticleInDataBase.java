package com.abdulrahmanjavanrd.newsappchallenge.fragments;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.abdulrahmanjavanrd.newsappchallenge.R;
import com.abdulrahmanjavanrd.newsappchallenge.adapter.NewsCursorAdapter;
import com.abdulrahmanjavanrd.newsappchallenge.data.ArticlesContract;
import com.abdulrahmanjavanrd.newsappchallenge.data.NewsContract;

import timber.log.Timber;

/**
 * @author Abdulrahman.A  on 31/01/2018.
 */

public class SaveArticleInDataBase extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

NewsCursorAdapter adapter ;
ListView listView ;
Cursor cursor  ;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View v = inflater.inflate(R.layout.save_news_activity,container,false);
       adapter = new NewsCursorAdapter(getContext(),null); // cursor = null
        listView = v.findViewById(R.id.list_view_items);
        listView.setAdapter(adapter);
        Timber.v("Here start Fragment SaveArticle");
        getLoaderManager().initLoader(0,null,this);
        return v;
    }



    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Timber.v("Start create Loader on Fragment SaveArticle");
        Uri uri = ArticlesContract.NewArticlesEntery.CONTENT_URI;
        String[] projection = {
                ArticlesContract.NewArticlesEntery._ID,
                ArticlesContract.NewArticlesEntery.COLUMN_NEWS_TITLE,
                ArticlesContract.NewArticlesEntery.COLUMN_NEWS_DESC,
                ArticlesContract.NewArticlesEntery.COLUMN_NEWS_IMAGE,
                ArticlesContract.NewArticlesEntery.COLUMN_NEWS_DATE,
                ArticlesContract.NewArticlesEntery.COLUMN_NEWS_AUTHOR,
                ArticlesContract.NewArticlesEntery.COLUMN_NEWS_SECTION,
                ArticlesContract.NewArticlesEntery.COLUMN_NEWS_URL
        };
        String selection = NewsContract.NewsEntry._ID ;
        return  new CursorLoader(getContext(),uri,projection,selection,null,"date DESC") ;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Timber.v("Finished Loader on Fragment SaveArticle");
            adapter.swapCursor(data);
            updateUi(data);
            Timber.v("DataBase = "+data.getColumnCount());
//            updateUi(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }


    private void updateUi( final Cursor data ){
        adapter = new NewsCursorAdapter(getContext(),data);
       listView.setAdapter(adapter);
       listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               Toast.makeText(getActivity(),"No internet connection",Toast.LENGTH_SHORT).show();
                }
       });
    }

}

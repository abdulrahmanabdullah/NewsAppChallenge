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
import android.widget.ListView;

import com.abdulrahmanjavanrd.newsappchallenge.R;
import com.abdulrahmanjavanrd.newsappchallenge.adapter.FavoriteArticleAdapter;
import com.abdulrahmanjavanrd.newsappchallenge.data.ArticlesContract;

import timber.log.Timber;

/**
 * @author Abdulrahman.A on 29/01/2018.
 */

public class FavoriteNewsFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

ListView listView ;
FavoriteArticleAdapter adapter ;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       View v = inflater.inflate(R.layout.favorite_articles_fragment,container,false);
        adapter = new FavoriteArticleAdapter(getContext(),null);
       listView = v.findViewById(R.id.list_view_items);
       listView.setAdapter(adapter);
       getLoaderManager().initLoader(0,null,this).forceLoad();
        return v;
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Timber.v("Start Favorite fragment , onCreateLoader work ");
        String[] projection = {
                ArticlesContract.FavoriteArticleEntery._ID,ArticlesContract.FavoriteArticleEntery.COLUMN_NEWS_IMAGE,
                ArticlesContract.FavoriteArticleEntery.COLUMN_NEWS_TITLE,
                ArticlesContract.FavoriteArticleEntery.COLUMN_NEWS_DESC,
                ArticlesContract.FavoriteArticleEntery.COLUMN_NEWS_SECTION,
                ArticlesContract.FavoriteArticleEntery.COLUMN_NEWS_AUTHOR,
                ArticlesContract.FavoriteArticleEntery.COLUMN_NEWS_DATE

        };
        Uri uri = ArticlesContract.FavoriteArticleEntery.CONTENT_URI;
        String selection = ArticlesContract.FavoriteArticleEntery._ID ;
        return new CursorLoader(getContext(),uri,projection,selection,null,"date DESC ");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        Timber.v("Start Favorite fragment , OnLoadFinished  work ");
        adapter.swapCursor(data);
        listView.setAdapter(adapter);
    }


    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        adapter.swapCursor(null);
    }
}

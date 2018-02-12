package com.abdulrahmanjavanrd.newsappchallenge.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.abdulrahmanjavanrd.newsappchallenge.R;
import com.abdulrahmanjavanrd.newsappchallenge.data.ArticlesContract;
import com.squareup.picasso.Picasso;

/**
 * Created by nfs05 on 02/02/2018.
 */

public class FavoriteArticleAdapter extends CursorAdapter {

    Context context ;
    public FavoriteArticleAdapter(Context context, Cursor c) {
        super(context, c, 0);
        this.context = context ;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.main_card,parent,false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ImageView img = view.findViewById(R.id.article_image);
        TextView articleTitle = view.findViewById(R.id.txv_article_Title);
        TextView articleSummary = view.findViewById(R.id.txv_article_summary);
        TextView articleSection = view.findViewById(R.id.txv_article_section);
        TextView articleDate = view.findViewById(R.id.txv_article_date);
        TextView articleAuthor = view.findViewById(R.id.txv_article_publisher);
        Button imageButton = view.findViewById(R.id.btn_favorite_icon);


        int imageIndex = cursor.getColumnIndex(ArticlesContract.FavoriteArticleEntery.COLUMN_NEWS_IMAGE);
        int titleIndex = cursor.getColumnIndex(ArticlesContract.FavoriteArticleEntery.COLUMN_NEWS_TITLE);
        int summaryIndex = cursor.getColumnIndex(ArticlesContract.FavoriteArticleEntery.COLUMN_NEWS_DESC);
        int sectionIndex = cursor.getColumnIndex(ArticlesContract.FavoriteArticleEntery.COLUMN_NEWS_SECTION);
        int authorIndex = cursor.getColumnIndex(ArticlesContract.FavoriteArticleEntery.COLUMN_NEWS_AUTHOR);
        int dateIndex = cursor.getColumnIndex(ArticlesContract.FavoriteArticleEntery.COLUMN_NEWS_DATE);


        String image = cursor.getString(imageIndex);
        Picasso.with(context).load(image).into(img);
        String title = cursor.getString(titleIndex);
        articleTitle.setText(title);
        String summary = cursor.getString(summaryIndex);
        articleSummary.setText(divideSummaryToQuarter(summary));
        String section = cursor.getString(sectionIndex);
        articleSection.setText(section);
        String author = cursor.getString(authorIndex);
        articleAuthor.setText(author);
        String date = cursor.getString(dateIndex);
        articleDate.setText(date);
        imageButton.setVisibility(View.GONE);
    }
    // To divide summary text  ..
    private String divideSummaryToQuarter(String summary) {
        String quarterText;
        int length = (int) Math.floor(summary.length()); // to get only int numbers .
        boolean MinVal = length >= 0 && length <= 6000;  // from  0 to 6000 char
        boolean MaxVal = length >= 6000 && length <= 11000; // from  6000 to 110000 char .
        boolean OverMaxVal = length >= 11000; // if length of String > 1100, Like 12000 ... etc  .
        if (MinVal) {
            quarterText = summary.substring(0, length / 16);
        } else if (MaxVal) {
            quarterText = summary.substring(0, length / 32);
        } else if (OverMaxVal) {
            quarterText = summary.substring(0, length / 64);
        }
        // if any text summary  under 6000 char .
        else {
            quarterText = summary.substring(0, length / 2);
        }
        return quarterText + " ...";
    }
}

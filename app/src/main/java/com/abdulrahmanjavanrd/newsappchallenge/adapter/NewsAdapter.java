package com.abdulrahmanjavanrd.newsappchallenge.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.abdulrahmanjavanrd.newsappchallenge.R;
import com.abdulrahmanjavanrd.newsappchallenge.data.ArticlesContract;
import com.abdulrahmanjavanrd.newsappchallenge.model.News;
import com.squareup.picasso.Picasso;

import java.util.List;

import timber.log.Timber;

import static com.abdulrahmanjavanrd.newsappchallenge.data.ArticlesContract.FavoriteArticleEntery.COLUMN_NEWS_AUTHOR;
import static com.abdulrahmanjavanrd.newsappchallenge.data.ArticlesContract.FavoriteArticleEntery.COLUMN_NEWS_DATE;
import static com.abdulrahmanjavanrd.newsappchallenge.data.ArticlesContract.FavoriteArticleEntery.COLUMN_NEWS_DESC;
import static com.abdulrahmanjavanrd.newsappchallenge.data.ArticlesContract.FavoriteArticleEntery.COLUMN_NEWS_IMAGE;
import static com.abdulrahmanjavanrd.newsappchallenge.data.ArticlesContract.FavoriteArticleEntery.COLUMN_NEWS_SECTION;
import static com.abdulrahmanjavanrd.newsappchallenge.data.ArticlesContract.FavoriteArticleEntery.COLUMN_NEWS_TITLE;

/**
 * @author  Abdulrahman.A  on 28/01/2018.
 */

public class NewsAdapter extends BaseAdapter {
    private Context  context ;
    private List<News> newsList ;
    public NewsAdapter(Context context, List<News> newsList) {
        this.context = context;
        this.newsList = newsList;
    }

    @Override
    public int getCount() {
        return newsList.size();
    }

    @Override
    public Object getItem(int position) {
        return newsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final MyViewHolder holder  ;
        if (convertView == null ){
            convertView = LayoutInflater.from(context).inflate(R.layout.main_card,null);
            holder = new MyViewHolder() ;
            holder.articleImage = convertView.findViewById(R.id.article_image);
            holder.articleTitle = convertView.findViewById(R.id.txv_article_Title);
            holder.articleSummary = convertView.findViewById(R.id.txv_article_summary);
            holder.articleSection = convertView.findViewById(R.id.txv_article_section);
            holder.articlePublisher = convertView.findViewById(R.id.txv_article_publisher);
            holder.articleDate = convertView.findViewById(R.id.txv_article_date);
            holder.favoriteEvent = convertView.findViewById(R.id.btn_favorite_icon);
            convertView.setTag(holder);
        }else
        {
            holder = (MyViewHolder) convertView.getTag();
        }

        // create current object of News .
        final News currentNews = newsList.get(position);
        // set image For article
        Picasso.with(context).load(currentNews.getArticleImage()).into(holder.articleImage);
        // set Article title .
        holder.articleTitle.setText(currentNews.getArticleTitle());
        // set Article summary ...
        String simpleSummary = divideSummaryToQuarter(currentNews.getArticleSummary());
        holder.articleSummary.setText(simpleSummary);
        // set Section name ..
        holder.articleSection.setText(currentNews.getArticleSection());
        // set publisher name .
        holder.articlePublisher.setText(currentNews.getArticlePublisher());
        // set Date ..
        holder.articleDate.setText(currentNews.getArticleDate());
        holder.favoriteEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Timber.d("It's clicked .. ");
                // change image to favorite image
                String str = holder.favoriteEvent.getText().toString();
                if (hasFavoriteArticle(str,position)){
                    holder.favoriteEvent.setBackgroundResource(R.drawable.favorite_icon);
                    Toast.makeText(context,context.getString(R.string.add_favorite_article),Toast.LENGTH_SHORT).show();
                    holder.favoriteEvent.setText(context.getString(R.string.favorite_choice_no));
                }else{
                    holder.favoriteEvent.setBackgroundResource(R.drawable.unfavored_icon);
                    //TODO::remove  the current position form Favorite database .
                    holder.favoriteEvent.setText(context.getString(R.string.favorite_choice_yes));
                    Toast.makeText(context,context.getString(R.string.remove_favorite_article),Toast.LENGTH_SHORT).show();
                }
            }
        });
        return convertView;
    }

    private boolean hasFavoriteArticle(String checkStatus,int position ){
        Uri  uri = ArticlesContract.FavoriteArticleEntery.CONTENT_URI;
        News mCurrentNews =  newsList.get(position);
        if (checkStatus.equals(context.getString(R.string.favorite_choice_yes)) && !TextUtils.isEmpty(checkStatus)){
            //TODO:: save current values to database
            ContentValues values = new ContentValues();
            values.put(COLUMN_NEWS_IMAGE,mCurrentNews.getArticleImage());
            values.put(COLUMN_NEWS_TITLE,mCurrentNews.getArticleTitle());
            values.put(COLUMN_NEWS_DESC,mCurrentNews.getArticleSummary());
            values.put(COLUMN_NEWS_SECTION,mCurrentNews.getArticleSection());
            values.put(COLUMN_NEWS_DATE,mCurrentNews.getArticleDate());
            values.put(COLUMN_NEWS_AUTHOR,mCurrentNews.getArticlePublisher());
            // insert data to favorite table ..
            context.getContentResolver().insert(uri,values);
            return true;
        }else{
            //TODO:: remove current values from database .. Done
            String selection = COLUMN_NEWS_TITLE+" = ?";
            String[] selectionArgs = {mCurrentNews.getArticleTitle()};
            context.getContentResolver().delete(uri,selection,selectionArgs);
//            Toast.makeText(context," deleted .",Toast.LENGTH_SHORT).show();
            return false;
        }
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
        // if any text summary under 6000 char .
        else {
            quarterText = summary.substring(0, length / 2);
        }
        return quarterText + " ...";
    }
    // ViewHolder Class ..
    public static class MyViewHolder {
        ImageView articleImage ;
        TextView articleTitle;
        TextView articleSummary;
        TextView articleSection;
        TextView articlePublisher;
        TextView articleDate;
        Button favoriteEvent ;
    }
}

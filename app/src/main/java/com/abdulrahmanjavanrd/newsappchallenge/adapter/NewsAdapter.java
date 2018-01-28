package com.abdulrahmanjavanrd.newsappchallenge.adapter;

import android.content.Context;
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
import com.abdulrahmanjavanrd.newsappchallenge.model.News;
import com.squareup.picasso.Picasso;

import java.util.List;

import timber.log.Timber;

/**
 * @author  Abdulrahman.A && Abdullah on 28/01/2018.
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
            holder.favoriteEvent = convertView.findViewById(R.id.imageButton);
            convertView.setTag(holder);
        }else
        {
            holder = (MyViewHolder) convertView.getTag();
        }

        // create current object of News .
        News currentNews = newsList.get(position);
        // set image For article
        Picasso.with(context).load(currentNews.getArticleImage()).into(holder.articleImage);
        // set Article title .
        holder.articleTitle.setText(currentNews.getArticleTitle());
        // set Article summary ...
        holder.articleSummary.setText(currentNews.getArticleSummary());
        // set Section name ..
        holder.articleSection.setText(currentNews.getArticleSection());
        // set publisher name .
        holder.articlePublisher.setText(currentNews.getArticlePublisher());
        // set Date ..
        holder.articleDate.setText(currentNews.getArticleDate());
        // TODO:: create func for favorite image ..
        holder.favoriteEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Timber.d("It's clicked .. ");
                // change image to favorite image
                String str = holder.favoriteEvent.getText().toString();
                if (hasFavoriteArticle(str)){
                    holder.favoriteEvent.setBackgroundResource(R.drawable.favorite_icon);
                    holder.favoriteEvent.setText(context.getString(R.string.favorite_choice_no));
                    Toast.makeText(context,context.getString(R.string.add_favorite_article),Toast.LENGTH_SHORT).show();
                }else{
                    holder.favoriteEvent.setBackgroundResource(R.drawable.unfavored_icon);
                    holder.favoriteEvent.setText(context.getString(R.string.favorite_choice_yes));
                    Toast.makeText(context,context.getString(R.string.remove_favorite_article),Toast.LENGTH_SHORT).show();
                }
            }
        });
        return convertView;
    }

    private boolean hasFavoriteArticle(String checkStatus ){
        if (checkStatus.equalsIgnoreCase(context.getString(R.string.favorite_choice_yes))){
            //TODO:: save current values to database .
//            context.getContentResolver().insert();
            return true;
        }else{
            //TODO:: remove current values from database ..
//            context.getContentResolver().delete();
            return false;
        }
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
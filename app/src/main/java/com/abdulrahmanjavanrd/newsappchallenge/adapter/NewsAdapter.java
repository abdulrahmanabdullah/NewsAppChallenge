package com.abdulrahmanjavanrd.newsappchallenge.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.abdulrahmanjavanrd.newsappchallenge.R;
import com.abdulrahmanjavanrd.newsappchallenge.model.News;
import com.squareup.picasso.Picasso;

import java.util.List;

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
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder holder  ;
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
        return convertView;
    }


    // ViewHolder Class ..
    public static class MyViewHolder {
        ImageView articleImage ;
        TextView articleTitle;
        TextView articleSummary;
        TextView articleSection;
        TextView articlePublisher;
        TextView articleDate;
        ImageButton favoriteEvent ;
    }
}

package com.abdulrahmanjavanrd.newsappchallenge.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.abdulrahmanjavanrd.newsappchallenge.R;
import com.abdulrahmanjavanrd.newsappchallenge.model.ArticlesWithRetroFit.TagsToGetAuthor;
import com.abdulrahmanjavanrd.newsappchallenge.model.ArticlesWithRetroFit.ResultsTag;
import com.abdulrahmanjavanrd.newsappchallenge.model.ArticlesWithRetroFit.FieldsTagToGetThumbnail;
import com.abdulrahmanjavanrd.newsappchallenge.model.ArticlesWithRetroFit.BodyTagsToGetShortText;
import com.abdulrahmanjavanrd.newsappchallenge.model.ArticlesWithRetroFit.BlocksTagToGetBodyTag;
import com.abdulrahmanjavanrd.newsappchallenge.utilties.DivideSummaryText;
import com.squareup.picasso.Picasso;

import java.util.List;

import timber.log.Timber;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyHolder> {

    Context mContext ;
    List<ResultsTag> mResultsTags ;

    public RecyclerAdapter(Context paramContext, List<ResultsTag> paramResultsTags) {
        mContext = paramContext;
        mResultsTags = paramResultsTags;
    }

    @Override
    public MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new  MyHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.main_card,parent,false));
    }

    @Override
    public void onBindViewHolder(MyHolder holder, int position) {

        ResultsTag currentObject = mResultsTags.get(position);
        //to get image, create new object of Fields class ..
        FieldsTagToGetThumbnail thumbnail = currentObject.getFields();
        //TODO:: save thumbnail in mImageView .
        Picasso.with(mContext).load(thumbnail.getThumbnail()).into(holder.mImageView);
        //set title .. from ResultTag ..
        holder.txvArticleTitle.setText(currentObject.getWebTitle());
        // set section
        holder.txvArticleSection.setText(currentObject.getSectionId());
        // set Date ..
        holder.txvArticleDate.setText(currentObject.getWebPublicationDate());
        // set summary text .. from BodyTagsToGet class ..
        //  inside this class method return list of BodyTagsToGetShortText,
        // Pass position for this class to create new object of BodyTagsToGetShortText,This Class contain summaryText .
        //TODO::Fix this error .IndexOutOfBoundException... DONE,solve it.
         BlocksTagToGetBodyTag blocksTag = currentObject.getBlocksTag();
         List<BodyTagsToGetShortText> bodyTags = blocksTag.getBodyTags();
         String summaryText = bodyTags.get(0).getBodyTextSummary();
        holder.txvArticleSummary.setText(DivideSummaryText.dividTextToQuarter(summaryText));
//        //set publisher ...
//        // publisher exist inside TagToGetAuthor class
        //TODO::Fix this error .IndexOutOfBoundException... Done, the error because some time the list is empty .
        List<TagsToGetAuthor> tagsToGetAuthors =currentObject.getTagsList();// result = position which mean List of Tags
        if (tagsToGetAuthors.size() == 0){
           Timber.e("it's null list man .");
           holder.txvArticlePublisher.setText("Unknown writer .");
        }else{
           String firstName = tagsToGetAuthors.get(0).getFirstName();
           String lastName = tagsToGetAuthors.get(0).getLastName();
           holder.txvArticlePublisher.setText(firstName+" "+lastName);
        }

        //set date ..
        holder.txvArticleDate.setText(currentObject.getWebPublicationDate());
        Timber.v("Date = "+currentObject.getWebPublicationDate());
        // hide favorite icon.
        holder.favoriteButton.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount()
    {
        return mResultsTags.size() ;
    }

    public static class MyHolder extends RecyclerView.ViewHolder{
        ImageView mImageView ;
        TextView txvArticleTitle ;
        TextView txvArticleSummary;
        TextView txvArticleSection;
        TextView txvArticleDate ;
        TextView txvArticlePublisher ;
        Button favoriteButton ;
        public MyHolder(View view) {
            super(view);
            mImageView = view.findViewById(R.id.article_image);
            txvArticleTitle = view.findViewById(R.id.txv_article_Title);
            txvArticleSummary = view.findViewById(R.id.txv_article_summary);
            txvArticleSection = view.findViewById(R.id.txv_article_section);
            txvArticleDate = view.findViewById(R.id.txv_article_date);
            txvArticlePublisher = view.findViewById(R.id.txv_article_publisher);
            favoriteButton = view.findViewById(R.id.btn_favorite_icon);
        }
    }
}

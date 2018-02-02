package com.abdulrahmanjavanrd.newsappchallenge.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by nfs05 on 02/02/2018.
 */

public final class ArticlesContract {

    // Base Content
    public static final String CONTENT_AUTHORITY ="com.abdulrahmanjavanrd.newsappchallenge";

    // Base Uri ..
    public static final Uri BASE_URI = Uri.parse("content://"+CONTENT_AUTHORITY);

    // path for new articles
    public static final String NEW_ARTICLE_PATH = "newArticle";
    // path for favorite articles ..
    public static final String FAVORITE_ARTICLE_PATH = "favorite";


    public static final class NewArticlesEntery implements BaseColumns{
        public static final Uri CONTENT_URI =Uri.withAppendedPath(BASE_URI,NEW_ARTICLE_PATH);
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE+CONTENT_URI+"/"+NEW_ARTICLE_PATH;
        public static final String CONTENT_ITEM = ContentResolver.ANY_CURSOR_ITEM_TYPE+CONTENT_URI+"/"+NEW_ARTICLE_PATH;

       // Table Name ..
        public static final String TABLE_NAME = "newArticles";
        // Id column
        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_NEWS_TITLE = "title";
        public final static String COLUMN_NEWS_SECTION = "section";
        public final static String COLUMN_NEWS_DATE = "date";
        public final static String COLUMN_NEWS_IMAGE = "image";
        public final static String COLUMN_NEWS_AUTHOR = "author";
        public final static String COLUMN_NEWS_URL = "url";
        public final static String COLUMN_NEWS_DESC = "desc";

        public static Uri buildGenreUri(long id){
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

    }

    public static final class FavoriteArticleEntery implements BaseColumns{

        public static final Uri CONTENT_URI =Uri.withAppendedPath(BASE_URI,FAVORITE_ARTICLE_PATH);
        public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE+CONTENT_URI+"/"+FAVORITE_ARTICLE_PATH;
        public static final String CONTENT_ITEM = ContentResolver.ANY_CURSOR_ITEM_TYPE+CONTENT_URI+"/"+FAVORITE_ARTICLE_PATH;

        // Table Name ..
        public static final String TABLE_NAME = "favoriteArticles";
        // field column
        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_NEWS_TITLE = "title";
        public final static String COLUMN_NEWS_SECTION = "section";
        public final static String COLUMN_NEWS_DATE = "date";
        public final static String COLUMN_NEWS_IMAGE = "image";
        public final static String COLUMN_NEWS_AUTHOR = "author";
        public final static String COLUMN_NEWS_URL = "url";
        public final static String COLUMN_NEWS_DESC = "desc";

        public static Uri buildGenreUri(long id){
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }
}

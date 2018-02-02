package com.abdulrahmanjavanrd.newsappchallenge.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import timber.log.Timber;

/**
 * @author  Abdulrahman.A on 02/02/2018.
 */

public class ArticlesDBHelper extends SQLiteOpenHelper {


    // DataBase Name
    public static final String DATA_BASE_NAME = "allArticles";
    public static final int DATA_BASE_V = 1 ;

    public ArticlesDBHelper(Context context) {
        super(context, DATA_BASE_NAME, null, DATA_BASE_V);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
       //Create New Articles table ..
        addNewArticle(db);
        Timber.v("Successful create New Articles ");
        // Create Favorite Articles table
        addFavoriteArticle(db);
        Timber.v("Successful create Favorite Articles ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop TABLE IF EXISTS "+ ArticlesContract.NewArticlesEntery.TABLE_NAME);
        db.execSQL("Drop TABLE IF EXISTS "+ ArticlesContract.FavoriteArticleEntery.TABLE_NAME);
    }


    private void addNewArticle(SQLiteDatabase db){
        String SQL = "CREATE TABLE  "+ ArticlesContract.NewArticlesEntery.TABLE_NAME+"( "
               +ArticlesContract.NewArticlesEntery._ID+ " INTEGER PRIMARY KEY AUTOINCREMENT , "
                +ArticlesContract.NewArticlesEntery.COLUMN_NEWS_TITLE+ " TEXT , "
                +ArticlesContract.NewArticlesEntery.COLUMN_NEWS_DESC+ " TEXT ,"
                +ArticlesContract.NewArticlesEntery.COLUMN_NEWS_IMAGE + " TEXT , "
                +ArticlesContract.NewArticlesEntery.COLUMN_NEWS_SECTION+ " TEXT , "
                +ArticlesContract.NewArticlesEntery.COLUMN_NEWS_AUTHOR+" TEXT ,"
                +ArticlesContract.NewArticlesEntery.COLUMN_NEWS_DATE+" TEXT ,"
                +ArticlesContract.NewArticlesEntery.COLUMN_NEWS_URL+" TEXT "
                + " ) ; ";
               db.execSQL(SQL);

    }
    private void addFavoriteArticle(SQLiteDatabase db){
        String SQL = "CREATE TABLE  "+ ArticlesContract.FavoriteArticleEntery.TABLE_NAME+"( "
                +ArticlesContract.FavoriteArticleEntery._ID+ " INTEGER PRIMARY KEY AUTOINCREMENT , "
                +ArticlesContract.FavoriteArticleEntery.COLUMN_NEWS_TITLE+ " TEXT , "
                +ArticlesContract.FavoriteArticleEntery.COLUMN_NEWS_DESC+ " TEXT ,"
                +ArticlesContract.FavoriteArticleEntery.COLUMN_NEWS_IMAGE + " TEXT , "
                +ArticlesContract.FavoriteArticleEntery.COLUMN_NEWS_SECTION+ " TEXT , "
                +ArticlesContract.FavoriteArticleEntery.COLUMN_NEWS_AUTHOR+" TEXT ,"
                +ArticlesContract.FavoriteArticleEntery.COLUMN_NEWS_DATE+" TEXT ,"
                +ArticlesContract.FavoriteArticleEntery.COLUMN_NEWS_URL+" TEXT "
                + " ) ; ";
        db.execSQL(SQL);
    }
}

package com.abdulrahmanjavanrd.newsappchallenge.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.abdulrahmanjavanrd.newsappchallenge.data.NewsContract.NewsEntry;

import timber.log.Timber;

import static com.abdulrahmanjavanrd.newsappchallenge.data.NewsContract.NewsEntry.LATEST_NEWS_TABLE_NAME;
import static com.abdulrahmanjavanrd.newsappchallenge.data.NewsContract.NewsFavorite.FAVORITE_TABLE_NAME;

/**
 * Created by Abdullah Aldobaie (akdPro) on 1/28/18 at 11:08 PM.
 *
 */

public class NewsDbHelper extends SQLiteOpenHelper

{

	private static final String DATABASE_NAME = "news.db";

	private static final int DATABASE_VERSION = 1;

	public NewsDbHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}



	@Override
	public void onCreate(SQLiteDatabase db) {
		// Create Last News Table ..
        addNewNews(db);
		addFavoriteTable(db);
		Timber.v("create Two tables ");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		//TODO: delete old table and create a new one
		db.execSQL("DROP TABLE IF EXISTS " + LATEST_NEWS_TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + FAVORITE_TABLE_NAME);
	}

	private void addFavoriteTable(SQLiteDatabase db){
	  String SQL = "Create Table "+ FAVORITE_TABLE_NAME+"( "+
			  NewsContract.NewsFavorite._ID+" INTEGER PRIMARY KEY AUTOINCREMENT "
			    + ", " + NewsContract.NewsFavorite.COLUMN_NEWS_TITLE + " Text "
				+ ", " + NewsContract.NewsFavorite.COLUMN_NEWS_SECTION + " TEXT"
				+ ", " + NewsContract.NewsFavorite.COLUMN_NEWS_DESC + " TEXT "
				+ ", " +  NewsContract.NewsFavorite.COLUMN_NEWS_AUTHOR + " TEXT"
				+ ", " + NewsContract.NewsFavorite.COLUMN_NEWS_IMAGE + " TEXT"
				+ ", " + NewsContract.NewsFavorite.COLUMN_NEWS_DATE + " TEXT"
				+ ", " + NewsContract.NewsFavorite.COLUMN_NEWS_URL + " TEXT" + ");";
		db.execSQL(SQL);
	}
	private void addNewNews(SQLiteDatabase db){
		String SQL_CREATE_NEWS_TABLE = "CREATE TABLE " + LATEST_NEWS_TABLE_NAME + " ("
				+ NewsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT"
				+ ", " + NewsEntry.COLUMN_NEWS_TITLE + " TEXT NOT NULL"
				+ ", " + NewsEntry.COLUMN_NEWS_SECTION + " TEXT"
				+ ", " + NewsEntry.COLUMN_NEWS_DATE + " INTEGER NOT NULL"
				+ ", " + NewsEntry.COLUMN_NEWS_AUTHOR + " TEXT"
				+ ", " + NewsEntry.COLUMN_NEWS_IMAGE + " TEXT"
				+ ", " + NewsEntry.COLUMN_NEWS_URL + " TEXT NOT NULL"
				+ ", " + NewsEntry.COLUMN_NEWS_DESC + " TEXT"
				+ ");";
		db.execSQL(SQL_CREATE_NEWS_TABLE);
	}
}

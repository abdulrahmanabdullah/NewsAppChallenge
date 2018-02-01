package com.abdulrahmanjavanrd.newsappchallenge.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

import com.abdulrahmanjavanrd.newsappchallenge.data.NewsContract.NewsEntry;

import timber.log.Timber;

import static com.abdulrahmanjavanrd.newsappchallenge.data.NewsContract.NewsEntry.FAVORITE_NEWS_TABLE_NAME;
import static com.abdulrahmanjavanrd.newsappchallenge.data.NewsContract.NewsEntry.LATEST_NEWS_TABLE_NAME;

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
		// This Main Table ..
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
		 // SQL - for favorite table ...
		 String SQL_CREATE_FAVORITE_TABLE = "CREATE TABLE " + FAVORITE_NEWS_TABLE_NAME + " ("
				+ NewsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT"
				+ ", " + NewsEntry.COLUMN_NEWS_TITLE + " TEXT NOT NULL"
				+ ", " + NewsEntry.COLUMN_NEWS_SECTION + " TEXT"
				+ ", " + NewsEntry.COLUMN_NEWS_DATE + " TEXT "
				+ ", " + NewsEntry.COLUMN_NEWS_AUTHOR + " TEXT"
				+ ", " + NewsEntry.COLUMN_NEWS_IMAGE + " TEXT"
				+ ", " + NewsEntry.COLUMN_NEWS_URL + " TEXT"
				+ ", " + NewsEntry.COLUMN_NEWS_DESC + " TEXT"
				+ ");";
		// Create Last News Table ..
		db.execSQL(SQL_CREATE_NEWS_TABLE);
		Timber.v("create last News Table");
		// Create Favorite news Table ..
//		db.execSQL(SQL_CREATE_FAVORITE_TABLE);
		Timber.v("create favorite News Table");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		//TODO: delete old table and create a new one
		db.execSQL("DROP TABLE IF EXISTS " + LATEST_NEWS_TABLE_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + FAVORITE_NEWS_TABLE_NAME);
	}
}

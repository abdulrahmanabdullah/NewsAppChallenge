package com.abdulrahmanjavanrd.newsappchallenge.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.abdulrahmanjavanrd.newsappchallenge.data.NewsContract.NewsEntry;

/**
 * Created by Abdullah Aldobaie (akdPro) on 1/28/18 at 11:08 PM.
 *
 */

public class NewsDbHelper extends SQLiteOpenHelper

{

	public static final String LOG_TAG = NewsDbHelper.class.getSimpleName();

	private static final String DATABASE_NAME = "news.db";

	private static final int DATABASE_VERSION = 1;

	public NewsDbHelper(Context context)
	{
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db)
	{
		String SQL_CREATE_NEWS_TABLE = "CREATE TABLE " + NewsEntry.LATEST_NEWS_TABLE_NAME + " ("
			 + NewsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT"
			 + ", " + NewsEntry.COLUMN_NEWS_TITLE + " TEXT NOT NULL"
			 + ", " + NewsEntry.COLUMN_NEWS_SECTION + " TEXT"
			 + ", " + NewsEntry.COLUMN_NEWS_DATE + " TEXT NOT NULL"
			 + ", " + NewsEntry.COLUMN_NEWS_AUTHOR + " TEXT"
			 + ", " + NewsEntry.COLUMN_NEWS_IMAGE + " TEXT"
			 + ", " + NewsEntry.COLUMN_NEWS_URL + " TEXT NOT NULL"
			 + ", " + NewsEntry.COLUMN_NEWS_DESC + " TEXT NOT NULL"
			 + ");";

		db.execSQL(SQL_CREATE_NEWS_TABLE);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
	{
		//TODO: delete old table and create a new one
	}
}

package com.abdulrahmanjavanrd.newsappchallenge.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.abdulrahmanjavanrd.newsappchallenge.data.NewsContract.NewsEntry;
/**
 * Created by Abdullah Aldobaie (akdPro) on 1/29/18 at 12:01 AM.
 *
 */

public class NewsProvider extends ContentProvider
{
	public static final String LOG_TAG = NewsProvider.class.getSimpleName();
	private static final int LATEST_NEWS = 100;
	private static final int NEWS_ID = 101;
	private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
	
	static
	{
		sUriMatcher.addURI(NewsContract.CONTENT_AUTHORITY, NewsContract.PATH_NEWS, LATEST_NEWS);
		sUriMatcher.addURI(NewsContract.CONTENT_AUTHORITY, NewsContract.PATH_NEWS + "/#", NEWS_ID);
	}
	
	private NewsDbHelper mDbHelper;
	
	@Override
	public boolean onCreate()
	{
		mDbHelper = new NewsDbHelper(getContext());
		return true;
	}
	
	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder)
	{
		SQLiteDatabase database = mDbHelper.getReadableDatabase();
		
		Cursor cursor;
		
		int match = sUriMatcher.match(uri);
		switch (match)
		{
			case LATEST_NEWS:
				cursor = database.query(NewsEntry.LATEST_NEWS_TABLE_NAME, projection, selection, selectionArgs,
					 null, null, sortOrder);
				break;
			case NEWS_ID:
				selection = NewsEntry._ID + "=?";
				selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
				
				cursor = database.query(NewsEntry.LATEST_NEWS_TABLE_NAME, projection, selection, selectionArgs,
					 null, null, sortOrder);
				break;
			default:
				throw new IllegalArgumentException("Cannot query unknown URI " + uri);
		}
		
		cursor.setNotificationUri(getContext().getContentResolver(), uri);
		
		return cursor;
	}
	
	@Override
	public Uri insert(@NonNull Uri uri, ContentValues contentValues)
	{
		final int match = sUriMatcher.match(uri);
		switch (match)
		{
			case LATEST_NEWS:
				return insertNews(uri, contentValues);
			default:
				throw new IllegalArgumentException("Insertion is not supported for " + uri);
		}
	}
	
	private Uri insertNews(Uri uri, ContentValues values)
	{
		String title = values.getAsString(NewsEntry.COLUMN_NEWS_TITLE);
		if (title == null)
		{
			throw new IllegalArgumentException("News requires a title");
		}
		
		String date = values.getAsString(NewsEntry.COLUMN_NEWS_DATE);
		if (date == null)
		{
			throw new IllegalArgumentException("News requires a date");
		}
		
		String url = values.getAsString(NewsEntry.COLUMN_NEWS_URL);
		if (url == null)
		{
			throw new IllegalArgumentException("News requires a url");
		}
		
		SQLiteDatabase database = mDbHelper.getWritableDatabase();
		
		long id = database.insert(NewsEntry.LATEST_NEWS_TABLE_NAME, null, values);

		if (id == -1)
		{
			Log.e(LOG_TAG, "Failed to insert row for " + uri);
			return null;
		}
		
		getContext().getContentResolver().notifyChange(uri, null);
		
		return ContentUris.withAppendedId(uri, id);
	}
	
	@Override
	public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs)
	{
		return -1;
	}
	
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs)
	{
		SQLiteDatabase database = mDbHelper.getWritableDatabase();
		
		int rowsDeleted;
		
		final int match = sUriMatcher.match(uri);
		switch (match)
		{
			case LATEST_NEWS:
				rowsDeleted = database.delete(NewsEntry.LATEST_NEWS_TABLE_NAME, selection, selectionArgs);
				break;
			case NEWS_ID:
				selection = NewsEntry._ID + "=?";
				selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
				rowsDeleted = database.delete(NewsEntry.LATEST_NEWS_TABLE_NAME, selection, selectionArgs);
				break;
			default:
				throw new IllegalArgumentException("Deletion is not supported for " + uri);
		}

		if (rowsDeleted != 0)
		{
			getContext().getContentResolver().notifyChange(uri, null);
		}
		
		return rowsDeleted;
	}
	
	@Override
	public String getType(Uri uri)
	{
		final int match = sUriMatcher.match(uri);
		switch (match)
		{
			case LATEST_NEWS:
				return NewsEntry.CONTENT_LIST_TYPE;
			case NEWS_ID:
				return NewsEntry.CONTENT_ITEM_TYPE;
			default:
				throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
		}
	}
}
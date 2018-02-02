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
import android.widget.Toast;

import com.abdulrahmanjavanrd.newsappchallenge.R;
import com.abdulrahmanjavanrd.newsappchallenge.data.NewsContract.NewsEntry;

import timber.log.Timber;

import static com.abdulrahmanjavanrd.newsappchallenge.data.NewsContract.NewsEntry.LATEST_NEWS_TABLE_NAME;
import static com.abdulrahmanjavanrd.newsappchallenge.data.NewsContract.NewsEntry._ID;

/**
 * Created by Abdullah Aldobaie (akdPro) on 1/29/18 at 12:01 AM.
 *
 */

public class NewsProvider extends ContentProvider
{
	public static final String LOG_TAG = NewsProvider.class.getSimpleName();
	// When query all data .
	private static final int LATEST_NEWS = 0;
	// When query By Id .
	private static final int NEWS_BY_ID = 1;
	// When query by name .
	private static final int NEWS_BY_NAME = 2;

	// Increase one
	private static final  int News_favorite = 10 ;
	private static final  int News_favorite_by_id = 11 ;
	private static final  int News_favorite_by_name = 12 ;
	private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);


	public static UriMatcher buildUriMatcher(){
		String content = NewsContract.CONTENT_AUTHORITY;
		UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		// New Articles  Table
		uriMatcher.addURI(content,NewsContract.PATH_NEWS,LATEST_NEWS);
		uriMatcher.addURI(content,NewsContract.PATH_NEWS+"/#",NEWS_BY_ID);
		uriMatcher.addURI(content,NewsContract.PATH_NEWS+"/*",NEWS_BY_NAME);
		// Favorite Table ..
		uriMatcher.addURI(content,NewsContract.PATH_favorite,News_favorite);
		uriMatcher.addURI(content,NewsContract.PATH_favorite+"/#",News_favorite_by_id);
		uriMatcher.addURI(content,NewsContract.PATH_favorite+"/*",News_favorite_by_name);

		return uriMatcher;
	}

	public static final UriMatcher myUriMatcher = buildUriMatcher();
	static
	{
		sUriMatcher.addURI(NewsContract.CONTENT_AUTHORITY, NewsContract.PATH_NEWS, LATEST_NEWS);
		sUriMatcher.addURI(NewsContract.CONTENT_AUTHORITY, NewsContract.PATH_NEWS + "/#", NEWS_BY_ID);
		sUriMatcher.addURI(NewsContract.CONTENT_AUTHORITY, NewsContract.PATH_NEWS + "/*", NEWS_BY_NAME);
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
				cursor = database.query(LATEST_NEWS_TABLE_NAME, projection, selection, selectionArgs,
					 null, null, sortOrder);
				break;
			case NEWS_BY_ID:
				selection = NewsEntry._ID + "=?";
				selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
				
				cursor = database.query(LATEST_NEWS_TABLE_NAME, projection, selection, selectionArgs,
					 null, null, sortOrder);
				break;
			case NEWS_BY_NAME:
				selection = NewsEntry.COLUMN_NEWS_TITLE + " =?";
				selectionArgs = new String[]{uri.getLastPathSegment()};
				cursor = database.query(LATEST_NEWS_TABLE_NAME, projection, selection, selectionArgs,
						null, null, sortOrder);
				break;
			default:
				throw new IllegalArgumentException("Cannot query unknown URI " + uri);
		}
		//update Ui .
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
//		String title = values.getAsString(NewsEntry.COLUMN_NEWS_TITLE);
//		if (title == null)
//		{
//			throw new IllegalArgumentException("News requires a title");
//		}
//
//		String date = values.getAsString(NewsEntry.COLUMN_NEWS_DATE);
//		if (date == null)
//		{
//			throw new IllegalArgumentException("News requires a date");
//		}
//
//		String url = values.getAsString(NewsEntry.COLUMN_NEWS_URL);
//		if (url == null)
//		{
//			throw new IllegalArgumentException("News requires a url");
//		}
		SQLiteDatabase database = mDbHelper.getWritableDatabase();
//		 database.insert(LATEST_NEWS_TABLE_NAME, null, values);
	long rowId =database.insertWithOnConflict(LATEST_NEWS_TABLE_NAME, null, values,SQLiteDatabase.CONFLICT_REPLACE);
		if (rowId == -1)
		{
//			Log.e(LOG_TAG, "Failed to insert row for " + uri);
			Timber.e(getContext().getString(R.string.failed_inserted));
			return null;
		}else {
			Timber.v("Successful inserted .. ");
			// update Ui .
			getContext().getContentResolver().notifyChange(uri, null);
		}
		return ContentUris.withAppendedId(uri, rowId);
	}
	
	@Override
	public int update(Uri uri, ContentValues contentValues, String selection, String[] selectionArgs)
	{
	    switch (sUriMatcher.match(uri)){
			case LATEST_NEWS :
				return  updateRecord(uri,LATEST_NEWS_TABLE_NAME,contentValues,selection,selectionArgs);
			case NEWS_BY_ID:
				selection = _ID +" = ?";
				// get last number in the uri .
				selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
				return updateRecord(uri,LATEST_NEWS_TABLE_NAME,contentValues,selection,selectionArgs);
			case NEWS_BY_NAME:
				selection = _ID +" =?";
				// get last str in the uri .
				selectionArgs = new String[]{uri.getLastPathSegment()};
				return updateRecord(uri,LATEST_NEWS_TABLE_NAME,contentValues,selection,selectionArgs);
				default:
					throw new IllegalArgumentException("Failed update record" + uri);
		}
	}

	private int updateRecord(Uri uri, String tableName, ContentValues values, String selection, String[] selectionArgs) {
		SQLiteDatabase dataBase = mDbHelper.getWritableDatabase();
		int rowId =  dataBase.update(tableName,values,selection,selectionArgs);
		if (rowId == 0){
			Timber.v("empty data");
		}else{
			getContext().getContentResolver().notifyChange(uri,null);
		}
		return rowId ;
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
				rowsDeleted = database.delete(LATEST_NEWS_TABLE_NAME, selection, selectionArgs);
				break;
			case NEWS_BY_ID:
				selection = NewsEntry._ID + "=?";
				selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
				rowsDeleted = database.delete(LATEST_NEWS_TABLE_NAME, selection, selectionArgs);
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
		switch (myUriMatcher.match(uri))
		{
			case LATEST_NEWS: // articles table , all data .
				return NewsEntry.CONTENT_LIST_TYPE;
			case NEWS_BY_ID:
				return NewsEntry.CONTENT_ITEM_TYPE;

			case News_favorite : // favorite tables .. all data ..
				return NewsContract.NewsFavorite.CONTENT_TYPE ;
			case News_favorite_by_id:
				return NewsContract.NewsFavorite.CONTENT_ITEM;
			default:
				throw new IllegalStateException("Unknown URI " + uri + " with match " + match);
		}
	}
}
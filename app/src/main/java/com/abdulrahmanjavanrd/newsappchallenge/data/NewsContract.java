package com.abdulrahmanjavanrd.newsappchallenge.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * @author  Abdullah.Aldobaie (akdPro) && Abdurlahman.A on 1/28/18 at 11:00 PM.
 */

public final class NewsContract
{
	private NewsContract() {
		throw new AssertionError("No NewsContract instance");
	}
	
	public static final String CONTENT_AUTHORITY = "com.abdulrahmanjavanrd.newsappchallenge";
	public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
	public static final String PATH_NEWS = "news";
	public static final String PATH_favorite = "favorite";

	public static final class NewsEntry implements BaseColumns
	{
		
		public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_NEWS);
		
		public static final String CONTENT_LIST_TYPE =
			 ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_NEWS;
		
		public static final String CONTENT_ITEM_TYPE =
			 ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_NEWS;
		
		public final static String LATEST_NEWS_TABLE_NAME = "latest_news";

		public final static String _ID = BaseColumns._ID;
		public final static String COLUMN_NEWS_TITLE = "title";
		public final static String COLUMN_NEWS_SECTION = "section";
		public final static String COLUMN_NEWS_DATE = "date";
		public final static String COLUMN_NEWS_IMAGE = "image";
		public final static String COLUMN_NEWS_AUTHOR = "author";
		public final static String COLUMN_NEWS_URL = "url";
		public final static String COLUMN_NEWS_DESC = "desc";



	}
	public static final class NewsFavorite implements BaseColumns{

		// content://com.abdurlahmanjavanrd.newsappchallenge/favorite.
		public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_favorite).build();

		public static final String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE+"/"+CONTENT_URI+"/" +PATH_favorite;
		public static final String CONTENT_ITEM = ContentResolver.ANY_CURSOR_ITEM_TYPE+"/"+CONTENT_URI+"/" +PATH_favorite;

		// Define the table scheme ..
		public final static String FAVORITE_TABLE_NAME = "latest_news";
		public final static String _ID = BaseColumns._ID;
		public final static String COLUMN_NEWS_TITLE = "title";
		public final static String COLUMN_NEWS_SECTION = "section";
		public final static String COLUMN_NEWS_DATE = "date";
		public final static String COLUMN_NEWS_IMAGE = "image";
		public final static String COLUMN_NEWS_AUTHOR = "author";
		public final static String COLUMN_NEWS_URL = "url";
		public final static String COLUMN_NEWS_DESC = "desc";

		// find specific table ..
		public static Uri buildFavoriteTable(long id){
			return ContentUris.withAppendedId(CONTENT_URI,id);
		}
	}
}

package com.abdulrahmanjavanrd.newsappchallenge.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

/*
 * Created by Abdullah Aldobaie (akdPro) on 1/28/18 at 11:00 PM.
 */

public final class NewsContract
{
	
	private NewsContract() { }
	
	public static final String CONTENT_AUTHORITY = "com.abdulrahmanjavanrd.newsappchallenge";
	public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
	public static final String PATH_NEWS = "news";
	
	public static final class NewsEntry implements BaseColumns
	{
		
		public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_NEWS);
		
		public static final String CONTENT_LIST_TYPE =
			 ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_NEWS;
		
		public static final String CONTENT_ITEM_TYPE =
			 ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_NEWS;
		
		public final static String LATEST_NEWS_TABLE_NAME = "latest_news";
		public final static String FAVORITE_NEWS_TABLE_NAME = "latest_news";
		
		public final static String _ID = BaseColumns._ID;
		
		public final static String COLUMN_NEWS_TITLE = "title";
		public final static String COLUMN_NEWS_SECTION = "section";
		public final static String COLUMN_NEWS_DATE = "date";
		public final static String COLUMN_NEWS_IMAGE = "image";
		public final static String COLUMN_NEWS_AUTHOR = "author";
		public final static String COLUMN_NEWS_URL = "url";
		public final static String COLUMN_NEWS_DESC = "desc";
	}
}

package com.abdulrahmanjavanrd.newsappchallenge.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.abdulrahmanjavanrd.newsappchallenge.data.ArticlesContract;
import com.abdulrahmanjavanrd.newsappchallenge.data.ArticlesDBHelper;

import timber.log.Timber;

/**
 * @author  Abdulrahman.A  02/02/2018.
 */

public class ArticlesProvider extends ContentProvider {

    ArticlesDBHelper helper ;


    //Define int for each Uri to query data ..
    private static final  int NEW_ARTICEL = 100 ;
    private static final  int NEW_ARTICEL_BY_ID = 101 ;
    private static final  int NEW_ARTICEL_BY_NAME = 103 ;
    private static final  int FAVORITE_ARTICLE = 200 ;
    private static final  int FAVORITE_ARTICLE_BY_ID = 201 ;
    private static final  int FAVORITE_ARTICLE_BY_NAME = 203 ;

    // create new variables = buildUriMatcher ..
    private static UriMatcher uriMatcher = buildUriMatcher();
    // create uri builder method to determine which one using ...
    private  static UriMatcher buildUriMatcher(){
        String content = ArticlesContract.CONTENT_AUTHORITY ;
        UriMatcher userUriMatchermUser = new UriMatcher(UriMatcher.NO_MATCH); // return -1
        // build uri for New Articles table ..
        userUriMatchermUser.addURI(content,ArticlesContract.NEW_ARTICLE_PATH,NEW_ARTICEL);
        userUriMatchermUser.addURI(content,ArticlesContract.NEW_ARTICLE_PATH+"/#",NEW_ARTICEL_BY_ID);
        userUriMatchermUser.addURI(content,ArticlesContract.NEW_ARTICLE_PATH+"/*",NEW_ARTICEL_BY_NAME);
        // build uri for favorite Articles table .
        userUriMatchermUser.addURI(content,ArticlesContract.FAVORITE_ARTICLE_PATH,FAVORITE_ARTICLE);
        userUriMatchermUser.addURI(content,ArticlesContract.FAVORITE_ARTICLE_PATH+"/#",FAVORITE_ARTICLE_BY_ID);
        userUriMatchermUser.addURI(content,ArticlesContract.FAVORITE_ARTICLE_PATH+"/*",FAVORITE_ARTICLE_BY_NAME);
        return userUriMatchermUser ;
    }

    @Override
    public boolean onCreate() {
        helper = new ArticlesDBHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        SQLiteDatabase dataBase = helper.getReadableDatabase();
        Cursor cursor ;
        switch (uriMatcher.match(uri)){
            case NEW_ARTICEL:
                cursor = dataBase.query(
                        ArticlesContract.NewArticlesEntery.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case NEW_ARTICEL_BY_ID:
               selection = ArticlesContract.NewArticlesEntery._ID + " = ?";
               selectionArgs = new String[] {String.valueOf(ContentUris.parseId(uri))};
                cursor = dataBase.query(
                        ArticlesContract.NewArticlesEntery.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            case NEW_ARTICEL_BY_NAME:
                selection = ArticlesContract.NewArticlesEntery.COLUMN_NEWS_TITLE + " = ?";
                selectionArgs = new String[] {uri.getLastPathSegment()};
                cursor = dataBase.query(
                        ArticlesContract.NewArticlesEntery.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;

            case FAVORITE_ARTICLE:
                cursor = dataBase.query(
                 ArticlesContract.FavoriteArticleEntery.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder

                );
                break;

            case FAVORITE_ARTICLE_BY_ID:
                selection = ArticlesContract.FavoriteArticleEntery._ID+" = ?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = dataBase.query(
                        ArticlesContract.FavoriteArticleEntery.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder

                );
                break;
            case FAVORITE_ARTICLE_BY_NAME:
                selection = ArticlesContract.FavoriteArticleEntery.COLUMN_NEWS_TITLE + " = ?";
                selectionArgs = new String[]{uri.getLastPathSegment()};
                cursor = dataBase.query(
                        ArticlesContract.FavoriteArticleEntery.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder

                );
               break;
                default:
                    throw new IllegalArgumentException("Unkown table name ..");
        }
        cursor.setNotificationUri(getContext().getContentResolver(),uri);
        return cursor ;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
//        switch (uriMatcher.match(uri)) {
//            case NEW_ARTICEL:
//                return insertNewRecord(uri, ArticlesContract.NewArticlesEntery.TABLE_NAME, values);
//            case NEW_ARTICEL_BY_ID:
//                return insertNewRecord(uri, ArticlesContract.NewArticlesEntery.TABLE_NAME, values);
//            case NEW_ARTICEL_BY_NAME:
//                return insertNewRecord(uri, ArticlesContract.NewArticlesEntery.TABLE_NAME, values);
//            case FAVORITE_ARTICLE:
//                return insertNewRecord(uri, ArticlesContract.NewArticlesEntery.TABLE_NAME, values);
//            case FAVORITE_ARTICLE_BY_ID:
//                return insertNewRecord(uri, ArticlesContract.NewArticlesEntery.TABLE_NAME, values);
//            case FAVORITE_ARTICLE_BY_NAME:
//                return insertNewRecord(uri, ArticlesContract.NewArticlesEntery.TABLE_NAME, values);
//            default:
//                throw new IllegalArgumentException("FAILED URI " + uri);
//        }
        SQLiteDatabase dataBase = helper.getWritableDatabase();
        long rowId ;
        Uri whichTableUri ;
//        switch (uriMatcher.match(uri)){
//            case NEW_ARTICEL:
//            case NEW_ARTICEL_BY_ID:
//            case NEW_ARTICEL_BY_NAME:
//                return insertNewRecord(uri,ArticlesContract.NewArticlesEntery.TABLE_NAME,values);
//            case FAVORITE_ARTICLE:
//            case FAVORITE_ARTICLE_BY_ID:
//            case FAVORITE_ARTICLE_BY_NAME:
//                return insertNewRecord(uri,ArticlesContract.FavoriteArticleEntery.TABLE_NAME,values);
//        }
        switch (uriMatcher.match(uri)){
            case NEW_ARTICEL:
            case NEW_ARTICEL_BY_ID:
            case NEW_ARTICEL_BY_NAME:
                rowId = dataBase.insert(ArticlesContract.NewArticlesEntery.TABLE_NAME,null,values);
                if (rowId > 0 ){
                    whichTableUri = ArticlesContract.NewArticlesEntery.buildGenreUri(rowId);
                }else{
                    throw new IllegalArgumentException("Unable to insert row in : "+ rowId);
                }
                break;
            case FAVORITE_ARTICLE:
            case FAVORITE_ARTICLE_BY_ID:
            case FAVORITE_ARTICLE_BY_NAME:
                rowId = dataBase.insert(ArticlesContract.FavoriteArticleEntery.TABLE_NAME,null,values);
                if (rowId > 0){
                    whichTableUri = ArticlesContract.FavoriteArticleEntery.buildGenreUri(rowId);
                }else{
                    throw new IllegalArgumentException("Unable to insert row in : "+ rowId);
                }
                break;
            default:
                throw new IllegalArgumentException("Unknown uri .. "+ uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return whichTableUri;
    }

    private Uri insertNewRecord(Uri uri, String tableName, ContentValues values) {
        SQLiteDatabase dataBase = helper.getWritableDatabase();
        long rowId ;
        Uri whichTableUri ;
        switch (uriMatcher.match(uri)){
            case NEW_ARTICEL:
            case NEW_ARTICEL_BY_ID:
            case NEW_ARTICEL_BY_NAME:
                rowId = dataBase.insert(ArticlesContract.NewArticlesEntery.TABLE_NAME,null,values);
                if (rowId > 0 ){
                    whichTableUri = ArticlesContract.NewArticlesEntery.buildGenreUri(rowId);
                }else{
                    throw new IllegalArgumentException("Unable to insert row in : "+ rowId);
                }
                break;
            case FAVORITE_ARTICLE:
            case FAVORITE_ARTICLE_BY_ID:
            case FAVORITE_ARTICLE_BY_NAME:
                rowId = dataBase.insert(ArticlesContract.FavoriteArticleEntery.TABLE_NAME,null,values);
                if (rowId > 0){
                    whichTableUri = ArticlesContract.FavoriteArticleEntery.buildGenreUri(rowId);
                }else{
                    throw new IllegalArgumentException("Unable to insert row in : "+ rowId);
                }
                break;
                default:
                    throw new IllegalArgumentException("Unknown uri .. "+ uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return whichTableUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase dataBase = helper.getWritableDatabase();
        int rows ;
        switch (uriMatcher.match(uri)){
            case NEW_ARTICEL:
                rows = dataBase.delete(ArticlesContract.NewArticlesEntery.TABLE_NAME,selection,selectionArgs);
                break;
            case FAVORITE_ARTICLE:
                rows = dataBase.delete(ArticlesContract.FavoriteArticleEntery.TABLE_NAME,selection,selectionArgs);
                break;
                default:
                    throw new IllegalArgumentException("FAILED DELETE RECORD .."+uri);

        }
        if (selection != null || rows != 0){
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return rows;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase dataBase = helper.getWritableDatabase();
        int rows ;
        switch (uriMatcher.match(uri)){
            case NEW_ARTICEL:
            case NEW_ARTICEL_BY_ID:
            case NEW_ARTICEL_BY_NAME:
                rows = dataBase.update(ArticlesContract.NewArticlesEntery.TABLE_NAME,values,selection,selectionArgs);
                break;
            case FAVORITE_ARTICLE:
            case FAVORITE_ARTICLE_BY_ID:
            case FAVORITE_ARTICLE_BY_NAME:
                rows = dataBase.update(ArticlesContract.FavoriteArticleEntery.TABLE_NAME,values,selection,selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("FAILED UPDATE RECORD .. "+uri);
        }
        if (rows != 0){
            getContext().getContentResolver().notifyChange(uri,null);
        }
        return rows;
    }
    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        switch (uriMatcher.match(uri)){
            case NEW_ARTICEL :
                return ArticlesContract.NewArticlesEntery.CONTENT_TYPE;
            case NEW_ARTICEL_BY_ID:
                return ArticlesContract.NewArticlesEntery.CONTENT_ITEM;
            case FAVORITE_ARTICLE:
                return ArticlesContract.FavoriteArticleEntery.CONTENT_TYPE;
            case FAVORITE_ARTICLE_BY_ID:
                return ArticlesContract.FavoriteArticleEntery.CONTENT_ITEM;
                default:
                    Timber.e("Error in getType method .");
                    throw new UnsupportedOperationException("Can't find this uri ."+uri);
        }
    }
}

package com.abdulrahmanjavanrd.newsappchallenge.utilties;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;

import com.abdulrahmanjavanrd.newsappchallenge.data.ArticlesContract;
import com.abdulrahmanjavanrd.newsappchallenge.model.News;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

import static com.abdulrahmanjavanrd.newsappchallenge.data.ArticlesContract.NewArticlesEntery.COLUMN_NEWS_DATE;
import static com.abdulrahmanjavanrd.newsappchallenge.data.ArticlesContract.NewArticlesEntery.COLUMN_NEWS_DESC;
import static com.abdulrahmanjavanrd.newsappchallenge.data.ArticlesContract.NewArticlesEntery.COLUMN_NEWS_IMAGE;
import static com.abdulrahmanjavanrd.newsappchallenge.data.ArticlesContract.NewArticlesEntery.COLUMN_NEWS_TITLE;
import static com.abdulrahmanjavanrd.newsappchallenge.data.ArticlesContract.NewArticlesEntery.COLUMN_NEWS_URL;
import static com.abdulrahmanjavanrd.newsappchallenge.data.ArticlesContract.NewArticlesEntery.COLUMN_NEWS_AUTHOR;
import static com.abdulrahmanjavanrd.newsappchallenge.data.ArticlesContract.NewArticlesEntery.COLUMN_NEWS_SECTION;


/**
 * @author Abdulrahman.A
 */

public class QueryUtils {
    // Magic Numbers .
    private static final int READ_TIMEOUT = 10000;
    private static final int CONNECT_TIMEOUT = 15000;
    private static Context context ;

    public QueryUtils(Context context){
        this.context = context;
    }

    /**
     * Note : This method public, Which mean i i called this method where i need it .
     *
     * @param thrUrl pass this param into {@link #createUrl(String)}
     * @return list of news with all json data .
     */
    public static List<News> fetchNewsData(String thrUrl) {
        URL url = createUrl(thrUrl);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Timber.e( "Error with fetch data method "+e.getMessage());
        }
        List<News> news = extractJsonData(jsonResponse);
        return news;
    }

    // create url.
    private static URL createUrl(String theUrl) {
        URL url = null;
        try {
            url = new URL(theUrl);
        } catch (MalformedURLException e) {
            Timber.e("Please check your Link URL " + e.getMessage());
        }
        return url;
    }


    /**
     * @param url to openHttpConnected .
     * @return String After converted by {@link #convertStreamToData(InputStream)} .
     * @throws IOException if Wrong URL OR Bad Connection
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String response = null;
        HttpURLConnection connection = null;
        InputStream inputStream = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(READ_TIMEOUT);
            connection.setConnectTimeout(CONNECT_TIMEOUT);
            // check if connection success .
            if (connection.getResponseCode() == 200) {
                inputStream = new BufferedInputStream(connection.getInputStream());
                response = convertStreamToData(inputStream);
            } else {
                Timber.e( "Bad Connection Please check url");
                Timber.e("Error of Stream ##" + connection.getErrorStream());
            }
        } catch (IOException e) {
            Timber.e(e.getMessage());
        } finally {
            if (connection != null)
                connection.disconnect();
            if (inputStream != null)
                inputStream.close();
        }
        return response;
    }


    /**
     * @param ins = InputStream , this param convert binary data to human data .
     * @return String.
     */
    private static String convertStreamToData(InputStream ins) {
        StringBuilder sb = new StringBuilder();
        String line;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(ins))) {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');
            }
        } catch (IOException e) {
            Timber.e("Failed to Convert Stream to String " + e.getMessage());
        }
        return sb.toString();
    }

    /**
     * @param jsonUrl pass this url into {@link #createUrl(String)} .
     * @return list of News contain all json data .
     */
    private static List<News> extractJsonData(String jsonUrl) {
        List<News> newsList = new ArrayList<>();
        if (TextUtils.isEmpty(jsonUrl)) {
            return null;
        }
        // declare publisher name ;
        String webPublisher;
        // declare ContentValues ..
        ContentValues values = new ContentValues();
        try {
            JSONObject baseKey = new JSONObject(jsonUrl);
            JSONObject root = baseKey.getJSONObject("response");
            JSONArray firstArray = root.getJSONArray("results");
            // check if JsonArray contain any elements before looping.
            if (firstArray.length() > 0) {
                for (int i = 0; i < firstArray.length(); i++) {
                    JSONObject index = firstArray.getJSONObject(i);
                    //First get Title , Section , Date, webUrl .
                    String webTitle = index.getString("webTitle");
                    String sectionName = index.getString("sectionName");
                    String date = index.getString("webPublicationDate");
                    String webUrl = index.getString("webUrl");
                    // Now fetch image and Article summary
                    JSONObject fields = index.getJSONObject("fields");
                    String thumbnail = fields.getString("thumbnail");
                    //get webTitle from array tags ..
                    JSONArray tags = index.getJSONArray("tags");
                    /** Create new json object to go inside the tags array, And get the publisher name,
                     *     First check if tags array contain any elements OR not if yes save the value
                     *     in {@link webPublisher} else set {@link webPublisher} empty .*/
                    if (tags.length() > 0) {
                        JSONObject tagIndex = tags.getJSONObject(0);
                        webPublisher = tagIndex.getString("webTitle");
                    } else {
                        webPublisher = "";
                    }
                    // Now fetch summary text from blocks
                    JSONObject blocks = index.getJSONObject("blocks");
                    // Now get all array inside this blocks .
                    JSONArray bodyArray = blocks.getJSONArray("body");
                    // Now fetch data in bodyArray .
                    JSONObject secondIndex = bodyArray.getJSONObject(0);
                    // Now i can access the all elements inside this array .
                    String articleSummary = secondIndex.getString("bodyTextSummary");
                    // Save all data in data Base ..
                    if (showDataBeforeSave(webTitle)){
                        Timber.v("yes ... it's New  ");
                        values.put(COLUMN_NEWS_TITLE, webTitle);
                        values.put(COLUMN_NEWS_SECTION, sectionName);
                        values.put(COLUMN_NEWS_DATE, date);
                        values.put(COLUMN_NEWS_IMAGE, thumbnail);
                        values.put(COLUMN_NEWS_AUTHOR, webPublisher);
                        values.put(COLUMN_NEWS_URL, webUrl);
                        values.put(COLUMN_NEWS_DESC, articleSummary);
                        Uri uri = ArticlesContract.NewArticlesEntery.CONTENT_URI;
                        Uri rowAdd = context.getContentResolver().insert(uri, values);
                        Timber.v("Inserted .. " + rowAdd);
                    }else{
                        Timber.v("No This  "+webTitle + " Exists.");
                    }
                    // Now save this values in News class .
                    newsList.add(new News(webTitle,thumbnail,articleSummary,sectionName,date,webPublisher,webUrl));
                }
            } else {
                Timber.e("JsonArray is empty ");
            }
        } catch (JSONException e) {
            Timber.e("Failed JsonObject "+e.getMessage());
        }
        return newsList;
    }

//    private ContentValues getAllData(){
//
//    }

    private static boolean showDataBeforeSave(String where){
        // Insert new data in New articles table ..
        String[] projection ={ArticlesContract.NewArticlesEntery._ID, COLUMN_NEWS_TITLE,
        COLUMN_NEWS_DESC};
        String selection = COLUMN_NEWS_TITLE+" = ?";
        String[] selectionArgs = {where};
        Timber.v("Title receive = "+where);
        Uri uri = Uri.withAppendedPath(ArticlesContract.NewArticlesEntery.CONTENT_URI, COLUMN_NEWS_TITLE);
        Timber.v("uri = "+uri);
        Cursor cursor = context.getContentResolver().query(ArticlesContract.NewArticlesEntery.CONTENT_URI,projection,selection,selectionArgs,"DESC");
        Timber.v("Cursor = "+cursor.getCount());
        if (cursor !=null && cursor.moveToFirst()){
            String title = cursor.getString(cursor.getColumnIndex(COLUMN_NEWS_TITLE));
            Timber.v("Title Found = "+title);
            if (title.equalsIgnoreCase(where)){
               return false;
            }
        }
        return true;
    }
}

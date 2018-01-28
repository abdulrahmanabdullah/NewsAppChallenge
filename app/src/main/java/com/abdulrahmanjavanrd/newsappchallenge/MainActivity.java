package com.abdulrahmanjavanrd.newsappchallenge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.abdulrahmanjavanrd.newsappchallenge.adapter.NewsAdapter;
import com.abdulrahmanjavanrd.newsappchallenge.model.News;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // TEst my work
        List<News> news  = new ArrayList<>();
        news.add(new News("test","someuri","Test summary ","sectionName","date here ","me"));
        news.add(new News("test","someuri","Test summary ","sectionName","date here ","me"));
        news.add(new News("test","someuri","Test summary ","sectionName","date here ","me"));
        news.add(new News("test","someuri","Test summary ","sectionName","date here ","me"));
        news.add(new News("test","someuri","Test summary ","sectionName","date here ","me"));
        news.add(new News("test","someuri","Test summary ","sectionName","date here ","me"));
        news.add(new News("test","someuri","Test summary ","sectionName","date here ","me"));
        news.add(new News("test","someuri","Test summary ","sectionName","date here ","me"));


        NewsAdapter adapter = new NewsAdapter(this,news);
        // ListView ...
        ListView list = findViewById(R.id.list_items);
        list.setAdapter(adapter);
    }
}

package com.abdulrahmanjavanrd.newsappchallenge;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.abdulrahmanjavanrd.newsappchallenge.fragments.FavoriteNewsFragment;
import com.abdulrahmanjavanrd.newsappchallenge.fragments.LastNewsFragment;
import com.abdulrahmanjavanrd.newsappchallenge.fragments.PopularNewsFragment;
import com.abdulrahmanjavanrd.newsappchallenge.fragments.SaveArticleInDataBase;
import com.abdulrahmanjavanrd.newsappchallenge.view.MyPager;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolBar = findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);
        ViewPager viewPager = findViewById(R.id.view_pager);
        setupViewPager(viewPager);
        TabLayout tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
//        MyPager pager = new MyPager(getSupportFragmentManager());
//        viewPager.setAdapter(pager);
//        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
//        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(viewPager));

    }

    private void setupViewPager(ViewPager viewPager){
        MyPager pager = new MyPager(getSupportFragmentManager());
        //todo::: check internet connection here..Done
        if (checkConnection(MainActivity.this)){
            pager.addFragment(new LastNewsFragment(),getString(R.string.last_news));
        }else {
            pager.addFragment(new SaveArticleInDataBase(),getString(R.string.last_news));
        }
        pager.addFragment(new FavoriteNewsFragment(),getString(R.string.favorite_article));
        pager.addFragment(new PopularNewsFragment(),getString(R.string.most_read));
        viewPager.setAdapter(pager);
    }
    private boolean checkConnection(Context context){
        ConnectivityManager mConnective = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo wifi = mConnective.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo moblie = mConnective.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (wifi != null && wifi.isConnected()){
            return true;
        }else if (moblie != null && moblie.isConnected()){
            return true;
        }
        return false;
    }
}

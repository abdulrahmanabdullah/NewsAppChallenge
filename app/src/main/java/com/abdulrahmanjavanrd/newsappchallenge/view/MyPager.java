package com.abdulrahmanjavanrd.newsappchallenge.view;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nfs05 on 29/01/2018.
 */

public class MyPager extends FragmentPagerAdapter {

    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentListTitle = new ArrayList<>();


    public MyPager(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }


    //Call this method in MainActivity to add fragments .
    public void addFragment(Fragment fragment , String title ){
        mFragmentList.add(fragment);
        mFragmentListTitle.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentListTitle.get(position);
    }
}

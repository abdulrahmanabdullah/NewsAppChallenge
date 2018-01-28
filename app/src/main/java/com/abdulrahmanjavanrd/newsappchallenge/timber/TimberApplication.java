package com.abdulrahmanjavanrd.newsappchallenge.timber;

import android.app.Application;
import android.os.Build;

import timber.log.BuildConfig;
import timber.log.Timber;

/**
 * @author  Abdulrahman. && Abdullah on 28/01/2018.
 */

public class TimberApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();

        //initialize Timber .
        Timber.plant(new Timber.DebugTree());
    }
}

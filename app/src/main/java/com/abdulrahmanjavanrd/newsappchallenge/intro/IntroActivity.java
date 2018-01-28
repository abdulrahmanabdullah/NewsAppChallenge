package com.abdulrahmanjavanrd.newsappchallenge.intro;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.abdulrahmanjavanrd.newsappchallenge.MainActivity;
import com.abdulrahmanjavanrd.newsappchallenge.R;

import timber.log.Timber;

/**
 * @author  Abdulrahman.a && Abdullah on 28/01/2018.
 */

public class IntroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        //Start Thread .
        new GoToMainActivity().start();

    }
    private class GoToMainActivity extends Thread {

        @Override
        public void run() {
            try {
                Thread.sleep(3000);
            } catch (Exception e) {
                Timber.d("Can't load MainActivity ");
            }
            Intent mIntent = new Intent(IntroActivity.this, MainActivity.class);
            startActivity(mIntent);
            IntroActivity.this.finish();
        }

    }
}

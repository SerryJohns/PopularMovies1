package com.example.popularmovies;

import android.app.Application;

import com.facebook.stetho.Stetho;

public class PopularMoviesApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this);
        }
    }
}

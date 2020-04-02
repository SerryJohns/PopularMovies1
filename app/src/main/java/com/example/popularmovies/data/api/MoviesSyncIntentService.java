package com.example.popularmovies.data.api;

import android.app.IntentService;
import android.content.Intent;

import androidx.annotation.Nullable;

import com.example.popularmovies.data.Repository;
import com.example.popularmovies.utils.InjectorUtils;

public class MoviesSyncIntentService extends IntentService {
    public MoviesSyncIntentService() {
        super(MoviesSyncIntentService.class.getSimpleName());
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Repository repository = InjectorUtils.provideRepository(this.getApplicationContext());
        repository.fetchMovies();
    }
}

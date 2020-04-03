package com.example.popularmovies.data.workers;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.example.popularmovies.data.Repository;
import com.example.popularmovies.utils.InjectorUtils;

public class SeedDatabaseWorker extends Worker {
    public SeedDatabaseWorker(@NonNull Context context, @NonNull WorkerParameters params) {
        super(context, params);
    }

    @NonNull
    @Override
    public Result doWork() {
        Repository repository = InjectorUtils.provideRepository(this.getApplicationContext());
        repository.fetchMovies();
        return Result.success();
    }

}

package com.example.popularmovies.data.api;

import android.content.Context;
import android.net.Uri;

import androidx.work.BackoffPolicy;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.example.popularmovies.data.model.Movie;
import com.example.popularmovies.data.model.Review;
import com.example.popularmovies.data.model.Trailer;
import com.example.popularmovies.data.workers.SeedDatabaseWorker;
import com.example.popularmovies.utils.AppExecutors;
import com.example.popularmovies.utils.JsonUtils;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class OnlineDataSource {
    private static final Object LOCK = new Object();
    private static OnlineDataSource onlineDataSource;
    private final AppExecutors executors;
    private final Context context;

    private OnlineDataSource(Context context, AppExecutors executors) {
        this.executors = executors;
        this.context = context;
    }

    public static OnlineDataSource getInstance(Context context, AppExecutors executors) {
        if (onlineDataSource == null) {
            synchronized (LOCK) {
                onlineDataSource = new OnlineDataSource(context, executors);
            }
        }
        return onlineDataSource;
    }

    public void getMostPopularMovies(ResponseCallback<List<Movie>> callback) {
        executors.getNetworkIO().execute(() -> {
            Uri path = NetworkUtils.getBaseUrl().buildUpon()
                    .appendEncodedPath("movie/popular")
                    .build();
            String result = execute(path, callback);
            if (result != null) {
                List<Movie> movieList = JsonUtils.parseMovieList(result, false);
                callback.onSuccess(movieList);
            }
        });
    }

    public void getTopRatedMovies(ResponseCallback<List<Movie>> callback) {
        executors.getNetworkIO().execute(() -> {
            Uri path = NetworkUtils.getBaseUrl().buildUpon()
                    .appendEncodedPath("movie/top_rated")
                    .build();
            String result = execute(path, callback);
            if (result != null) {
                List<Movie> movieList = JsonUtils.parseMovieList(result, true);
                callback.onSuccess(movieList);
            }
        });
    }

    public void getMovieTrailers(int movieId, ResponseCallback<List<Trailer>> callback) {
        executors.getNetworkIO().execute(() -> {
            Uri path = NetworkUtils.getBaseUrl().buildUpon()
                    .appendEncodedPath("movie")
                    .appendEncodedPath(Integer.toString(movieId))
                    .appendEncodedPath("videos")
                    .build();
            String result = execute(path, callback);
            if (result != null) {
                List<Trailer> movieTrailers = JsonUtils.parseMovieTrailersList(result, movieId);
                callback.onSuccess(movieTrailers);
            }
        });
    }

    public void getMovieReviews(int movieId, ResponseCallback<List<Review>> callback) {
        executors.getNetworkIO().execute(() -> {
            Uri path = NetworkUtils.getBaseUrl().buildUpon()
                    .appendEncodedPath("movie")
                    .appendEncodedPath(Integer.toString(movieId))
                    .appendEncodedPath("reviews")
                    .build();
            String result = execute(path, callback);
            if (result != null) {
                List<Review> reviewList = JsonUtils.parseMovieReviewList(result, movieId);
                callback.onSuccess(reviewList);
            }
        });
    }

    private String execute(Uri path, ResponseCallback callback) {
        if (!NetworkUtils.isOnline()) {
            callback.onError("No internet connection");
            return null;
        }
        try {
            return NetworkUtils.getResponseFromHttpUrl(path);
        } catch (IOException e) {
            e.printStackTrace();
            callback.onError(e.getMessage());
            return null;
        }
    }

    public void scheduleRecurringMoviesSync() {
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresBatteryNotLow(true)
                .setRequiresDeviceIdle(true)
                .build();

        // Schedule work request for every 6 hours
        PeriodicWorkRequest workRequest = new PeriodicWorkRequest.Builder(
                SeedDatabaseWorker.class, 6, TimeUnit.HOURS)
                .setConstraints(constraints)
                .setBackoffCriteria(
                        BackoffPolicy.LINEAR,
                        OneTimeWorkRequest.MIN_BACKOFF_MILLIS,
                        TimeUnit.MILLISECONDS
                )
                .build();
        WorkManager.getInstance(context).enqueue(workRequest);
    }
}

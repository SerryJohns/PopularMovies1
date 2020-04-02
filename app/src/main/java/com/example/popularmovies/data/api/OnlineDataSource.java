package com.example.popularmovies.data.api;

import android.net.Uri;

import com.example.popularmovies.data.model.Movie;
import com.example.popularmovies.data.model.Review;
import com.example.popularmovies.data.model.Trailer;
import com.example.popularmovies.utils.JsonUtils;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class OnlineDataSource implements OnlineRepository {
    private ExecutorService executorService;

    public OnlineDataSource() {
        this.executorService = Executors.newSingleThreadExecutor();
    }

    @Override
    public void getMostPopularMovies(ResponseCallback<List<Movie>> callback) {
        executorService.execute(() -> {
            Uri path = NetworkUtils.getBaseUrl().buildUpon()
                    .appendEncodedPath("movie/popular")
                    .build();
            String result = execute(path, callback);
            if (result != null) {
                List<Movie> movieList = JsonUtils.parseMovieList(result);
                callback.onSuccess(movieList);
            }
        });
    }

    @Override
    public void getTopRatedMovies(ResponseCallback<List<Movie>> callback) {
        executorService.execute(() -> {
            Uri path = NetworkUtils.getBaseUrl().buildUpon()
                    .appendEncodedPath("movie/top_rated")
                    .build();
            String result = execute(path, callback);
            if (result != null) {
                List<Movie> movieList = JsonUtils.parseMovieList(result);
                callback.onSuccess(movieList);
            }
        });
    }

    @Override
    public void getMovieTrailers(int movieId, ResponseCallback<List<Trailer>> callback) {
        executorService.execute(() -> {
            Uri path = NetworkUtils.getBaseUrl().buildUpon()
                    .appendEncodedPath("movie")
                    .appendEncodedPath(Integer.toString(movieId))
                    .appendEncodedPath("videos")
                    .build();
            String result = execute(path, callback);
            if (result != null) {
                List<Trailer> movieTrailers = JsonUtils.parseMovieTrailersList(result);
                callback.onSuccess(movieTrailers);
            }
        });
    }

    @Override
    public void getMovieReviews(int movieId, ResponseCallback<List<Review>> callback) {
        executorService.execute(() -> {
            Uri path = NetworkUtils.getBaseUrl().buildUpon()
                    .appendEncodedPath("movie")
                    .appendEncodedPath(Integer.toString(movieId))
                    .appendEncodedPath("reviews")
                    .build();
            String result = execute(path, callback);
            if (result != null) {
                List<Review> reviewList = JsonUtils.parseMovieReviewList(result);
                callback.onSuccess(reviewList);
            }
        });
    }

    private String execute(Uri path, ResponseCallback callback) {
        if (!NetworkUtils.isOnline()) {
            callback.onError("No internet Connection!");
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
}

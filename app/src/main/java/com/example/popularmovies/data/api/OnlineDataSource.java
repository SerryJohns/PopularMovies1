package com.example.popularmovies.data.api;

import com.example.popularmovies.data.model.Movie;
import com.example.popularmovies.utils.JsonUtils;
import com.example.popularmovies.utils.NetworkUtils;

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
            if (!NetworkUtils.isOnline()) {
                callback.onError("No internet connection!");
                return;
            }

            try {
                String result = NetworkUtils.getResponseFromHttpUrl("movie/popular");
                if (result != null) {
                    List<Movie> movieList = JsonUtils.parseMovieListJson(result);
                    callback.onSuccess(movieList);
                }
            } catch (IOException e) {
                e.printStackTrace();
                callback.onError(e.getMessage());
            }
        });
    }

    @Override
    public void getTopRatedMovies(ResponseCallback<List<Movie>> callback) {
        executorService.execute(() -> {
            if (!NetworkUtils.isOnline()) {
                callback.onError("No internet Connection!");
                return;
            }

            try {
                String result = NetworkUtils.getResponseFromHttpUrl("movie/top_rated");
                if (result != null) {
                    List<Movie> movieList = JsonUtils.parseMovieListJson(result);
                    callback.onSuccess(movieList);
                }
            } catch (IOException e) {
                e.printStackTrace();
                callback.onError(e.getMessage());
            }
        });
    }
}

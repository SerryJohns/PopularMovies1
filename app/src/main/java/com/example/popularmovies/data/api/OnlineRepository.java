package com.example.popularmovies.data.api;

import com.example.popularmovies.data.model.Movie;

import java.util.List;

public interface OnlineRepository {
    void getMostPopularMovies(ResponseCallback<List<Movie>> callback);

    void getTopRatedMovies(ResponseCallback<List<Movie>> callback);
}

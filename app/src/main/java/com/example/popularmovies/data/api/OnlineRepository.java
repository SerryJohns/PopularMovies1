package com.example.popularmovies.data.api;

import com.example.popularmovies.data.model.Movie;
import com.example.popularmovies.data.model.Review;
import com.example.popularmovies.data.model.Trailer;

import java.util.List;

public interface OnlineRepository {
    void getMostPopularMovies(ResponseCallback<List<Movie>> callback);

    void getTopRatedMovies(ResponseCallback<List<Movie>> callback);

    void getMovieTrailers(int movieId, ResponseCallback<List<Trailer>> callback);

    void getMovieReviews(int movieId, ResponseCallback<List<Review>> callback);
}

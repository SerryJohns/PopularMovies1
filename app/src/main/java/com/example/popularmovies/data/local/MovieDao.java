package com.example.popularmovies.data.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.popularmovies.data.model.Movie;
import com.example.popularmovies.data.model.MovieWithReviews;
import com.example.popularmovies.data.model.MovieWithTrailers;

import java.util.List;

@Dao
public interface MovieDao extends BaseDao<Movie> {
    // Fetch popular or top rated movies
    @Query("SELECT * FROM movie WHERE top_rated = :isTopRated")
    LiveData<List<Movie>> getMovies(boolean isTopRated);

    @Query("SELECT * FROM movie WHERE favorite = :isFavorite")
    LiveData<List<Movie>> getFavoriteMovies(boolean isFavorite);

    @Query("SELECT * FROM movie WHERE movie_id = :movieId")
    LiveData<Movie> getMovieById(int movieId);

    @Transaction
    @Query("SELECT * FROM movie WHERE movie_id = :movieId")
    LiveData<MovieWithTrailers> getMovieTrailers(int movieId);

    @Transaction
    @Query("SELECT * FROM movie WHERE movie_id = :movieId")
    LiveData<MovieWithReviews> getMovieReviews(int movieId);

    @Query("SELECT COUNT(movie_id) FROM movie")
    int getCount();

    @Update
    void updateMove(Movie movie);
}

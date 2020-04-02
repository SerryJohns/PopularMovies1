package com.example.popularmovies.data.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.example.popularmovies.data.model.Movie;
import com.example.popularmovies.data.model.MovieWithReviews;
import com.example.popularmovies.data.model.MovieWithTrailers;

import java.util.List;

@Dao
public interface MovieDao {
    @Query("SELECT * FROM movie")
    LiveData<List<Movie>> getMovies();

    @Query("SELECT * FROM movie WHERE movie_id = :movieId")
    LiveData<Movie> getMovieById(int movieId);

    @Update
    void update(Movie... movies);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovies(Movie... movies);

    @Transaction
    @Query("SELECT * FROM movie WHERE movie_id = :movieId")
    LiveData<MovieWithTrailers> getMovieTrailers(int movieId);

    @Transaction
    @Query("SELECT * FROM movie WHERE movie_id = :movieId")
    LiveData<MovieWithReviews> getMovieReviews(int movieId);
}

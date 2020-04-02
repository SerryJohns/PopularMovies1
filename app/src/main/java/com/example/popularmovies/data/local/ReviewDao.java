package com.example.popularmovies.data.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.example.popularmovies.data.model.Review;

import java.util.List;

@Dao
public interface ReviewDao extends BaseDao<Review> {
    @Query("SELECT * FROM review WHERE movie_creator_id = :movieId")
    LiveData<List<Review>> getMovieReviews(int movieId);
}

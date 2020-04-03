package com.example.popularmovies.data.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Query;

import com.example.popularmovies.data.model.Trailer;

import java.util.List;

@Dao
public interface TrailerDao extends BaseDao<Trailer> {
    @Query("SELECT * FROM trailer WHERE movie_creator_id = :movieId")
    LiveData<List<Trailer>> getMovieTrailers(int movieId);
}

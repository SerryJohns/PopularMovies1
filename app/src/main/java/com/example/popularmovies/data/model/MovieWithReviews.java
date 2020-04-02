package com.example.popularmovies.data.model;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class MovieWithReviews {
    @Embedded
    public Movie movie;

    @Relation(
            parentColumn = "movie_id",
            entityColumn = "movie_creator_id"
    )
    public List<Review> reviews;
}

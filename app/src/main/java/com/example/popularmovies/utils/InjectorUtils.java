package com.example.popularmovies.utils;

import android.content.Context;

import com.example.popularmovies.data.Repository;
import com.example.popularmovies.data.api.OnlineDataSource;
import com.example.popularmovies.data.local.AppDatabase;
import com.example.popularmovies.data.local.MovieRepository;
import com.example.popularmovies.data.local.ReviewRepository;
import com.example.popularmovies.data.local.TrailerRepository;

public class InjectorUtils {
    public static Repository provideRepository(Context context) {
        return Repository.getInstance(
                provideOnlineDataSource(context),
                provideMovieRepository(context),
                provideTrailerRepository(context),
                provideReviewRepository(context),
                getAppExecutors());
    }

    public static OnlineDataSource provideOnlineDataSource(Context context) {
        return OnlineDataSource.getInstance(context, getAppExecutors());
    }

    public static MovieRepository provideMovieRepository(Context context) {
        return MovieRepository.getInstance(
                AppDatabase.getInstance(context).movieDao());
    }

    public static ReviewRepository provideReviewRepository(Context context) {
        return ReviewRepository.getInstance(
                AppDatabase.getInstance(context).reviewDao());
    }

    public static TrailerRepository provideTrailerRepository(Context context) {
        return TrailerRepository.getInstance(
                AppDatabase.getInstance(context).trailerDao());
    }

    public static AppExecutors getAppExecutors() {
        return AppExecutors.getInstance();
    }
}

package com.example.popularmovies.utils;

import android.content.Context;

import com.example.popularmovies.data.Repository;
import com.example.popularmovies.data.api.OnlineDataSource;
import com.example.popularmovies.data.local.AppDatabase;
import com.example.popularmovies.data.local.MovieRepository;
import com.example.popularmovies.data.local.ReviewRepository;
import com.example.popularmovies.data.local.TrailerRepository;
import com.example.popularmovies.ui.detail.DetailActivityViewModelFactory;
import com.example.popularmovies.ui.main.MainActivityViewModelFactory;

public class InjectorUtils {
    public static Repository provideRepository(Context context) {
        return Repository.getInstance(
                provideOnlineDataSource(context),
                provideMovieRepository(context),
                provideTrailerRepository(context),
                provideReviewRepository(context),
                getAppExecutors(),
                context);
    }

    public static OnlineDataSource provideOnlineDataSource(Context context) {
        return OnlineDataSource.getInstance(context, getAppExecutors());
    }

    public static MovieRepository provideMovieRepository(Context context) {
        return MovieRepository.getInstance(
                AppDatabase.getInstance(context).movieDao(),
                getAppExecutors());
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

    public static MainActivityViewModelFactory provideMainActivityViewModelFactory(Context context) {
        return new MainActivityViewModelFactory(provideRepository(context));
    }

    public static DetailActivityViewModelFactory provideDetailActivityViewModelFactory(Context context) {
        return new DetailActivityViewModelFactory(provideRepository(context));
    }
}

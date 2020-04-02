package com.example.popularmovies.data;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.popularmovies.data.api.MoviesSyncIntentService;
import com.example.popularmovies.data.api.OnlineDataSource;
import com.example.popularmovies.data.api.ResponseCallback;
import com.example.popularmovies.data.local.MovieRepository;
import com.example.popularmovies.data.local.ReviewRepository;
import com.example.popularmovies.data.local.TrailerRepository;
import com.example.popularmovies.data.model.Movie;
import com.example.popularmovies.data.model.Review;
import com.example.popularmovies.data.model.Trailer;
import com.example.popularmovies.utils.AppExecutors;

import java.util.List;

public class Repository {
    private final static Object LOCK = new Object();
    private static final String TAG = Repository.class.getSimpleName();
    private static Repository repository;

    private OnlineDataSource onlineDataSource;
    private MovieRepository movieRepository;
    private TrailerRepository trailerRepository;
    private ReviewRepository reviewRepository;
    private AppExecutors executors;
    private Context mContext;
    private boolean isInitialized = false;

    private Repository(
            OnlineDataSource onlineDataSource,
            MovieRepository movieRepository,
            TrailerRepository trailerRepository,
            ReviewRepository reviewRepository,
            AppExecutors executors,
            Context context
    ) {
        this.onlineDataSource = onlineDataSource;
        this.movieRepository = movieRepository;
        this.trailerRepository = trailerRepository;
        this.reviewRepository = reviewRepository;
        this.mContext = context;
    }

    public static Repository getInstance(
            OnlineDataSource onlineDataSource,
            MovieRepository movieRepository,
            TrailerRepository trailerRepository,
            ReviewRepository reviewRepository,
            AppExecutors executors,
            Context context
    ) {
        if (repository == null) {
            synchronized (LOCK) {
                repository = new Repository(
                        onlineDataSource,
                        movieRepository,
                        trailerRepository,
                        reviewRepository,
                        executors,
                        context
                );
            }
        }
        return repository;
    }

    // Creates periodic sync requests and checks to see if an immediate sync is required.
    private synchronized void initializeData() {
        if (isInitialized) return;
        isInitialized = true;

        // Synchronize Data periodically
        onlineDataSource.scheduleRecurringMoviesSync();

        executors.getDiskIO().execute(() -> {
            if (isFetchNeeded()) {
                Intent intentToFetchData = new Intent(mContext, MoviesSyncIntentService.class);
                mContext.startService(intentToFetchData);
            }
        });
    }

    private boolean isFetchNeeded() {
        return !(movieRepository.getCount() > 0);
    }

    public void fetchMovies() {
        // Fetch Popular Movies
        onlineDataSource.getMostPopularMovies(new ResponseCallback<List<Movie>>() {
            @Override
            public void onSuccess(List<Movie> result) {
                // Cache Movie Results and fetch reviews and trailers
                fetchTrailersAndReviewsAndCacheMovies(result);
            }

            @Override
            public void onError(String errMsg) {
                Log.e(TAG, "getMostPopularMovies::onError: " + errMsg);
            }
        });

        // Fetch TopRated Movies
        onlineDataSource.getTopRatedMovies(new ResponseCallback<List<Movie>>() {
            @Override
            public void onSuccess(List<Movie> result) {
                // Cache top rated movie results and fetch reviews and trailers
                fetchTrailersAndReviewsAndCacheMovies(result);
            }

            @Override
            public void onError(String errMsg) {
                Log.e(TAG, "getTopRatedMovies::onError: " + errMsg);
            }
        });
    }

    private void fetchTrailers(int movieId) {
        onlineDataSource.getMovieTrailers(movieId, new ResponseCallback<List<Trailer>>() {
            @Override
            public void onSuccess(List<Trailer> result) {
                // Cache Trailers
                trailerRepository.insertTrailers(result);
            }

            @Override
            public void onError(String errMsg) {
                Log.e(TAG, "getMovieTrailers::onError: " + errMsg);
            }
        });
    }

    private void fetchReviews(int movieId) {
        onlineDataSource.getMovieReviews(movieId, new ResponseCallback<List<Review>>() {
            @Override
            public void onSuccess(List<Review> result) {
                // Cache reviews
                reviewRepository.insertReviews(result);
            }

            @Override
            public void onError(String errMsg) {
                Log.e(TAG, "getMovieReviews::onError: " + errMsg);
            }
        });
    }

    private void fetchTrailersAndReviewsAndCacheMovies(List<Movie> result) {
        movieRepository.insertMovies(result);
        // Fetch Trailers and Reviews for each movie
        for (Movie movie : result) {
            fetchTrailers(movie.getMovieId());
            fetchReviews(movie.getMovieId());
        }
    }

    public LiveData<List<Movie>> getPopularOrTopRatedMovies(boolean isTopRated) {
        initializeData();
        return movieRepository.getMovies(isTopRated);
    }

    public MovieRepository getMovieRepository() {
        return movieRepository;
    }

    public TrailerRepository getTrailerRepository() {
        return trailerRepository;
    }

    public ReviewRepository getReviewRepository() {
        return reviewRepository;
    }
}

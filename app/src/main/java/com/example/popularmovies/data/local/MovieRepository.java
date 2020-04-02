package com.example.popularmovies.data.local;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.popularmovies.data.model.Movie;
import com.example.popularmovies.data.model.MovieWithReviews;
import com.example.popularmovies.data.model.MovieWithTrailers;
import com.example.popularmovies.utils.AppExecutors;

import java.util.List;

public class MovieRepository {
    private static MovieRepository movieRepository;
    private static final Object LOCK = new Object();
    private final MovieDao movieDao;
    private final AppExecutors executors;

    private MovieRepository(MovieDao movieDao, AppExecutors executors) {
        this.movieDao = movieDao;
        this.executors = executors;
    }

    public static MovieRepository getInstance(MovieDao movieDao, AppExecutors executors) {
        if (movieRepository == null) {
            synchronized (LOCK) {
                movieRepository = new MovieRepository(movieDao, executors);
            }
        }
        return movieRepository;
    }

    public LiveData<List<Movie>> getMovies(boolean isTopRated) {
        return movieDao.getMovies(isTopRated);
    }

    public LiveData<List<Movie>> getFavoriteMovies(boolean isFavorite) {
        MutableLiveData<List<Movie>> movieLiveData = new MutableLiveData<>();
        executors.getDiskIO().execute(() -> {
            movieLiveData.postValue(movieDao.getFavoriteMovies(isFavorite).getValue());
        });
        return movieLiveData;
    }

    public LiveData<MovieWithTrailers> getMovieTrailers(int movieId) {
        return movieDao.getMovieTrailers(movieId);
    }

    public LiveData<MovieWithReviews> getMovieReviews(int movieId) {
        return movieDao.getMovieReviews(movieId);
    }

    public void insertMovies(List<Movie> movies) {
        movieDao.insert(movies);
    }

    public void deleteMovies(List<Movie> movies) {
        movieDao.delete(movies);
    }

    public void updateMovies(List<Movie> movies) {
        movieDao.update(movies);
    }

    public int getCount() {
        return movieDao.getCount();
    }

}

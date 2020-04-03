package com.example.popularmovies.ui.detail;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.popularmovies.data.Repository;
import com.example.popularmovies.data.model.Movie;
import com.example.popularmovies.data.model.MovieWithReviews;
import com.example.popularmovies.data.model.MovieWithTrailers;
import com.example.popularmovies.data.model.Trailer;

public class DetailActivityViewModel extends ViewModel {
    private final Repository repository;
    private MutableLiveData<Movie> mMovie;
    private LiveData<MovieWithTrailers> mTrailers;
    private LiveData<MovieWithReviews> mReviews;
    private Trailer mainTrailer;

    public DetailActivityViewModel(Repository repository) {
        this.repository = repository;
    }

    public void init(Movie movie) {
        if (mMovie == null) {
            mMovie = new MutableLiveData<>(movie);
            mTrailers = repository.getMovieRepository().getMovieTrailers(movie.getMovieId());
            mReviews = repository.getMovieRepository().getMovieReviews(movie.getMovieId());
        }
    }

    public void updateMovie(Movie movie) {
        repository.getMovieRepository().updateMovie(movie);
    }

    public LiveData<MovieWithTrailers> getTrailers(int movieId) {
        if (mTrailers == null || mTrailers.getValue() == null) {
            return repository.getMovieRepository().getMovieTrailers(movieId);
        }
        return mTrailers;
    }

    public LiveData<MovieWithReviews> getReviews(int movieId) {
        if (mReviews == null || mTrailers.getValue() == null)
            return repository.getMovieRepository().getMovieReviews(movieId);
        return mReviews;
    }

    public Trailer getMainTrailer() {
        return mainTrailer;
    }

    public void setMainTrailer(Trailer mainTrailer) {
        this.mainTrailer = mainTrailer;
    }
}

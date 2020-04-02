package com.example.popularmovies.ui.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.popularmovies.data.Repository;
import com.example.popularmovies.data.model.Movie;

import java.util.List;

public class MainActivityViewModel extends ViewModel {
    private Repository repository;
    private LiveData<List<Movie>> movieList = new MutableLiveData<>();

    MainActivityViewModel(Repository repository) {
        this.repository = repository;
    }

    public LiveData<List<Movie>> getMovieList() {
        return movieList;
    }

    public void fetchPopularOrTopRatedMovies(boolean isTopRated) {
        movieList = repository.getPopularOrTopRatedMovies(isTopRated);
    }

    public void fetchFavoriteMovies() {
        movieList = repository.getMovieRepository().getFavoriteMovies(true);
    }
}

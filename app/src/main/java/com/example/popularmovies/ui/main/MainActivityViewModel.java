package com.example.popularmovies.ui.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.popularmovies.data.Repository;
import com.example.popularmovies.data.model.Movie;

import java.util.List;

public class MainActivityViewModel extends ViewModel {
    private Repository repository;
    private MutableLiveData<List<Movie>> movieList = new MutableLiveData<>();

    MainActivityViewModel(Repository repository) {
        this.repository = repository;
    }

    public LiveData<List<Movie>> getMovieList() {
        return movieList;
    }

    public LiveData<List<Movie>> fetchPopularOrTopRatedMovies(boolean isTopRated) {
        return repository.getPopularOrTopRatedMovies(isTopRated);
    }

    public LiveData<List<Movie>> fetchFavoriteMovies() {
        return repository.getMovieRepository().getFavoriteMovies(true);
    }

    public void setMovieList(List<Movie> movies) {
        movieList.postValue(movies);
    }
}

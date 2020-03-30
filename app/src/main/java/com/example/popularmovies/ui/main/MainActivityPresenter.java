package com.example.popularmovies.ui.main;

import com.example.popularmovies.data.Repository;
import com.example.popularmovies.data.api.ResponseCallback;
import com.example.popularmovies.data.model.Movie;

import java.util.List;

public class MainActivityPresenter implements MainActivityContract.Presenter {
    private Repository repository;
    private MainActivityContract.View view;

    MainActivityPresenter(MainActivityContract.View view, Repository repository) {
        this.view = view;
        this.repository = repository;
    }

    @Override
    public void fetchPopularMovies() {
        repository.getMostPopularMovies(new ResponseCallback<List<Movie>>() {
            @Override
            public void onSuccess(List<Movie> result) {
                view.displayResults(result);
            }

            @Override
            public void onError(String errMsg) {
                view.showErrorMsg(errMsg);
            }
        });
    }

    @Override
    public void fetchTopRatedMovies() {
        repository.getTopRatedMovies(new ResponseCallback<List<Movie>>() {
            @Override
            public void onSuccess(List<Movie> result) {
                view.displayResults(result);
            }

            @Override
            public void onError(String errMsg) {
                view.showErrorMsg(errMsg);
            }
        });
    }
}

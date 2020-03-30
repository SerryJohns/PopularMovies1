package com.example.popularmovies.ui.main;

import com.example.popularmovies.data.model.Movie;

import java.util.List;

public interface MainActivityContract {
    interface View {
        void toggleProgress(boolean state);

        void showErrorMsg(String msg);

        void displayResults(List<Movie> movieList);
    }

    interface Presenter {
        void fetchPopularMovies();

        void fetchTopRatedMovies();
    }
}

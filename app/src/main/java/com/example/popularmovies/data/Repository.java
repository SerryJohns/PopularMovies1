package com.example.popularmovies.data;

import com.example.popularmovies.data.api.OnlineDataSource;
import com.example.popularmovies.data.api.OnlineRepository;
import com.example.popularmovies.data.api.ResponseCallback;
import com.example.popularmovies.data.model.Movie;

import java.util.List;

public class Repository implements OnlineRepository {
    private static Repository repository;
    private OnlineRepository onlineRepository;

    private Repository(OnlineRepository onlineRepository) {
        this.onlineRepository = onlineRepository;
    }

    synchronized public static Repository getInstance() {
        if (repository == null) {
            repository = new Repository(new OnlineDataSource());
        }
        return repository;
    }

    @Override
    public void getMostPopularMovies(ResponseCallback<List<Movie>> callback) {
        onlineRepository.getMostPopularMovies(callback);
    }

    @Override
    public void getTopRatedMovies(ResponseCallback<List<Movie>> callback) {
        onlineRepository.getTopRatedMovies(callback);
    }

}

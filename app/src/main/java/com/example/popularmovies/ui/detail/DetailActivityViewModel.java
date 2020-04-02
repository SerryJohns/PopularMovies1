package com.example.popularmovies.ui.detail;

import androidx.lifecycle.ViewModel;

import com.example.popularmovies.data.Repository;

public class DetailActivityViewModel extends ViewModel {
    private final Repository repository;

    public DetailActivityViewModel(Repository repository) {
        this.repository = repository;
    }
}

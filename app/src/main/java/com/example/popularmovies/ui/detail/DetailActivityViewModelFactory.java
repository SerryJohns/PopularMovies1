package com.example.popularmovies.ui.detail;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.popularmovies.data.Repository;

public class DetailActivityViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final Repository repository;

    public DetailActivityViewModelFactory(Repository repository) {
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new DetailActivityViewModel(repository);
    }
}

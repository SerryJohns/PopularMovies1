package com.example.popularmovies.data.api;

public interface ResponseCallback<T> {
    void onSuccess(T result);

    void onError(String errMsg);
}

package com.example.popularmovies.data.local;

import com.example.popularmovies.data.model.Trailer;

import java.util.List;

public class TrailerRepository {
    private static TrailerRepository trailerRepository;
    private static final Object LOCK = new Object();
    private final TrailerDao trailerDao;

    private TrailerRepository(TrailerDao trailerDao) {
        this.trailerDao = trailerDao;
    }

    public static TrailerRepository getInstance(TrailerDao trailerDao) {
        if (trailerRepository == null) {
            synchronized (LOCK) {
                trailerRepository = new TrailerRepository(trailerDao);
            }
        }
        return trailerRepository;
    }

    public void insertTrailers(List<Trailer> trailers) {
        trailerDao.insert(trailers);
    }

    public void deleteTrailers(List<Trailer> trailers) {
        trailerDao.delete(trailers);
    }
}

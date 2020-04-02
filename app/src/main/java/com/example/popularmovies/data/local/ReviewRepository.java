package com.example.popularmovies.data.local;

import com.example.popularmovies.data.model.Review;

import java.util.List;

public class ReviewRepository {
    private static ReviewRepository reviewRepository;
    private static final Object LOCK = new Object();
    private final ReviewDao reviewDao;

    private ReviewRepository(ReviewDao reviewDao) {
        this.reviewDao = reviewDao;
    }

    public static ReviewRepository getInstance(ReviewDao trailerDao) {
        if (reviewRepository == null) {
            synchronized (LOCK) {
                reviewRepository = new ReviewRepository(trailerDao);
            }
        }
        return reviewRepository;
    }

    public void insertReviews(List<Review> reviews) {
        reviewDao.insert(reviews);
    }

    public void deleteReviews(List<Review> reviews) {
        reviewDao.delete(reviews);
    }
}

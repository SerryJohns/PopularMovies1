package com.example.popularmovies.utils;

import com.example.popularmovies.data.model.Movie;
import com.example.popularmovies.data.model.Review;
import com.example.popularmovies.data.model.Trailer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class JsonUtils {
    // Movie Constants
    private static final String RESULTS = "results";
    private static final String ORIGINAL_TITLE = "original_title";
    private static final String RELEASE_DATE = "release_date";
    private static final String POSTER_PATH = "poster_path";
    private static final String OVERVIEW = "overview";
    private static final String VOTE_AVERAGE = "vote_average";
    private static final String MOVIE_ID = "id";

    // Trailer Constants
    private static final String KEY = "key";
    private static final String NAME = "name";
    private static final String TYPE = "type";

    // Review Constants
    private static final String AUTHOR = "author";
    private static final String CONTENT = "content";

    public static List<Movie> parseMovieList(String result) {
        List<Movie> movieList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray resultsArray = jsonObject.getJSONArray(RESULTS);

            for (int i = 0; i < resultsArray.length(); i++) {
                JSONObject movieObj = (JSONObject) resultsArray.get(i);
                Integer ID = movieObj.getInt(MOVIE_ID);
                String title = movieObj.getString(ORIGINAL_TITLE);
                String releaseDate = movieObj.getString(RELEASE_DATE);
                String posterPath = movieObj.getString(POSTER_PATH);
                String overview = movieObj.getString(OVERVIEW);
                Double voteAverage = movieObj.getDouble(VOTE_AVERAGE);
                LocalDate date = LocalDate.parse(releaseDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

                Movie movie = new Movie(ID, title, date, posterPath, overview, voteAverage);
                movieList.add(movie);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return movieList;
    }

    public static List<Trailer> parseMovieTrailersList(String result) {
        List<Trailer> trailerList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray resultsArray = jsonObject.getJSONArray(RESULTS);

            for (int i = 0; i < resultsArray.length(); i++) {
                JSONObject trailerObj = (JSONObject) resultsArray.get(i);
                String key = trailerObj.getString(KEY);
                String name = trailerObj.getString(NAME);
                String type = trailerObj.getString(TYPE);

                Trailer trailer = new Trailer(key, name, type);
                trailerList.add(trailer);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return trailerList;
    }

    public static List<Review> parseMovieReviewList(String result) {
        List<Review> reviewList = new ArrayList<>();
        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray resultsArray = jsonObject.getJSONArray(RESULTS);

            for (int i = 0; i < resultsArray.length(); i++) {
                JSONObject reviewObj = (JSONObject) resultsArray.get(i);
                String author = reviewObj.getString(AUTHOR);
                String content = reviewObj.getString(CONTENT);

                Review review = new Review(author, content);
                reviewList.add(review);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return reviewList;
    }
}

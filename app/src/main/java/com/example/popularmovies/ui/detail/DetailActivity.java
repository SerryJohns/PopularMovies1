package com.example.popularmovies.ui.detail;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popularmovies.R;
import com.example.popularmovies.data.Movie;
import com.example.popularmovies.ui.main.MainActivity;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    private ImageView thumbnail;
    private TextView title;
    private TextView releaseYear;
    private TextView rating;
    private Button btnFavourite;
    private TextView plotSynopsis;
    private RecyclerView trailersRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
            return;
        }

        Movie movie = intent.getParcelableExtra(MainActivity.MOVIE_EXTRA);
        if (movie == null) {
            closeOnError();
            return;
        }

        ActionBar appbar = getSupportActionBar();
        if (appbar != null) {
            appbar.setTitle(this.getString(R.string.movie_detail_txt));
            appbar.setDisplayHomeAsUpEnabled(true);
        }

        initViews();
        showMovieDetails(movie);
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, getString(R.string.detail_error_message), Toast.LENGTH_SHORT).show();
    }

    private void initViews() {
        thumbnail = (ImageView) findViewById(R.id.thumbnail_iv);
        title = (TextView) findViewById(R.id.movie_title_tv);
        releaseYear = (TextView) findViewById(R.id.year_of_release_tv);
        rating = (TextView) findViewById(R.id.ratings_tv);
        btnFavourite = (Button) findViewById(R.id.btn_favorite);
        plotSynopsis = (TextView) findViewById(R.id.plot_synopsis_tv);
        trailersRecyclerView = (RecyclerView) findViewById(R.id.trailers_recycler_view);
    }

    private void showMovieDetails(Movie movie) {
        Picasso.get()
                .load(movie.getPosterPath())
                .placeholder(R.drawable.placeholder)
                .into(thumbnail);
        title.setText(movie.getTitle());
        releaseYear.setText(getString(R.string.release_year_txt, movie.getReleaseDate().getYear()));
        rating.setText(getString(R.string.rating_txt, movie.getVoteAverage().toString()));
        plotSynopsis.setText(movie.getOverview());

        btnFavourite.setOnClickListener(v -> Toast.makeText(this, "Not implemented", Toast.LENGTH_SHORT).show());
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}

package com.example.popularmovies.ui.detail;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.core.app.ShareCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popularmovies.R;
import com.example.popularmovies.data.model.Movie;
import com.example.popularmovies.data.model.Trailer;
import com.example.popularmovies.ui.main.MainActivity;
import com.example.popularmovies.utils.InjectorUtils;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    private ImageView thumbnail;
    private TextView title;
    private TextView releaseYear;
    private TextView rating;
    private ImageView imgFavourite;
    private ImageView imgShare;
    private TextView plotSynopsis;
    private DetailActivityViewModel viewModel;
    private Movie mMovie;
    private TrailersAdapter trailersAdapter;
    private ReviewsAdapter reviewsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        viewModel = new ViewModelProvider(
                this,
                InjectorUtils.provideDetailActivityViewModelFactory(getApplicationContext())
        ).get(DetailActivityViewModel.class);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
            return;
        }

        mMovie = intent.getParcelableExtra(MainActivity.MOVIE_EXTRA);
        if (mMovie == null) {
            closeOnError();
            return;
        }

        ActionBar appbar = getSupportActionBar();
        if (appbar != null) {
            appbar.setTitle(this.getString(R.string.movie_detail_txt));
            appbar.setDisplayHomeAsUpEnabled(true);
        }

        initViews();
        initListeners();

        viewModel.init(mMovie);
        showMovieDetails(mMovie);
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
        imgFavourite = (ImageView) findViewById(R.id.btn_favorite);
        imgShare = (ImageView) findViewById(R.id.btn_share);
        plotSynopsis = (TextView) findViewById(R.id.plot_synopsis_tv);
        RecyclerView trailersRecyclerView = (RecyclerView) findViewById(R.id.trailers_recycler_view);
        RecyclerView reviewsRecyclerView = (RecyclerView) findViewById(R.id.movie_reviews);

        // Initialize adapters
        trailersAdapter = new TrailersAdapter();
        trailersAdapter.setItemClickListener(this::launchYoutubeIntent);
        trailersRecyclerView.setAdapter(trailersAdapter);

        reviewsAdapter = new ReviewsAdapter();
        reviewsAdapter.setBindAuthorName(this::setAuthorString);
        reviewsRecyclerView.setAdapter(reviewsAdapter);
    }

    private void launchYoutubeIntent(Trailer trailer) {
        Intent youTubeIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(trailer.getTrailerPath()));
        try {
            startActivity(youTubeIntent);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, getString(R.string.err_launch_player), Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    private void initListeners() {
        imgFavourite.setOnClickListener(v -> {
            // Mark Movie as favorite and update the movie object
            boolean newStatus = !mMovie.isFavorite();
            showAsFavorite(newStatus);

            mMovie.setFavorite(!mMovie.isFavorite());
            viewModel.updateMovie(mMovie);

            if (newStatus) {
                Toast.makeText(this, getString(R.string.txt_marked_as_favorite), Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getString(R.string.txt_un_marked_as_favorite), Toast.LENGTH_SHORT).show();
            }
        });

        imgShare.setOnClickListener(v -> {
            String mimeType = "text/plain";
            String title = mMovie.getTitle();
            String textToShare = viewModel.getMainTrailer().getTrailerPath();

            ShareCompat.IntentBuilder.from(this)
                    .setType(mimeType)
                    .setChooserTitle(title)
                    .setText(textToShare)
                    .startChooser();
        });

        viewModel.getTrailers(mMovie.getMovieId()).observe(this,
                movieWithTrailers -> {
                    trailersAdapter.setTrailerList(movieWithTrailers.trailers);

                    if (movieWithTrailers.trailers != null && movieWithTrailers.trailers.size() > 0) {
                        viewModel.setMainTrailer(movieWithTrailers.trailers.get(0));
                    }
                });

        viewModel.getReviews(mMovie.getMovieId()).observe(this, movieWithReviews -> {
            reviewsAdapter.setReviewList(movieWithReviews.reviews);
            if (movieWithReviews.reviews == null || movieWithReviews.reviews.size() == 0) {
                ((TextView) findViewById(R.id.reviews_label)).setVisibility(View.GONE);
            }
        });
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
        showAsFavorite(movie.isFavorite());
    }

    private void showAsFavorite(boolean isFavorite) {
        if (isFavorite) {
            imgFavourite.setImageResource(R.drawable.ic_star_orange_24dp);
        } else {
            imgFavourite.setImageResource(R.drawable.ic_star_border_black_24dp);
        }
    }

    private void setAuthorString(TextView authorTv, String authorName) {
        authorTv.setText(getString(R.string.written_by_txt, authorName));
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }
}

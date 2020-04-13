package com.example.popularmovies.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.moviedata.HelloWorld;
import com.example.popularmovies.R;
import com.example.popularmovies.ui.detail.DetailActivity;
import com.example.popularmovies.utils.InjectorUtils;

public class MainActivity extends AppCompatActivity {
    public static final String MOVIE_EXTRA = "MOVIE_EXTRA";
    private MoviesAdapter moviesAdapter;
    private ProgressBar progressBar;
    private TextView errMsg;

    private MainActivityViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = new ViewModelProvider(
                this,
                InjectorUtils.provideMainActivityViewModelFactory(getApplicationContext())
        ).get(MainActivityViewModel.class);

        initUI();
        initMovieListListener();

        if (savedInstanceState == null) {
            // Perform initial load of data
            fetchTopRatedMovies(false);
        }
        String greeting = HelloWorld.greeting();
        Toast.makeText(this, greeting, Toast.LENGTH_SHORT).show();
    }

    private void initUI() {
        // Initialize AppBar
        ActionBar appBar = getSupportActionBar();
        if (appBar != null) {
            appBar.setTitle(this.getString(R.string.pop_movies_txt));
        }

        RecyclerView moviesRecyclerView = (RecyclerView) findViewById(R.id.movies_recycler_view);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        errMsg = (TextView) findViewById(R.id.msg_tv);

        RecyclerView.LayoutManager gridLayoutManager = new GridLayoutManager(this, numOfColumns());
        moviesRecyclerView.setLayoutManager(gridLayoutManager);

        moviesAdapter = new MoviesAdapter();
        moviesRecyclerView.setAdapter(moviesAdapter);

        moviesAdapter.setListener(movie -> {
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra(MOVIE_EXTRA, movie);
            startActivity(intent);
        });
    }

    private int numOfColumns() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int widthDivider = 600;
        int width = displayMetrics.widthPixels;
        int nColumns = width / widthDivider;
        return Math.max(nColumns, 2);
    }

    private void initMovieListListener() {
        viewModel.getMovieList().observe(this, movieList -> {
            toggleProgress(false);

            if (movieList.size() == 0) {
                errMsg.setVisibility(View.VISIBLE);
                errMsg.setText(getString(R.string.msg_no_content));
            } else {
                errMsg.setVisibility(View.GONE);
            }

            moviesAdapter.setMovieList(movieList);
        });
    }

    private void fetchTopRatedMovies(boolean isTopRated) {
        viewModel.fetchPopularOrTopRatedMovies(isTopRated).observe(this, movieList -> {
            viewModel.setMovieList(movieList);
        });
    }

    private void fetchFavoriteMovies() {
        viewModel.fetchFavoriteMovies().observe(this, movieList -> {
            viewModel.setMovieList(movieList);
        });
    }

    public void toggleProgress(boolean state) {
        progressBar.setVisibility(state ? View.VISIBLE : View.GONE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.movies_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.most_popular:
                toggleProgress(true);
                fetchTopRatedMovies(false);
                return true;
            case R.id.top_rated:
                toggleProgress(true);
                fetchTopRatedMovies(true);
                return true;
            case R.id.favorites:
                toggleProgress(true);
                fetchFavoriteMovies();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

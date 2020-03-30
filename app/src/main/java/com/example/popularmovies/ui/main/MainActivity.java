package com.example.popularmovies.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popularmovies.R;
import com.example.popularmovies.data.Repository;
import com.example.popularmovies.data.model.Movie;
import com.example.popularmovies.ui.detail.DetailActivity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MainActivityContract.View {
    public static final String MOVIE_EXTRA = "MOVIE_EXTRA";
    private static final String SAVED_MOVIES_STATE = "SAVED_MOVIES_STATE";
    private MoviesAdapter moviesAdapter;
    private ProgressBar progressBar;
    private TextView errMsg;
    private MainActivityPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new MainActivityPresenter(this, Repository.getInstance());

        initUI();
        if (savedInstanceState != null) {
            List<Movie> movieList = savedInstanceState.getParcelableArrayList(SAVED_MOVIES_STATE);
            moviesAdapter.setMovieList(movieList);
        } else {
            toggleProgress(true);
            presenter.fetchPopularMovies();
        }
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

        RecyclerView.LayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        moviesRecyclerView.setLayoutManager(gridLayoutManager);

        moviesAdapter = new MoviesAdapter();
        moviesRecyclerView.setAdapter(moviesAdapter);

        moviesAdapter.setListener(movie -> {
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra(MOVIE_EXTRA, movie);
            startActivity(intent);
        });
    }

    @Override
    public void toggleProgress(boolean state) {
        progressBar.setVisibility(state ? View.VISIBLE : View.GONE);
    }

    @Override
    public void showErrorMsg(String msg) {
        runOnUiThread(() -> {
            toggleProgress(false);
            errMsg.setText(msg);
        });
    }

    @Override
    public void displayResults(List<Movie> movieList) {
        runOnUiThread(() -> {
            moviesAdapter.setMovieList(movieList);
            toggleProgress(false);
            showErrorMsg(null);
        });
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(SAVED_MOVIES_STATE, new ArrayList<>(moviesAdapter.getMovieList()));
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
                presenter.fetchPopularMovies();
                return true;
            case R.id.top_rated:
                toggleProgress(true);
                presenter.fetchTopRatedMovies();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

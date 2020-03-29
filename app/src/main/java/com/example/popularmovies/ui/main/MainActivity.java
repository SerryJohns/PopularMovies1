package com.example.popularmovies.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popularmovies.R;
import com.example.popularmovies.data.Repository;
import com.example.popularmovies.ui.detail.DetailActivity;

public class MainActivity extends AppCompatActivity {
    public static final String MOVIE_EXTRA = "MOVIE_EXTRA";
    private RecyclerView moviesRecyclerView;
    private MoviesAdapter moviesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize AppBar
        ActionBar appBar = getSupportActionBar();
        if (appBar != null) {
            appBar.setTitle(this.getString(R.string.pop_movies_txt));
        }

        moviesRecyclerView = (RecyclerView) findViewById(R.id.movies_recycler_view);

        initUI();
    }

    private void initUI() {
        RecyclerView.LayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        moviesRecyclerView.setLayoutManager(gridLayoutManager);

        moviesAdapter = new MoviesAdapter();
        moviesRecyclerView.setAdapter(moviesAdapter);

        moviesAdapter.setMovieList(Repository.getDummyMovies());
        moviesAdapter.setListener(movie -> {
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra(MOVIE_EXTRA, movie);
            startActivity(intent);
        });
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
                // sort by most popular
                return true;
            case R.id.top_rated:
                // sort by top rated
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

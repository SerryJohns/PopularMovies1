package com.example.popularmovies.ui.main;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popularmovies.R;
import com.example.popularmovies.data.model.Movie;
import com.example.popularmovies.utils.ItemClickListener;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder> {
    private List<Movie> movieList = Collections.emptyList();
    private ItemClickListener<Movie> listener;

    public MoviesAdapter() {
    }

    @NonNull
    @Override
    public MoviesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviesAdapter.ViewHolder holder, int position) {
        holder.bind(movieList.get(position));
    }

    @Override
    public int getItemCount() {
        return movieList != null ? movieList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private View itemView;
        private ImageView moviePoster;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            moviePoster = itemView.findViewById(R.id.movie_iv);
        }

        public void bind(Movie movie) {
            Picasso.get()
                    .load(movie.getPosterPath())
                    .placeholder(R.drawable.placeholder)
                    .into(moviePoster);
            itemView.setOnClickListener(v -> listener.onItemClick(movie));
        }
    }

    public void setListener(ItemClickListener<Movie> listener) {
        this.listener = listener;
    }

    public void setMovieList(List<Movie> movieList) {
        this.movieList = movieList;
        this.notifyDataSetChanged();
    }
}

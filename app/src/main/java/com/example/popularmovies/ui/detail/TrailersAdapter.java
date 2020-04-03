package com.example.popularmovies.ui.detail;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popularmovies.R;
import com.example.popularmovies.data.model.Trailer;
import com.example.popularmovies.utils.ItemClickListener;

import java.util.Collections;
import java.util.List;

public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.ViewHolder> {
    private List<Trailer> trailerList = Collections.emptyList();
    private ItemClickListener<Trailer> itemClickListener;

    public TrailersAdapter() {
    }

    @NonNull
    @Override
    public TrailersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trailer_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailersAdapter.ViewHolder holder, int position) {
        holder.bind(trailerList.get(position));
    }

    @Override
    public int getItemCount() {
        return trailerList != null ? trailerList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ConstraintLayout trailerLayout;
        private TextView trailerTitle;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            trailerLayout = (ConstraintLayout) itemView.findViewById(R.id.movie_trailer_layout);
            trailerTitle = (TextView) itemView.findViewById(R.id.trailer_title_tv);
        }

        public void bind(Trailer trailer) {
            trailerTitle.setText(trailer.getName());
            trailerLayout.setOnClickListener(v -> itemClickListener.onItemClick(trailer));
        }
    }

    public void setItemClickListener(ItemClickListener<Trailer> itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void setTrailerList(List<Trailer> trailerList) {
        this.trailerList = trailerList;
        this.notifyDataSetChanged();
    }
}

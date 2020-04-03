package com.example.popularmovies.ui.detail;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popularmovies.R;
import com.example.popularmovies.data.model.Review;

import java.util.Collections;
import java.util.List;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ViewHolder> {
    private List<Review> reviewList = Collections.emptyList();
    private BindAuthorName bindAuthorName;

    @NonNull
    @Override
    public ReviewsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_reviews_item_layout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewsAdapter.ViewHolder holder, int position) {
        holder.bind(reviewList.get(position));
    }

    @Override
    public int getItemCount() {
        return reviewList != null ? reviewList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView authorTv;
        private TextView reviewTv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            authorTv = (TextView) itemView.findViewById(R.id.author_tv);
            reviewTv = (TextView) itemView.findViewById(R.id.review_tv);
        }

        public void bind(Review review) {
            reviewTv.setText(review.getContent());
            bindAuthorName.setAuthorName(authorTv, review.getAuthor());
        }
    }

    public void setReviewList(List<Review> reviewList) {
        this.reviewList = reviewList;
        this.notifyDataSetChanged();
    }

    public void setBindAuthorName(BindAuthorName bindAuthorName) {
        this.bindAuthorName = bindAuthorName;
    }

    public interface BindAuthorName {
        void setAuthorName(TextView textView, String name);
    }
}

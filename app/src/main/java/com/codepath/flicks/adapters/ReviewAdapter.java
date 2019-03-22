package com.codepath.flicks.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codepath.flicks.R;
import com.codepath.flicks.models.Review;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {

    Context context;
    List<Review> reviews;

    public ReviewAdapter(Context context, List<Review> reviews) {
        this.context = context;
        this.reviews = reviews;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Log.d("smile ", "onCreateViewHolder");
        View view = LayoutInflater.from(context).inflate(R.layout.review_movie, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Log.d("smile ", "onBindViewHolder: " + i);
        Review review = reviews.get(i);
        viewHolder.setData(review);
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView tvAuthor;
        TextView tvReview;
        TextView reviewView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAuthor= itemView.findViewById(R.id.tvAuthor);
            tvReview = itemView.findViewById(R.id.tvReview);
            reviewView = itemView.findViewById(R.id.reviewView);
        }

        public void setData(final Review review) {
            tvAuthor.setText(review.getAuthor());
            tvReview.setText(review.getContent());
        }
    }
}



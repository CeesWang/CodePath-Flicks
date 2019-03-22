package com.codepath.flicks.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.codepath.flicks.DetailActivity;
import com.codepath.flicks.R;
import com.codepath.flicks.models.Movie;

import org.parceler.Parcels;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.ViewHolder>{

    Context context;
    List<Movie> movies;

    public MoviesAdapter(Context context, List<Movie> movies) {
        this.context = context;
        this.movies = movies;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Log.d("smile ", "onCreateViewHolder");
        View view = LayoutInflater.from(context).inflate(R.layout.item_movie, viewGroup, false );
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Log.d("smile ", "onBindViewHolder: " + i);
        Movie movie = movies.get(i);
        viewHolder.setData(movie);
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tvAuthor)TextView tvTitle;
        @BindView(R.id.tvOverview)TextView tvOverview;
        @BindView(R.id.ivPoster)ImageView ivPoster;
        @BindView (R.id.container) RelativeLayout container;

        public ViewHolder(View itemView) {
            super(itemView);
         //   container = itemView.findViewById(R.id.container);
            ButterKnife.bind(this,itemView);
        }

        public void setData(final Movie movie) {
            tvTitle.setText(movie.getTitle());
            String imageUrl = movie.getPosterPath();
            tvOverview.setText(movie.getOverview());
            if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
                imageUrl = movie.getBackdropPath();
            //Glide.with(context).load(imageUrl).into(ivPoster);
            Glide.with(context).load(imageUrl).apply(new RequestOptions().circleCrop().transform((new RoundedCornersTransformation(25,0)))).into(ivPoster);
            container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(context, DetailActivity.class);
                    i.putExtra("movie", Parcels.wrap(movie));
                    context.startActivity(i);
                }
            });
        }
    }
}

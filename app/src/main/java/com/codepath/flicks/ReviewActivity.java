package com.codepath.flicks;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.codepath.flicks.adapters.ReviewAdapter;
import com.codepath.flicks.models.Review;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import cz.msebera.android.httpclient.Header;

public class ReviewActivity extends AppCompatActivity {
    private static String review_url = "https://api.themoviedb.org/3/movie/%d/reviews?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    List<Review> reviews;
    @BindView(R.id.rvReview) RecyclerView rvReview;
    ReviewAdapter adapter;
    AsyncHttpClient client;
    int movieId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        getSupportActionBar().setTitle("User Reviews");
        ButterKnife.bind(this);
        reviews = new ArrayList<>();
        adapter = new ReviewAdapter(this,reviews);
        rvReview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rvReview.setAdapter(adapter);

        movieId = getIntent().getIntExtra("movie",0);
        client = new AsyncHttpClient();

        client.get(String.format(review_url, movieId), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("results");
                    if (jsonArray.length() == 0) {
                        Review rw= new Review();
                        rw.setContent("No Reviews");
                        reviews.add(rw);
                    }
                    else
                        reviews.addAll(Review.fromJsonArray(jsonArray));
                    adapter.notifyDataSetChanged();
                    Log.d("smile", reviews.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }


        });
    }
}

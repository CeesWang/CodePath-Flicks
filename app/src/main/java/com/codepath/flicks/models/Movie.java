package com.codepath.flicks.models;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cz.msebera.android.httpclient.Header;

@Parcel
public class Movie {
    public static final String GENRES = "https://api.themoviedb.org/3/genre/movie/list?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed&language=en-US";
    HashMap<Integer,String > map_genres;
    String posterPath;
    String title;
    String overview;
    String backdropPath;
    StringBuilder genres;
    String releaseDate;
    double rating;
    int movieId;
    boolean isAdult;

    // empty constructor needed by the Parceler library
    public Movie() {

    }
    public Movie(JSONObject jsonObject) throws JSONException {
        map_genres = new HashMap<>();
        genres = new StringBuilder();
        posterPath = jsonObject.getString("poster_path");
        title = jsonObject.getString("title");
        overview = jsonObject.getString("overview");
        backdropPath = jsonObject.getString("backdrop_path");
        rating = jsonObject.getDouble("vote_average");
        movieId = jsonObject.getInt("id");
        releaseDate = jsonObject.getString("release_date");
        isAdult = jsonObject.getBoolean("adult");
        setGenres(jsonObject);
    }

    public void setGenres (final JSONObject jsonObject) throws JSONException {
        //request to get genres
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(GENRES, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    JSONArray jsonArray = jsonObject.getJSONArray("genre_ids");
                    JSONArray results = response.getJSONArray("genres");
                    for (int i = 0; i < results.length(); ++i) {
                        map_genres.put(results.getJSONObject(i).getInt("id"), results.getJSONObject(i).getString("name"));
                    }
                    for (int i = 0; i < jsonArray.length(); ++i) {
                        if (i == jsonArray.length()-1)
                            genres.append(map_genres.get(jsonArray.getInt(i)));
                        else
                            genres.append(map_genres.get(jsonArray.getInt(i)) + ", ");

                    }

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

    public static List<Movie> fromJsonArray (JSONArray jsonArray)throws JSONException  {
        List<Movie> movies = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); ++i) {
            movies.add(new Movie(jsonArray.getJSONObject(i)));
        }
        return movies;
    }


    public String getPosterPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", posterPath);
    }
    public String getBackdropPath() {
        return String.format("https://image.tmdb.org/t/p/w342/%s", backdropPath);
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public double getRating() {
        return rating;
    }
    public int getMovieId() {
        return movieId;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public String getGenres() {
        return genres.toString();
    }
    public String getAdult() {
        if (isAdult)
            return "R";
        else
            return "PG-13";
    }
}

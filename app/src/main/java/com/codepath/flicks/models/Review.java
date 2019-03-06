package com.codepath.flicks.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

@Parcel
public class Review {
    String author;
    String content;
    // empty constructor needed by the Parceler library
    public Review() {

    }
    public Review(JSONObject jsonObject) throws JSONException {
        author = jsonObject.getString("author");
        content = jsonObject.getString("content");
    }

    public static List<Review> fromJsonArray (JSONArray jsonArray)throws JSONException  {
        List<Review> reviews = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); ++i) {
            reviews.add(new Review(jsonArray.getJSONObject(i)));
        }
        return reviews;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }
}

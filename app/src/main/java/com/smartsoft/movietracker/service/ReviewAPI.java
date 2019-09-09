package com.smartsoft.movietracker.service;

import com.smartsoft.movietracker.model.review.ReviewResult;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ReviewAPI {

    @GET("movie/{movie_id}/reviews")
    Call<ReviewResult> getReviews(@Path("movie_id") int movie_id, @Query("api_key") String api_key);
}

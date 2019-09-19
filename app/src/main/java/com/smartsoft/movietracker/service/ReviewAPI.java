package com.smartsoft.movietracker.service;

import com.smartsoft.movietracker.model.review.ReviewResult;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ReviewAPI {

    @GET("movie/{movie_id}/reviews")
    Observable<ReviewResult> getReviews(@Path("movie_id") int movie_id, @Query("api_key") String api_key);
}

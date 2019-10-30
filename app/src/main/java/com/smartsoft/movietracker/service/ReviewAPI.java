package com.smartsoft.movietracker.service;

import com.smartsoft.movietracker.model.review.ReviewResult;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * This interface contains the @GET method of the {@link com.smartsoft.movietracker.model.review.ReviewResult}
 */
public interface ReviewAPI {

    /**
     * This function create the query and download all the review for the current movie
     * with movieId same as
     * @param movie_id (Unique key / Identifier key)
     * @param api_key (This key in every method needed.
     *                  The API doesn't send anything if this key isn't real)
     * @return Observable<ReviewResult> object
     */
    @GET("movie/{movie_id}/reviews")
    Observable<ReviewResult> getReviews(@Path("movie_id") int movie_id,
                                        @Query("api_key") String api_key);
}

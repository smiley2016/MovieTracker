package com.smartsoft.movietracker.service;

import com.smartsoft.movietracker.model.cast.CastResult;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * This interface contains the @GET method of the {@link com.smartsoft.movietracker.model.cast.Cast}
 */

public interface CastAPI {

    /**
     * This function execute the query and download all the Cast members from API
     * for the movies that has the same unique ID like
     * @param movie_id (Unique ID)
     * @param apiKey (This key in every method needed.
     *               The API doesn't send anything if this key isn't real)
     * @return Observable<Response<CastResult>> object.
     */
    @GET("movie/{movie_id}/credits")
    Observable<Response<CastResult>> getCast(@Path("movie_id") int movie_id,
                                             @Query("api_key") String apiKey);
}

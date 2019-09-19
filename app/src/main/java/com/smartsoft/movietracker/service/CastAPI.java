package com.smartsoft.movietracker.service;

import com.smartsoft.movietracker.model.cast.CastResult;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CastAPI {

    @GET("movie/{movie_id}/credits")
    Observable<Response<CastResult>> getCast(@Path("movie_id") int movie_id,
                                 @Query("api_key") String apiKey);
}

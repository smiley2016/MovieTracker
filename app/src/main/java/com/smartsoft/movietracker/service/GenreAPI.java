package com.smartsoft.movietracker.service;

import com.smartsoft.movietracker.model.genre.GenreResult;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GenreAPI {


    @GET("genre/movie/list")
    Observable<Response<GenreResult>> getGenres(@Query("api_key") String apiKey, @Query("language") String language);
}

package com.smartsoft.movietracker.service;

import com.smartsoft.movietracker.model.genre.GenreResult;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * This interface contains the @GET method of the {@link com.smartsoft.movietracker.model.genre.GenreResult}
 */

public interface GenreAPI {

    /**
     * This function execute the query and download all the genre in
     * @param language (This variable say the API in which language want to download the
     *                 {@link com.smartsoft.movietracker.model.genre.Genre})
     * @param apiKey (This key in every method needed.
     *               The API doesn't send anything if this key isn't real)
     * @return Observable<Response<GenreResult>> object
     */
    @GET("genre/movie/list")
    Observable<Response<GenreResult>> getGenres(@Query("api_key") String apiKey, @Query("language") String language);
}

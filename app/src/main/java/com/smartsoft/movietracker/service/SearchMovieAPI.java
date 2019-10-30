package com.smartsoft.movietracker.service;

import com.smartsoft.movietracker.model.movie.MovieResult;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface SearchMovieAPI {

    @GET("search/movie")
    Observable<Response<MovieResult>> getSearchedMoviesByString(@Query("api_key") String apiKey,
                                                                @Query("query") String keyword,
                                                                @Query("include_adult") Boolean includeAdult,
                                                                @Query("page") int page);
}

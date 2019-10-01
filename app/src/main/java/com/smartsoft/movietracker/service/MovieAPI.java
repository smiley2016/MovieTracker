package com.smartsoft.movietracker.service;

import com.smartsoft.movietracker.model.movie.MovieResult;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieAPI {

    @GET("discover/movie")
    Observable<Response<MovieResult>> getMovies(@Query("api_key") String apiKey,
                                                @Query("language") String language,
                                                @Query("sort_by") String sortBy,
                                                @Query("include_adult") boolean control,
                                                @Query("include_video") boolean isVideo,
                                                @Query("page") int page,
                                                @Query("with_genres") String genreIds);
}

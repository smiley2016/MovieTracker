package com.smartsoft.movietracker.service;

import com.smartsoft.movietracker.model.movie.MovieResult;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieAPI {

    @GET("discover/movie")
    Call<MovieResult> getMovies(@Query("api_key") String apiKey,
                                @Query("language") String language,
                                @Query("sort_by") String sortBy,
                                @Query("include_adult") boolean control,
                                @Query("include_video") boolean isVideo,
                                @Query("page") int page,
                                @Query("with_genres") ArrayList<Integer> genIds);
}

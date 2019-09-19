package com.smartsoft.movietracker.service;

import com.smartsoft.movietracker.model.genre.GenreResult;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;

public interface GenreAPI {



    @GET("genre/movie/list?api_key=221aac16f7f58f1cf0fd5f99ff6e6b60&language=en-US")
    Observable<Response<GenreResult>> getGenres();
}

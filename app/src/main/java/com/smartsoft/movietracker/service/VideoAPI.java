package com.smartsoft.movietracker.service;

import com.smartsoft.movietracker.model.video.VideoResult;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface VideoAPI {

    @GET("movie/{movie_id}/videos")
    Observable<VideoResult> getVideos(@Path("movie_id") int movie_id, @Query("api_key") String api_key);
}

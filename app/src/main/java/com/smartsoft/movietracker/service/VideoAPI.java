package com.smartsoft.movietracker.service;

import com.smartsoft.movietracker.model.video.VideoResult;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * This interface contains the @GET method of the {@link com.smartsoft.movietracker.model.video.VideoResult}
 */
public interface VideoAPI {

    /**
     * create the query and download all the videos for the current movie with
     * @param movie_id (unique key)
     * @param api_key (This key in every method needed.
     *                  The API doesn't send anything if this key isn't real)
     * @return Observable<VideoResult>
     */
    @GET("movie/{movie_id}/videos")
    Observable<VideoResult> getVideos(@Path("movie_id") int movie_id,
                                      @Query("api_key") String api_key);
}

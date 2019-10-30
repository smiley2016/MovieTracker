package com.smartsoft.movietracker.service;

import com.smartsoft.movietracker.model.movie.MovieResult;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * This interface contains the @GET method of the {@link com.smartsoft.movietracker.model.movie.MovieResult}
 */
public interface MovieAPI {

    /**
     * This function create the query and download all the movies which
     * have the following next attributes:
     * @param language in which language spoke in the film
     * @param sortBy  in which sort (popularity, title, release date, vote average)
     * @param adultContent download adultContent too or not
     * @param isVideo Movie has videos or not
     * @param page the next page
     * @param genreIds (download movies that has these genreIds)
     * @param apiKey (This key in every method needed.
     *                  The API doesn't send anything if this key isn't real)
     * @return Observable<Response<MovieResult>> object
     */
    @GET("discover/movie")
    Observable<Response<MovieResult>> getMovies(@Query("api_key") String apiKey,
                                                @Query("language") String language,
                                                @Query("sort_by") String sortBy,
                                                @Query("include_adult") boolean adultContent,
                                                @Query("include_video") boolean isVideo,
                                                @Query("page") int page,
                                                @Query("with_genres") String genreIds);

}

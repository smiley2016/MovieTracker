package com.smartsoft.movietracker.service;

import com.smartsoft.movietracker.model.movie.MovieResult;

import io.reactivex.Observable;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
* This interface contains the @GET method of the {@link com.smartsoft.movietracker.model.movie.MovieResult}
*/
public interface SearchMovieAPI {

    /**
     * This function create the query and download all the movies which we want to search
     * and has the following next attributes:
     * @param keyword (searched text)
     * @param includeAdult (Adult content )
     * @param page next page
     * @param apiKey (This key in every method needed.
     *               The API doesn't send anything if this key isn't real)
     * @return Observable<Response<MovieResult>> object
     */
    @GET("search/movie")
    Observable<Response<MovieResult>> getSearchedMoviesByString(@Query("api_key") String apiKey,
                                                                @Query("query") String keyword,
                                                                @Query("include_adult") Boolean includeAdult,
                                                                @Query("page") int page);
}

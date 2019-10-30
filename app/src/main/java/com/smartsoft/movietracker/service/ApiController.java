package com.smartsoft.movietracker.service;

import android.content.Context;
import android.util.Log;

import com.smartsoft.movietracker.R;
import com.smartsoft.movietracker.model.cast.Cast;
import com.smartsoft.movietracker.model.genre.Genre;
import com.smartsoft.movietracker.model.movie.MovieResult;
import com.smartsoft.movietracker.model.review.Review;
import com.smartsoft.movietracker.model.review.ReviewResult;
import com.smartsoft.movietracker.model.video.Video;
import com.smartsoft.movietracker.model.video.VideoResult;
import com.smartsoft.movietracker.utils.Constant;
import com.smartsoft.movietracker.utils.SharedPreferences;
import com.smartsoft.movietracker.utils.Utils;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * This class is calls the all API query in the app
 */
public class ApiController {

    /**
     * Contains the class name.
     */
    private static final String TAG = ApiController.class.getName();

    /**
     * The Singleton class instance
     */
    private static ApiController sInstance = null;

    /**
     * Variable of {@link GenreAPI} class.
     */
    private GenreAPI genreApiService;

    /**
     * Variable of {@link MovieAPI} class.
     */
    private MovieAPI movieApiService;

    /**
     * Variable of {@link CastAPI} class.
     */
    private CastAPI castApiService;

    /**
     * Variable of {@link ReviewAPI} class.
     */
    private ReviewAPI reviewApiService;

    /**
     * Variable of {@link VideoAPI} class.
     */
    private VideoAPI videoApiService;

    /**
     * Variable of {@link SearchMovieAPI} class.
     */
    private SearchMovieAPI searchMovieService;

    /**
     * SingleTon class constructor
     * This constructor define the
     * {@link Retrofit} instance for the queries
     * and the all API class variables.
     */
    private ApiController() {

        Retrofit mRetrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(Constant.API.BASE_URL)
                .build();
        genreApiService = mRetrofit.create(GenreAPI.class);
        movieApiService = mRetrofit.create(MovieAPI.class);
        castApiService = mRetrofit.create(CastAPI.class);
        reviewApiService = mRetrofit.create(ReviewAPI.class);
        videoApiService = mRetrofit.create(VideoAPI.class);
        searchMovieService = mRetrofit.create(SearchMovieAPI.class);
    }

    /**
     * Most important function in the singleton {@link ApiController} class
     * It is a static function because from everywhere the program can call it
     * By this function the program can calls all the functions in the class.
     * @return the class instance if the instance ins't null and if null then
     * instantiate a new {@link ApiController} instance.
     *
     */
    public static ApiController getInstance() {
        if (sInstance == null) {
            sInstance = new ApiController();
        }
        return sInstance;
    }

    /**
     * this function call the {@link GenreAPI} query to get all the
     * genres from the API
     * @return an Observable<ArrayList<Genre>>
     */
    public Observable<ArrayList<Genre>> getAllGenres() {
        return genreApiService.getGenres(Constant.API.API_KEY, Constant.API.LANGUAGE)
                .map(genreResultResponse -> {
                    Log.e(TAG, genreResultResponse.toString());
                    assert genreResultResponse.body() != null;
                    return genreResultResponse.body().getGenres();
                });
    }

    /**
     * this function call the {@link SearchMovieAPI} query to get all the
     * movies that contains
     * @param keyword (what send to query because we need just those movies which contain
     *                in title this keyword)
     * @param page is used for send the API what page we need to download
     * @return an Observable<ArrayList<Genre>>
     */
    public Observable<MovieResult> getSearchedMovies(String keyword, Integer page) {
        return searchMovieService
                .getSearchedMoviesByString(
                        Constant.API.API_KEY,
                        keyword,
                        true,
                        page).map(searchedMovieResultResponse -> {
                    Log.e(TAG, "getSearchedMovies: " + searchedMovieResultResponse.toString());
                    return searchedMovieResultResponse.body();
                });
    }

    /**
     * this function call the {@link MovieAPI} query to get all the
     * movies which contains the
     * @param genreIds (Selected genres)
     * @param page (The next page which will download from API)
     * @param context (Is used for to read from the {@link android.content.SharedPreferences}
     *                in which order and sort want to download the user the movies)
     * @return Observable<MovieResult> object if the query works perfectly
     * or null if something went wrong
     */
    public Observable<MovieResult> getMovies(Context context, ArrayList<Integer> genreIds, Integer page) {

        SharedPreferences sp = new SharedPreferences(context, context.getString(R.string.sortBy));
        String sortBy = sp.ReadFromStorage();

        SharedPreferences orderSp = new SharedPreferences(context, context.getString(R.string.orderBy));
        String orderBy = orderSp.ReadFromStorage();


        Log.e(TAG, String.valueOf(page));
        return movieApiService.getMovies(Constant.API.API_KEY,
                Constant.API.LANGUAGE,
                sortBy + orderBy,
                Constant.API.INCLUDE_ADULT,
                Constant.API.INCLUDE_VIDEO,
                page,
                Utils.genreListToCsvIdString(genreIds)).map(movieResultResponse -> {
            Log.e(TAG, "getMovies:" + movieResultResponse.toString());
            if (movieResultResponse.code() == Constant.API.RESPONSE_CODE) {
                return movieResultResponse.body();
            }
            return null;
        });
    }

    /**
     * Calls the {@link CastAPI} to download all the cast members
     * for the respected movie which has attribute
     * @param movie_id (Unique key of the current movie)
     * @return Observable<ArrayList<Cast>> object
     */
    public Observable<ArrayList<Cast>> getCast(int movie_id) {
        return castApiService.getCast(movie_id, Constant.API.API_KEY)
                .map(castResultResponse -> {
                    assert castResultResponse.body() != null;
                    return castResultResponse.body().getCast();
                });
    }

    /**
     * Calls the {@link ReviewAPI} to download all the review
     * for the current movie which has the same attribute like
     * @param movie_id (Unique key/ ID  of the current movie)
     * @return Observable<ArrayList<Review>>
     */
    public Observable<ArrayList<Review>> getReviews(int movie_id) {
        return reviewApiService.getReviews(movie_id, Constant.API.API_KEY)
                .map(ReviewResult::getReviews);
    }

    /**
     * Calls the {@link VideoAPI} to download all the videos
     * for the current movie which has the same attribute like
     * @param movie_id (Unique key/ ID  of the current movie)
     * @return Observable<ArrayList<Video >>
     */
    public Observable<ArrayList<Video>> getVideos(int movie_id) {
        return videoApiService.getVideos(movie_id, Constant.API.API_KEY)
                .map(VideoResult::getResults);
    }


}

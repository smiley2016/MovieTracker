package com.smartsoft.movietracker.service;

import android.util.Log;

import com.smartsoft.movietracker.model.cast.Cast;
import com.smartsoft.movietracker.model.cast.CastResult;
import com.smartsoft.movietracker.model.genre.Genre;
import com.smartsoft.movietracker.model.genre.GenreResult;
import com.smartsoft.movietracker.model.movie.Movie;
import com.smartsoft.movietracker.model.movie.MovieResult;
import com.smartsoft.movietracker.model.review.Review;
import com.smartsoft.movietracker.model.review.ReviewResult;
import com.smartsoft.movietracker.model.video.Video;
import com.smartsoft.movietracker.model.video.VideoResult;
import com.smartsoft.movietracker.utils.Constant;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.smartsoft.movietracker.utils.Constant.Genre.genre;

public class ApiController {

    private static final String TAG = "Apicontroller";
    private static ApiController sInstance = null;
    private GenreAPI genreApiService;
    private MovieAPI movieApiService;
    private CastAPI castApiService;
    private ReviewAPI reviewApiService;
    private VideoAPI videoApiService;

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
        videoApiService = mRetrofit.create((VideoAPI.class));
    }

    public static ApiController getInstance(){
        if(sInstance == null){
            sInstance =  new ApiController();
        }
        return sInstance;
    }


    public Observable<ArrayList<Genre>> getAllGenres() {
        return genreApiService.getGenres()
                .map(GenreResult::getGenres);
    }

    public Observable<ArrayList<Movie>> getMovies(){
        ArrayList<Integer> genreIds = new ArrayList<>();
        for (Genre value : genre) {
            genreIds.add(value.getId());
        }

        Constant.API.PAGE++;
        Log.e(TAG, String.valueOf(Constant.API.PAGE));
        return movieApiService.getMovies(Constant.API.API_KEY,
                Constant.API.LANGUAGE,
                Constant.API.sortList,
                Constant.API.INCLUDE_ADULT,
                Constant.API.INCLUDE_VIDEO,
                Constant.API.PAGE,
                genreIds).map(MovieResult::getResults);
    }

    public Observable<ArrayList<Cast>> getCast(int movie_id){
        return castApiService.getCast(movie_id, Constant.API.API_KEY)
                .map(CastResult::getCast);
    }

    public Observable<ArrayList<Review>> getReviews(int movie_id){
        return reviewApiService.getReviews(movie_id, Constant.API.API_KEY)
                .map(ReviewResult::getReviews);
    }

    public Observable<ArrayList<Video>> getVideos(int movie_id){
        return videoApiService.getVideos(movie_id, Constant.API.API_KEY)
                .map(VideoResult::getResults);
    }



}

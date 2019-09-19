package com.smartsoft.movietracker.service;

import android.content.Context;
import android.util.Log;

import com.smartsoft.movietracker.R;
import com.smartsoft.movietracker.model.cast.Cast;
import com.smartsoft.movietracker.model.genre.Genre;
import com.smartsoft.movietracker.model.movie.Movie;
import com.smartsoft.movietracker.model.review.Review;
import com.smartsoft.movietracker.model.review.ReviewResult;
import com.smartsoft.movietracker.model.video.Video;
import com.smartsoft.movietracker.model.video.VideoResult;
import com.smartsoft.movietracker.utils.Constant;
import com.smartsoft.movietracker.utils.SharedPreferences;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiController {

    private static final String TAG = ApiController.class.getName();
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
                .map(genreResultResponse -> {
                    Log.e(TAG, genreResultResponse.toString());
                    assert genreResultResponse.body() != null;
                    return genreResultResponse.body().getGenres();
                });
    }

    public Observable<ArrayList<Movie>> getMovies(Context context, ArrayList<Integer> genreIds){

        SharedPreferences sp = new SharedPreferences(context, context.getString(R.string.sortBy));
        String sortBy = sp.ReadFromStorage();

        SharedPreferences orderSp = new SharedPreferences( context, context.getString(R.string.orderBy));
        String orderBy = orderSp.ReadFromStorage();

        Constant.API.PAGE++;
        Log.e(TAG, String.valueOf(Constant.API.PAGE));
        return movieApiService.getMovies(Constant.API.API_KEY,
                Constant.API.LANGUAGE,
                sortBy+orderBy,
                Constant.API.INCLUDE_ADULT,
                Constant.API.INCLUDE_VIDEO,
                Constant.API.PAGE,
                genreIds).map(movieResultResponse -> {
            Log.e(TAG, context.getString(R.string.getMovies) + movieResultResponse.toString());
                    assert movieResultResponse.body() != null;
                    return movieResultResponse.body().getResults();
                });
    }

    public Observable<ArrayList<Cast>> getCast(int movie_id){
        return castApiService.getCast(movie_id, Constant.API.API_KEY)
                .map(castResultResponse -> {
                    assert castResultResponse.body() != null;
                    return castResultResponse.body().getCast();
                });
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

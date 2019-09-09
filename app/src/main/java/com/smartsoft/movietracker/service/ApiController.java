package com.smartsoft.movietracker.service;

import android.util.Log;

import com.smartsoft.movietracker.model.cast.CastResult;
import com.smartsoft.movietracker.model.genre.Genre;
import com.smartsoft.movietracker.model.genre.GenreResult;
import com.smartsoft.movietracker.model.movie.MovieResult;
import com.smartsoft.movietracker.model.review.Review;
import com.smartsoft.movietracker.model.review.ReviewResult;
import com.smartsoft.movietracker.model.video.VideoResult;
import com.smartsoft.movietracker.utils.Constant;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
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
                .baseUrl(Constant.Common.BASE_URL)
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


    public void getAllGenres(Callback<GenreResult> callback){
        genreApiService.getGenres().enqueue(callback);
    }

    public void getMovies(Callback<MovieResult> callback){
        ArrayList<Integer> genreIds = new ArrayList<>();
        for (Genre value : genre) {
            genreIds.add(value.getId());
        }
        Constant.Common.PAGE++;
        Log.e(TAG, String.valueOf(Constant.Common.PAGE));
        movieApiService.getMovies(Constant.Common.API_KEY,
                Constant.Common.LANGUAGE,
                Constant.Common.SORT_BY,
                Constant.Common.INCLUDE_ADULT,
                Constant.Common.INCLUDE_VIDEO,
                Constant.Common.PAGE,
                genreIds).enqueue(callback);
    }

    public void getCast(int movie_id, Callback<CastResult> callback){
        castApiService.getCast(movie_id, Constant.Common.API_KEY).enqueue(callback);
    }

    public void getReviews(int movie_id, Callback<ReviewResult> callback){
        reviewApiService.getReviews(movie_id, Constant.Common.API_KEY).enqueue(callback);
    }

    public void getVideos(int movie_id, Callback<VideoResult> callback){
        videoApiService.getVideos(movie_id, Constant.Common.API_KEY).enqueue(callback);
    }



}

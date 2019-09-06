package com.smartsoft.movietracker.service;

import android.util.Log;

import com.smartsoft.movietracker.model.Genre;
import com.smartsoft.movietracker.model.GenreResult;
import com.smartsoft.movietracker.model.Result;
import com.smartsoft.movietracker.utils.Constant;

import java.util.ArrayList;

import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.smartsoft.movietracker.utils.Constant.Common.BASE_URL;
import static com.smartsoft.movietracker.utils.Constant.Genre.genre;

public class ApiController {

    private static final String TAG = "Apicontroller";
    private static ApiController sInstance = null;
    private GenreAPI genreApiService;
    private MovieAPI movieApiService;

    private ApiController() {

        Retrofit mRetrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build();
        genreApiService = mRetrofit.create(GenreAPI.class);
        movieApiService = mRetrofit.create(MovieAPI.class);
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

    public void getMovies(Callback<Result> callback){
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



}

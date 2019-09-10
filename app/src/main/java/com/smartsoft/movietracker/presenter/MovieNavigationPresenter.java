package com.smartsoft.movietracker.presenter;

import android.util.Log;

import androidx.annotation.NonNull;

import com.smartsoft.movietracker.MainActivity;
import com.smartsoft.movietracker.model.movie.Movie;
import com.smartsoft.movietracker.model.movie.MovieResult;
import com.smartsoft.movietracker.service.ApiController;
import com.smartsoft.movietracker.view.home.GenreSelectorFragment;

import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class MovieNavigationPresenter {

    public static String TAG = MovieNavigationPresenter.class.getName();

    GenreSelectorFragment fragment;

    public MovieNavigationPresenter() {
    }

    public void updateMovieNavigationGridView(View view){


        ApiController.getInstance().getMovies(new Callback<MovieResult>() {
            @Override
            public void onResponse(@NonNull Call<MovieResult> call, @NonNull Response<MovieResult> response) {
                Log.e(TAG, response.toString());
                ArrayList<Movie> movies = response.body().getResults();
                view.updateMovieNavigationGridView(movies);

            }

            @Override
            public void onFailure(@NonNull Call<MovieResult> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });


    }



    public interface View{
        void updateMovieNavigationGridView(ArrayList<Movie> movies);
    }
}

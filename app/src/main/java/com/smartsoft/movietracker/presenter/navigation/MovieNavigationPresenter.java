package com.smartsoft.movietracker.presenter.navigation;

import android.util.Log;

import androidx.annotation.NonNull;
import com.smartsoft.movietracker.model.Movie;
import com.smartsoft.movietracker.model.Result;
import com.smartsoft.movietracker.presenter.home.HomePresenter;
import com.smartsoft.movietracker.service.ApiController;
import com.smartsoft.movietracker.utils.Constant;

import java.util.ArrayList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class MovieNavigationPresenter {

    public static String TAG = HomePresenter.class.getName();

    public MovieNavigationPresenter() {
    }

    public void updateMovieNavigationGridView(View view){


        ApiController.getInstance().getMovies(new Callback<Result>() {
            @Override
            public void onResponse(@NonNull Call<Result> call, @NonNull Response<Result> response) {
                Log.e(TAG, response.toString());
                ArrayList<Movie> movies = response.body().getResults();
                view.updateMovieNavigationGridView(movies);

            }

            @Override
            public void onFailure(@NonNull Call<Result> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });


    }

    public interface View{
        void updateMovieNavigationGridView(ArrayList<Movie> movies);
    }
}

package com.smartsoft.movietracker.presenter;

import android.util.Log;

import androidx.annotation.NonNull;

import com.smartsoft.movietracker.model.genre.Genre;
import com.smartsoft.movietracker.model.genre.GenreResult;
import com.smartsoft.movietracker.service.ApiController;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GenreSelectorPresenter {

    public static String TAG = GenreSelectorPresenter.class.getName();

    public GenreSelectorPresenter() {
    }

    public void updateGenres(View view){
            ApiController.getInstance().getAllGenres(new Callback<GenreResult>() {
                @Override
                public void onResponse(@NonNull Call<GenreResult> call, @NonNull Response<GenreResult> response) {
                    GenreResult result = response.body();

                    assert result != null;
                    view.updateGenres(result.getGenres());
                }

                @Override
                public void onFailure(Call<GenreResult> call, Throwable t) {
                    Log.e(TAG, t.toString());
                }
            });
        }


    public interface View {
        void updateGenres(ArrayList<Genre> list);

    }
}

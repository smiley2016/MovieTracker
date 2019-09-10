package com.smartsoft.movietracker.presenter;

import android.util.Log;


import com.smartsoft.movietracker.interfaces.DetailPageInterface;
import com.smartsoft.movietracker.model.cast.CastResult;
import com.smartsoft.movietracker.model.review.ReviewResult;
import com.smartsoft.movietracker.model.video.VideoResult;
import com.smartsoft.movietracker.service.ApiController;
import com.smartsoft.movietracker.view.detail.DetailPageActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailPagePresenter implements DetailPageInterface.DetailPagePresenterInterface {


    static final String TAG = "DetailPagePresenter";
    private DetailPageActivity activity;

    public DetailPagePresenter(DetailPageActivity activity) {
        this.activity = activity;
    }

    @Override
    public void downloadCast(int movie_id) {
        ApiController.getInstance().getCast(movie_id, new Callback<CastResult>() {
            @Override
            public void onResponse(Call<CastResult> call, Response<CastResult> response) {
                Log.e(TAG, response.toString());
                activity.updateCast(response.body().getCast());
            }

            @Override
            public void onFailure(Call<CastResult> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });
    }

    @Override
    public void downloadReviews(int movie_id) {
        ApiController.getInstance().getReviews(movie_id, new Callback<ReviewResult>() {
            @Override
            public void onResponse(Call<ReviewResult> call, Response<ReviewResult> response) {
                Log.e(TAG, response.body().toString());
                activity.updateReviews(response.body().getReviews());
            }

            @Override
            public void onFailure(Call<ReviewResult> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });
    }

    @Override
    public void downloadVideos(int movie_id) {
        ApiController.getInstance().getVideos(movie_id, new Callback<VideoResult>() {
            @Override
            public void onResponse(Call<VideoResult> call, Response<VideoResult> response) {
                Log.e(TAG, response.toString());
                VideoResult video = response.body();
                activity.updateVideos(response.body().getResults());
            }

            @Override
            public void onFailure(Call<VideoResult> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });

    }


}

package com.smartsoft.movietracker.presenter;

import android.util.Log;


import com.smartsoft.movietracker.interfaces.DetailPageInterface;
import com.smartsoft.movietracker.model.cast.Cast;
import com.smartsoft.movietracker.model.review.Review;
import com.smartsoft.movietracker.model.video.Video;
import com.smartsoft.movietracker.service.ApiController;
import com.smartsoft.movietracker.view.detail.DetailPageFragment;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class DetailPagePresenter implements DetailPageInterface.DetailPagePresenterInterface {


    static final String TAG = "DetailPagePresenter";
    private DetailPageFragment detailPageFragment;

    public DetailPagePresenter(DetailPageFragment detailPageFragment) {
        this.detailPageFragment = detailPageFragment;
    }

    @Override
    public void downloadCast(int movie_id) {
        ApiController.getInstance().getCast(movie_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArrayList<Cast>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ArrayList<Cast> casts) {
                        detailPageFragment.updateCast(casts);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("3ss", "retrofit fail" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void downloadReviews(int movie_id) {
        ApiController.getInstance().getReviews(movie_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArrayList<Review>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ArrayList<Review> reviews) {
                        detailPageFragment.updateReviews(reviews);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("3ss", "retrofit fail" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void downloadVideos(int movie_id) {
        ApiController.getInstance().getVideos(movie_id)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArrayList<Video>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ArrayList<Video> videos) {
                        detailPageFragment.updateVideos(videos);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("3ss", "retrofit fail" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }


}

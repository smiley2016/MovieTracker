package com.smartsoft.movietracker.presenter;

import android.util.Log;

import com.smartsoft.movietracker.interfaces.OnDetailPageListener;
import com.smartsoft.movietracker.model.MovieDetails;
import com.smartsoft.movietracker.service.ApiController;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class DetailPagePresenter {
    private static final String TAG = DetailPagePresenter.class.getName();

    private OnDetailPageListener detailPageViewInterface;

    public DetailPagePresenter(OnDetailPageListener detailPageViewInterface) {
        this.detailPageViewInterface = detailPageViewInterface;
    }


    public void loadData(int movieId) {
        Observable.zip(
                ApiController.getInstance().getCast(movieId),
                ApiController.getInstance().getReviews(movieId),
                ApiController.getInstance().getVideos(movieId), MovieDetails::new)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MovieDetails>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(MovieDetails movieDetails) {
                        Log.e(TAG, "DetailPagePresenter" + movieDetails);

                        detailPageViewInterface.displayData(movieDetails);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "DetailPagePresenter", e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    public void backPressed() {
        detailPageViewInterface.backPressed();
    }


}

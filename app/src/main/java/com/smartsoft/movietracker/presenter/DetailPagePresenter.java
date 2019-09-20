package com.smartsoft.movietracker.presenter;

import com.smartsoft.movietracker.interfaces.DetailPageInterface;
import com.smartsoft.movietracker.model.MovieDetails;
import com.smartsoft.movietracker.service.ApiController;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class DetailPagePresenter implements DetailPageInterface.DetailPagePresenterInterface {

    private DetailPageInterface.DetailPageViewInterface detailPageViewInterface;

    public DetailPagePresenter(DetailPageInterface.DetailPageViewInterface detailPageViewInterface) {
        this.detailPageViewInterface = detailPageViewInterface;
    }

    @Override
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
                        detailPageViewInterface.loadData(movieDetails);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}

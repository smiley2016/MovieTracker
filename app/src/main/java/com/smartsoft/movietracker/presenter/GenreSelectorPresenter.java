package com.smartsoft.movietracker.presenter;

import android.util.Log;

import com.smartsoft.movietracker.model.genre.Genre;
import com.smartsoft.movietracker.service.ApiController;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class GenreSelectorPresenter {

    public static String TAG = GenreSelectorPresenter.class.getName();

    public void updateGenres(GenreSelectorInterface genreSelectorInterface) {
        ApiController.getInstance().getAllGenres()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArrayList<Genre>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ArrayList<Genre> genres) {
                        if(genreSelectorInterface != null){
                            genreSelectorInterface.updateGenres(genres);
                        }

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


    //TODO: Ennek a MovieNavigationInterface-nak adj valami mas nevet mert elegge megteveszto (pl GenreView vagy valami hasonlo)
    public interface GenreSelectorInterface {
        void updateGenres(ArrayList<Genre> list);

    }
}

package com.smartsoft.movietracker.presenter;

import android.util.Log;

import com.smartsoft.movietracker.model.genre.Genre;
import com.smartsoft.movietracker.service.ApiController;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @see GenreSelectorPresenter using for the saving the data we get from the API
 * and send these data to {@link com.smartsoft.movietracker.view.main.genre.GenreSelectorFragment}
 */

public class GenreSelectorPresenter {

    /**
     * @see #TAG is saves the class name.
     */
    public static String TAG = GenreSelectorPresenter.class.getName();

    /**
     * @see #updateGenres(GenreSelectorInterface) function is calls the
     * method of the {@link ApiController} namely getAllGenres() function
     * to get the data from the server.
     * When the data downloaded in the {@link Observer} onNext() function called the
     * interface.updateGenres() function. Which is implemented in
     * {@link com.smartsoft.movietracker.view.main.genre.GenreSelectorFragment}
     * @param genreSelectorInterface used for to communicate between
     *                               {@link com.smartsoft.movietracker.view.main.genre.GenreSelectorFragment}
     *                               {@link GenreSelectorPresenter}
     */
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
                        if (genreSelectorInterface != null) {
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

    /**
     * @see GenreSelectorInterface is used for presenter will have a point where
     * can call the respected Fragment implementation to update the data on the userInterface.
     */
    public interface GenreSelectorInterface {
        void updateGenres(ArrayList<Genre> list);
    }
}

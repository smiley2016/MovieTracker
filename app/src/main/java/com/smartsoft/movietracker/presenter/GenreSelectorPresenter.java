package com.smartsoft.movietracker.presenter;

import android.util.Log;

import androidx.annotation.NonNull;

import com.smartsoft.movietracker.model.genre.Genre;
import com.smartsoft.movietracker.model.genre.GenreResult;
import com.smartsoft.movietracker.service.ApiController;
import com.smartsoft.movietracker.utils.Constant;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GenreSelectorPresenter {

    public static String TAG = GenreSelectorPresenter.class.getName();

    public GenreSelectorPresenter() {
    }

    public void updateGenres(View view){
            ApiController.getInstance().getAllGenres()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArrayList<Genre>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ArrayList<Genre> genres) {
                        view.updateGenres(genres);
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
    public interface View {
        void updateGenres(ArrayList<Genre> list);

    }
}

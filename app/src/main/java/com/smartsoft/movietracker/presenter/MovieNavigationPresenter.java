package com.smartsoft.movietracker.presenter;

import android.util.Log;

import androidx.annotation.NonNull;

import com.smartsoft.movietracker.MainActivity;
import com.smartsoft.movietracker.model.movie.Movie;
import com.smartsoft.movietracker.model.movie.MovieResult;
import com.smartsoft.movietracker.service.ApiController;
import com.smartsoft.movietracker.utils.Constant;
import com.smartsoft.movietracker.view.home.GenreSelectorFragment;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
public class MovieNavigationPresenter {

    public static String TAG = MovieNavigationPresenter.class.getName();

    GenreSelectorFragment fragment;
    View view ;

    public MovieNavigationPresenter(View view) {
        this.view = view;

    }

    public void updateMovieNavigationGridView(){

        ApiController.getInstance().getMovies()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArrayList<Movie>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ArrayList<Movie> movies) {
                        ArrayList<Movie> movieList = new ArrayList<>();
                        for (Movie it: movies){
                            if(it.getPosterPath() != null){
                                movieList.add(it);
                            }
                        }
                        view.updateMovieNavigationGridView(movies);
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



    public interface View{
        void updateMovieNavigationGridView(ArrayList<Movie> movies);
    }
}

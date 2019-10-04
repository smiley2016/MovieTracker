package com.smartsoft.movietracker.presenter;

import android.content.Context;
import android.util.Log;

import com.smartsoft.movietracker.model.genre.Genre;
import com.smartsoft.movietracker.model.movie.Movie;
import com.smartsoft.movietracker.service.ApiController;

import java.util.ArrayList;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MovieNavigationPresenter {

    public static String TAG = MovieNavigationPresenter.class.getName();

    private MovieNavigationInterface movieNavigationInterface;

    public MovieNavigationPresenter(MovieNavigationInterface movieNavigationInterface) {
        this.movieNavigationInterface = movieNavigationInterface;

    }

    public void loadMovieNavigationPresenter(Context context, ArrayList<Genre> selectedGenres) {
        ArrayList<Integer> genreIds = new ArrayList<>();

        for (Genre it : selectedGenres) {
            genreIds.add(it.getId());
        }

        ApiController.getInstance().getMovies(context, genreIds)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArrayList<Movie>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ArrayList<Movie> movies) {
                        ArrayList<Movie> movieList = new ArrayList<>();
                        for (Movie it : movies) {
                            if (it.getPosterPath() != null) {
                                movieList.add(it);
                            }
                        }
                        movieNavigationInterface.updateMovieNavigationGridView(movieList);
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


    public interface MovieNavigationInterface {
        void updateMovieNavigationGridView(ArrayList<Movie> movies);
    }
}

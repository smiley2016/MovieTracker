package com.smartsoft.movietracker.presenter;

import android.content.Context;
import android.util.Log;

import com.smartsoft.movietracker.model.genre.Genre;
import com.smartsoft.movietracker.model.movie.Movie;
import com.smartsoft.movietracker.model.movie.MovieResult;
import com.smartsoft.movietracker.service.ApiController;
import com.smartsoft.movietracker.utils.Constant;

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

    public void loadMovieData(Context context, ArrayList<Genre> selectedGenres) {
        ArrayList<Integer> genreIds = new ArrayList<>();

        for (Genre it : selectedGenres) {
            genreIds.add(it.getId());
        }

        Constant.API.PAGE++;

        ApiController.getInstance().getMovies(context, genreIds)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MovieResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(MovieResult movieResult) {
                        ArrayList<Movie> movieList = movieResult.getResults();
                        Integer totalPages = movieResult.getTotalPages();
                        for (Movie it : movieList) {
                            if (it.getPosterPath() != null) {
                                movieList.add(it);
                            }
                        }
                        movieNavigationInterface.updateMovieNavigationGridView(movieList, totalPages);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }


    public interface MovieNavigationInterface {
        void updateMovieNavigationGridView(ArrayList<Movie> movies, Integer totalPages);
    }
}

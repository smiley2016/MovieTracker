package com.smartsoft.movietracker.presenter;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.smartsoft.movietracker.R;
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
    private Integer page;

    public MovieNavigationPresenter(MovieNavigationInterface movieNavigationInterface) {
        this.movieNavigationInterface = movieNavigationInterface;
        page = 0;
    }

    public void loadMovieData(Context context, ArrayList<Genre> selectedGenres) {
        ArrayList<Integer> genreIds = new ArrayList<>();

        for (Genre it : selectedGenres) {
            genreIds.add(it.getId());
        }

        page++;

        ApiController.getInstance().getMovies(context, genreIds, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MovieResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(MovieResult movieResult) {
                        if(movieResult != null){
                            ArrayList<Movie> movieList = new ArrayList<>();
                            for (Movie it : movieResult.getResults()) {
                                if (it.getPosterPath() != null) {
                                    movieList.add(it);
                                }
                            }
                            movieNavigationInterface.updateMovieNavigationGridView(movieList, movieResult.getTotalPages());
                        }else{
                            Toast.makeText(context, R.string.data_load_server_error, Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "onError: Data couldn\\'t loaded from the Server", e);
                        Toast.makeText(context, R.string.data_load_server_error, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }

    public void onBackgroundChange(String backgroundPath){
        movieNavigationInterface.onBackgroundChange(backgroundPath);
    }

    public void clearPage() {
        page = 0;
    }

    public Integer getPage() {
        return page;
    }

    public interface MovieNavigationInterface {
        void updateMovieNavigationGridView(ArrayList<Movie> movies, Integer totalPages);

        void onBackgroundChange(String backdropPath);
    }
}

package com.smartsoft.movietracker.view.main.navigation;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.leanback.widget.ArrayObjectAdapter;
import androidx.leanback.widget.ItemBridgeAdapter;
import androidx.leanback.widget.PresenterSelector;
import androidx.leanback.widget.SinglePresenterSelector;
import androidx.leanback.widget.VerticalGridView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.smartsoft.movietracker.R;
import com.smartsoft.movietracker.interfaces.ToolbarListener;
import com.smartsoft.movietracker.model.genre.Genre;
import com.smartsoft.movietracker.model.movie.Movie;
import com.smartsoft.movietracker.presenter.MovieNavigationPresenter;
import com.smartsoft.movietracker.utils.Constant;
import com.smartsoft.movietracker.utils.StringUtils;
import com.smartsoft.movietracker.view.main.BaseMainNavigationFragment;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class MovieNavigationFragment extends BaseMainNavigationFragment implements MovieNavigationPresenter.MovieNavigationInterface, ToolbarListener{

    public static String TAG = MovieNavigationFragment.class.getSimpleName();

    @BindView(R.id.gridView_container)
    VerticalGridView verticalGridView;

    @BindView(R.id.fragment_base_background)
    ImageView background;

    private MovieNavigationPresenter presenter;
    private ArrayList<Genre> selectedGenres;
    private ArrayList<Genre> allGenres;
    private ObservableEmitter<String> urlStreamEmitter;
    private Drawable placeholderDrawable;
    private ArrayList<Movie> movies;
    private MovieNavigationGridViewPresenter gridViewPresenter;
    private ArrayObjectAdapter objectAdapter;
    private boolean isSearched = false;
    private String searchForString;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        movies = new ArrayList<>();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_base, container, false);
            ButterKnife.bind(this, rootView);
            initViews(this, View.INVISIBLE, getGenreTitle());
        }
        initializeViews();
        return rootView;
    }

    private void initializeViews() {
        initEmitters();
        verticalGridView.setNumColumns(Constant.GridView.COLUMN_NUM7);
        verticalGridView.setItemSpacing((int) rootView.getContext().getResources().getDimension(R.dimen.spacing));
        verticalGridView.setFocusDrawingOrderEnabled(true);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        selectedGenres = new ArrayList<>();
        allGenres = new ArrayList<>();
        presenter = new MovieNavigationPresenter(this);
        if (getArguments() != null && getArguments().getSerializable(context.getString(R.string.selectedGenres)) != null) {
            selectedGenres = (ArrayList<Genre>) getArguments().getSerializable(context.getString(R.string.selectedGenres));
            if (selectedGenres != null) {
                presenter.loadMovieData(context, selectedGenres);
            }
        }
        if(getArguments() != null && getArguments().getString(context.getString(R.string.search)) != null){
            searchForString = getArguments().getString(context.getString(R.string.search));
            isSearched = true;
            presenter.loadMovieData(searchForString, getContext());
        }
        if(getArguments() != null){
            allGenres = (ArrayList<Genre>) getArguments().getSerializable(context.getString(R.string.allGenres));
        }


    }

    @Override
    public void updateMovieNavigationGridView(ArrayList<Movie> movies, Integer totalPages) {
        this.movies.addAll(movies);

        if (objectAdapter != null) {
            objectAdapter.addAll(objectAdapter.size(), movies);
            gridViewPresenter.updateListSize(movies.size());

        } else {
            onInitRecyclerViewAdapter(movies, totalPages);
        }

    }

    @Override
    public void onBackgroundChange(String backdropPath) {
        urlStreamEmitter.onNext(Constant.API.IMAGE_ORIGINAL_BASE_URL + backdropPath);
    }

    @Override
    public int getPosition(Movie movie) {
        return objectAdapter.indexOf(movie);
    }


    private String getGenreTitle() {
        StringBuilder genreTitle = new StringBuilder();
        if(selectedGenres.size() != 0){

            for (Genre selectedGenre : selectedGenres) {
                genreTitle.append(selectedGenre.getName()).append(StringUtils.HYPHEN_DELITMITER_WITH_SPACE_IN_FRONT_AND_BACK);
            }
            genreTitle.replace(genreTitle.length() - 3, genreTitle.length() - 1, StringUtils.EMPTY_STRING);

            return genreTitle.toString();
        }else{
            return getString(R.string.search_in_all_genres);
        }

    }

    private void onInitRecyclerViewAdapter(ArrayList<Movie> movies, int totalPages) {

        gridViewPresenter = new MovieNavigationGridViewPresenter(
                presenter, allGenres, selectedGenres, totalPages, isSearched, searchForString);

        objectAdapter = new ArrayObjectAdapter();

        objectAdapter.addAll(0, movies);
        gridViewPresenter.updateListSize(movies.size());

        PresenterSelector presenterSelector = new SinglePresenterSelector(gridViewPresenter);

        ItemBridgeAdapter adapter = new ItemBridgeAdapter();
        adapter.setAdapter(objectAdapter);
        adapter.setPresenter(presenterSelector);

        verticalGridView.setHasFixedSize(true);
        verticalGridView.setAdapter(adapter);
    }

    @Override
    public void onInternetConnected() {
        super.onInternetConnected();
        if (objectAdapter != null && objectAdapter.size() == 0) {
            if (presenter.getPage() == 1) {
                presenter.clearPage();
            }
            presenter.loadMovieData(rootView.getContext(), selectedGenres);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.clearPage();
        gridViewPresenter = null;
    }

    @Override
    public void onSortButtonClicked(Dialog dialog) {
        dialog.dismiss();
        if (gridViewPresenter != null) {
            objectAdapter.clear();
            gridViewPresenter.clearListSize();
        }
        presenter.clearPage();
        presenter.loadMovieData(getContext(), selectedGenres);

    }

    @Override
    public void onSearchButtonClicked() {

    }

    @Override
    public void onSearch() {
        if(objectAdapter != null){
            objectAdapter.clear();
            presenter.clearPage();
            presenter.loadMovieData(super.getSearchText(), rootView.getContext());

        }
    }

    private void initEmitters() {
        Observable.create((ObservableOnSubscribe<String>) emitter -> urlStreamEmitter = emitter)
                .debounce(1000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        Log.d(TAG, "Context when load image: " + getContext() + "  " + s);
                        Glide.with(rootView.getContext()).asDrawable().load(s).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).transition(withCrossFade(500)).into(new CustomTarget<Drawable>() {
                            @Override
                            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                if (placeholderDrawable != null)
                                    Glide.with(rootView.getContext()).load(s).placeholder(placeholderDrawable).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).transition(withCrossFade(500)).into(background);
                                else {
                                    Glide.with(rootView.getContext()).load(s).placeholder(R.drawable.background).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).transition(withCrossFade(500)).into(background);
                                }
                                placeholderDrawable = resource;
                            }

                            @Override
                            public void onLoadCleared(@Nullable Drawable placeholder) {

                            }
                        });

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
}

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
import java.util.Iterator;
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

    private MovieNavigationVerticalGridViewAdapter adapter;
    private MovieNavigationPresenter presenter;
    private ArrayList<Genre> selectedGenres;
    private ObservableEmitter<String> urlStreamEmitter;
    private Drawable placeholderDrawable;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new MovieNavigationPresenter(this);
        presenter.loadMovieData(getContext(), selectedGenres);
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
        if (this.getArguments() != null) {
            selectedGenres = (ArrayList<Genre>) this.getArguments().getSerializable(context.getString(R.string.selectedGenres));
        }
    }

    @Override
    public void updateMovieNavigationGridView(ArrayList<Movie> movies, Integer totalPages) {
        if (adapter != null) {
            adapter.updateMovieList(movies);
            adapter.notifyDataSetChanged();
        } else {
            onInitRecyclerViewAdapter(movies, totalPages);
        }


    }

    @Override
    public void onBackgroundChange(String backdropPath) {
        urlStreamEmitter.onNext(Constant.API.IMAGE_ORIGINAL_BASE_URL + backdropPath);
    }


    private String getGenreTitle() {
        Iterator<Genre> genreIterator = selectedGenres.iterator();
        StringBuilder genreTitle = new StringBuilder();
        while (genreIterator.hasNext()) {
            genreTitle.append(genreIterator.next().getName()).append(StringUtils.HYPHEN_DELITMITER_WITH_SPACE_IN_FRONT_AND_BACK);
        }
        genreTitle.replace(genreTitle.length() - 3, genreTitle.length() - 1, StringUtils.EMPTY_STRING);

        return genreTitle.toString();
    }

    private void onInitRecyclerViewAdapter(ArrayList<Movie> movies, Integer totalPages) {
        adapter = new MovieNavigationVerticalGridViewAdapter(
                movies,
                getActivity(),
                presenter,
                selectedGenres,
                totalPages);
        verticalGridView.setHasFixedSize(true);
        verticalGridView.setAdapter(adapter);
    }

    @Override
    public void onInternetConnected() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.clearPage();
        adapter = null;
        setToolbarSearchButtonVisibility(View.VISIBLE);
    }

    @Override
    public void onSortButtonClicked(Dialog dialog) {
        dialog.dismiss();
        if (adapter != null) {
            adapter.clearAll();
        }
        presenter.clearPage();
        presenter.loadMovieData(getContext(), selectedGenres);

    }

    @Override
    public void onSearchButtonClicked() {

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

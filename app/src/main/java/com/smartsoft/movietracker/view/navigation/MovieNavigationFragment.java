package com.smartsoft.movietracker.view.navigation;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.leanback.widget.VerticalGridView;

import com.smartsoft.movietracker.R;
import com.smartsoft.movietracker.interfaces.ToolbarListener;
import com.smartsoft.movietracker.model.genre.Genre;
import com.smartsoft.movietracker.model.movie.Movie;
import com.smartsoft.movietracker.presenter.MovieNavigationPresenter;
import com.smartsoft.movietracker.utils.Constant;
import com.smartsoft.movietracker.view.BaseFragment;

import java.util.ArrayList;
import java.util.Iterator;

public class MovieNavigationFragment extends BaseFragment implements MovieNavigationPresenter.MovieNavigationInterface, ToolbarListener {

    public static String TAG = MovieNavigationFragment.class.getSimpleName();
    private VerticalGridView verticalGridView;
    private MovieNavigationVerticalGridViewAdapter adapter;
    private MovieNavigationPresenter presenter;
    private ArrayList<Genre> genres = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new MovieNavigationPresenter(this);
        presenter.updateMovieNavigationGridView(getContext(), genres);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_base, container, false);
        }

        initViews();
        initEmmitters();
        setToolbarView(this);
        setToolbarSearchButtonVisibility(View.INVISIBLE);
        initializeViews();
        return rootView;
    }

    private void initializeViews(){
        verticalGridView = rootView.findViewById(R.id.gridView_container);
        verticalGridView.setNumColumns(R.dimen.numColumns);
        verticalGridView.setItemSpacing((int) rootView.getContext().getResources().getDimension(R.dimen.spacing));
        verticalGridView.setFocusDrawingOrderEnabled(true);
        setGenreTitle();
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (this.getArguments() != null) {
            genres = (ArrayList<Genre>) this.getArguments().getSerializable(rootView.getContext().getString(R.string.genres));
        }
    }

    @Override
    public void updateMovieNavigationGridView(ArrayList<Movie> movies) {
        if (adapter != null) {
            adapter.updateMovieList(movies);
        } else {
            startRecyclerViewAdapter(movies);
        }


    }

    @Override
    public void setBackground(String path) {
        super.setBackground(path);
    }

    private void setGenreTitle() {
        Iterator<Genre> genreIterator = genres.iterator();
        StringBuilder genreTitle = new StringBuilder();
        while (genreIterator.hasNext()) {
            genreTitle.append(genreIterator.next().getName()).append(" - ");
        }
        genreTitle.replace(genreTitle.length() - 3, genreTitle.length() - 1, "");

        super.setTitle(genreTitle.toString());
    }

    private void startRecyclerViewAdapter(ArrayList<Movie> movies) {
        adapter = new MovieNavigationVerticalGridViewAdapter(movies, getActivity(), presenter, genres);
        verticalGridView.setHasFixedSize(true);
        verticalGridView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void InternetConnected() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Constant.API.PAGE = 0;
        if (adapter != null) {
            adapter.clearAll();
        }
        super.setToolbarSearchButtonVisibility(View.VISIBLE);
    }


    public MovieNavigationPresenter getPresenter() {
        return presenter;
    }


    @Override
    public void onSortButtonClicked(Dialog dialog) {
        dialog.dismiss();
        adapter.clearAll();
        Constant.API.PAGE = 0;
        presenter.updateMovieNavigationGridView(getContext(), genres);
    }

    @Override
    public int getBundleSize() {
        return 255;
    }

    public Bundle getAdaptersBundle() {
        return adapter.getBundle();
    }
}

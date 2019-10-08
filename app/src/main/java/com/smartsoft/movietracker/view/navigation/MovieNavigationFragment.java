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
import com.smartsoft.movietracker.interfaces.OnBackgroundChangeListener;
import com.smartsoft.movietracker.interfaces.ToolbarListener;
import com.smartsoft.movietracker.model.genre.Genre;
import com.smartsoft.movietracker.model.movie.Movie;
import com.smartsoft.movietracker.presenter.BackgroundPresenter;
import com.smartsoft.movietracker.presenter.MovieNavigationPresenter;
import com.smartsoft.movietracker.utils.Constant;
import com.smartsoft.movietracker.utils.StringUtils;
import com.smartsoft.movietracker.view.BaseFragment;

import java.util.ArrayList;
import java.util.Iterator;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieNavigationFragment extends BaseFragment implements MovieNavigationPresenter.MovieNavigationInterface, ToolbarListener, OnBackgroundChangeListener {

    public static String TAG = MovieNavigationFragment.class.getSimpleName();
    @BindView(R.id.gridView_container)
    VerticalGridView verticalGridView;
    private MovieNavigationVerticalGridViewAdapter adapter;
    private MovieNavigationPresenter presenter;
    private BackgroundPresenter backgroundPresenter;
    private ArrayList<Genre> selectedGenres;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        backgroundPresenter = new BackgroundPresenter(this);
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
    public void onBackgroundChange(String path) {
        super.setBackground(path);
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
                backgroundPresenter,
                totalPages);
        verticalGridView.setHasFixedSize(true);
        verticalGridView.setAdapter(adapter);
    }

    @Override
    public void InternetConnected() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Constant.API.PAGE = 0;
        adapter = null;
        setToolbarSearchButtonVisibility(View.VISIBLE);
    }

    @Override
    public void onSortButtonClicked(Dialog dialog) {
        dialog.dismiss();
        if (adapter != null) {
            adapter.clearAll();
        }
        Constant.API.PAGE = 0;
        presenter.loadMovieData(getContext(), selectedGenres);

    }

    @Override
    public void onSearchButtonClicked() {

    }
}

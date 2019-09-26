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


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if(rootView == null){
            rootView = inflater.inflate(R.layout.fragment_base, container, false);
        }
        return rootView;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        super.initViews();
        super.setToolbarView(this);
        super.setToolbarSearchButtonVisibility(View.INVISIBLE);

        if(this.getArguments() != null){
            genres = (ArrayList<Genre>) this.getArguments().getSerializable(rootView.getContext().getString(R.string.genres));
        }

        verticalGridView = view.findViewById(R.id.gridView_container);
        verticalGridView.setNumColumns(7);
        presenter = new MovieNavigationPresenter(this);
        presenter.updateMovieNavigationGridView(getContext(),genres);
        verticalGridView.setItemSpacing(16);
        verticalGridView.setFocusDrawingOrderEnabled(true);
        setGenreTitle();
    }




    @Override
    public void updateMovieNavigationGridView(ArrayList<Movie> movies) {
        if(adapter != null){
            adapter.updateMovieList(movies);
        }else {
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
        while (genreIterator.hasNext()){
            genreTitle.append(genreIterator.next().getName()).append(" - ");
        }
        genreTitle.replace(genreTitle.length()-3, genreTitle.length()-1, "");

        super.setTitle(genreTitle);
    }

    private void startRecyclerViewAdapter(ArrayList<Movie> movies){
        adapter = new MovieNavigationVerticalGridViewAdapter(movies, getActivity(), presenter, genres);
        verticalGridView.setHasFixedSize(true);
        verticalGridView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }




    @Override
    public void onDestroy() {
        super.onDestroy();
        Constant.API.PAGE = 0;
        adapter.clearAll();
        super.setToolbarSearchButtonVisibility(View.VISIBLE);
    }


    public MovieNavigationPresenter getPresenter(){
        return presenter;
    }


    @Override
    public void onSortButtonClicked(Dialog dialog) {
        dialog.dismiss();
        adapter.clearAll();
        Constant.API.PAGE = 0;
        presenter.updateMovieNavigationGridView(getContext(), genres);
    }

    public Bundle getAdaptersBundle(){
        return adapter.getBundle();
    }
}

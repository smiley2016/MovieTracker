package com.smartsoft.movietracker.view.navigation;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.leanback.widget.VerticalGridView;

import com.smartsoft.movietracker.MainActivity;
import com.smartsoft.movietracker.R;
import com.smartsoft.movietracker.model.movie.Movie;
import com.smartsoft.movietracker.presenter.MovieNavigationPresenter;
import com.smartsoft.movietracker.utils.Constant;

import java.util.ArrayList;
import java.util.Objects;

public class MovieNavigationFragment extends Fragment implements MovieNavigationPresenter.MovieNavigationInterface {

    public static String TAG = MovieNavigationFragment.class.getSimpleName();
    private VerticalGridView verticalGridView;
    private MovieNavigationVerticalGridViewAdapter adapter;
    private MovieNavigationPresenter presenter;
    private View rootView;
    private ArrayList<Integer> genreIds = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if(rootView == null){
            rootView = inflater.inflate(R.layout.movie_navigation_fragment, container, false);
        }
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(this.getArguments() != null){
            genreIds = this.getArguments().getIntegerArrayList("genreIds");
        }
        verticalGridView = view.findViewById(R.id.movie_navigation_gridView);
        verticalGridView.setNumColumns(7);
        presenter = new MovieNavigationPresenter(this);
        presenter.updateMovieNavigationGridView(getContext(),genreIds);
        verticalGridView.setItemSpacing(16);

    }


    @Override
    public void updateMovieNavigationGridView(ArrayList<Movie> movies) {
        if(adapter != null){
            if(Constant.MovieNavigationFragment.sortFromMovieNavFragment){
                adapter.clearAll();
                adapter.updateMovieList(movies);
                Constant.MovieNavigationFragment.sortFromMovieNavFragment = false;
                return;
            }

            adapter.updateMovieList(movies);
        }else {
            startRecyclerViewAdapter(movies);
        }


    }

    public MovieNavigationPresenter getPresenter(){
        return presenter;
    }

    public void startRecyclerViewAdapter(ArrayList<Movie> movies){
        adapter = new MovieNavigationVerticalGridViewAdapter(movies, getActivity(), presenter, genreIds);
        verticalGridView.setHasFixedSize(true);
        verticalGridView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        ((MainActivity) Objects.requireNonNull(getActivity())).setInvisibleSearchIcon();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Constant.API.PAGE = 0;
        adapter.clearAll();
        ((MainActivity) Objects.requireNonNull(getActivity())).setVisibleSearchIcon();
        ((MainActivity)Objects.requireNonNull(getActivity())).setBackground(getActivity().getDrawable(R.drawable.background));
    }






}

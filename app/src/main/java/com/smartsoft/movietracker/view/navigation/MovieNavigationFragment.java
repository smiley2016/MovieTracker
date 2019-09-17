package com.smartsoft.movietracker.view.navigation;

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
import com.smartsoft.movietracker.model.movie.Movie;
import com.smartsoft.movietracker.presenter.MovieNavigationPresenter;
import com.smartsoft.movietracker.service.BaseFragmentComponentSettings;
import com.smartsoft.movietracker.utils.Constant;
import com.smartsoft.movietracker.view.BaseFragment;

import java.util.ArrayList;

public class MovieNavigationFragment extends BaseFragment implements MovieNavigationPresenter.MovieNavigationInterface, ToolbarListener {

    public static String TAG = MovieNavigationFragment.class.getSimpleName();
    private VerticalGridView verticalGridView;
    private MovieNavigationVerticalGridViewAdapter adapter;
    private MovieNavigationPresenter presenter;
    private ArrayList<Integer> genreIds = new ArrayList<>();
    private View rootView;


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
            adapter.updateMovieList(movies);
        }else {
            startRecyclerViewAdapter(movies);
        }


    }

    @Override
    public void setBackground(String path) {
        BaseFragmentComponentSettings.getInstance().setBackground(path);
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
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Constant.API.PAGE = 0;
        adapter.clearAll();
        BaseFragmentComponentSettings.getInstance().setVisibleSearchIcon(View.VISIBLE);
        BaseFragmentComponentSettings.getInstance().setBackground(getContext().getDrawable(R.drawable.background));

    }


    public MovieNavigationPresenter getPresenter(){
        return presenter;
    }


    @Override
    public void onSortButtonClicked() {
        adapter.clearAll();
        Constant.API.PAGE = 0;
        presenter.updateMovieNavigationGridView(getContext(), genreIds);
    }
}

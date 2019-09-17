package com.smartsoft.movietracker.view.home;

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
import androidx.fragment.app.Fragment;
import androidx.leanback.widget.VerticalGridView;

import com.smartsoft.movietracker.R;
import com.smartsoft.movietracker.interfaces.BaseFragmentInterface;
import com.smartsoft.movietracker.interfaces.ToolbarListener;
import com.smartsoft.movietracker.model.genre.Genre;
import com.smartsoft.movietracker.presenter.BaseFragmentPresenter;
import com.smartsoft.movietracker.presenter.GenreSelectorPresenter;
import com.smartsoft.movietracker.service.BaseFragmentComponentSettings;
import com.smartsoft.movietracker.utils.Constant;
import com.smartsoft.movietracker.utils.FragmentNavigation;
import com.smartsoft.movietracker.utils.ToolbarDialog;
import com.smartsoft.movietracker.view.BaseFragment;

import java.util.ArrayList;

public class GenreSelectorFragment extends BaseFragment implements GenreSelectorPresenter.GenreSelectorInterface, ToolbarListener {

    private VerticalGridView verticalGridView;
    private VerticalGridViewGenreAdapter adapter;
    private GenreSelectorPresenter presenter = new GenreSelectorPresenter();
    private static final String TAG = GenreSelectorFragment.class.getSimpleName();

    private ImageView search;
    private ImageView settings;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.updateGenres(this);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if(rootView == null){
            rootView = inflater.inflate(R.layout.fragment_home, container, false);
        }
        return rootView;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        verticalGridView = view.findViewById(R.id.movie_genres_gridView);
        verticalGridView.setNumColumns(Constant.HomeFragment.COLUMN_NUM);
        verticalGridView.setItemSpacing(16);

        ToolbarDialog dialog = new ToolbarDialog(this);

        settings = getActivity().findViewById(R.id.toolbar_settings);
        settings.setOnClickListener(view1 -> dialog.startToolbarSettingsDialog(getContext()));

        search = getActivity().findViewById(R.id.toolbar_search);
        search.setOnClickListener(view2 -> {
            FragmentNavigation.getInstance().showMovieNavigationFragment();
            setVisibleSearchIcon(View.INVISIBLE);
        });


    }

    @Override
    public void onResume() {
        super.onResume();
        if(adapter != null ){
            Log.e(TAG, "onResume: "+adapter.getBundle());
            adapter.setList();
        }
    }

    @Override
    public void updateGenres(ArrayList<Genre> genre) {
        adapter = new VerticalGridViewGenreAdapter(getActivity(), genre);
        verticalGridView.setHasFixedSize(true);
        verticalGridView.setAdapter(adapter);

    }

    public Bundle getAdapterBundle(){
        return adapter.getBundle();
    }

    public void setAdapterBundle(){
        adapter.setBundle();
    }


    @Override
    public void onSortButtonClicked() {

    }
}

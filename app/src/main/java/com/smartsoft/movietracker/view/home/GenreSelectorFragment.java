package com.smartsoft.movietracker.view.home;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.leanback.widget.VerticalGridView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.smartsoft.movietracker.R;
import com.smartsoft.movietracker.model.genre.Genre;
import com.smartsoft.movietracker.presenter.GenreSelectorPresenter;
import com.smartsoft.movietracker.utils.Constant;

import java.util.ArrayList;

public class GenreSelectorFragment extends Fragment implements GenreSelectorPresenter.View {

    private VerticalGridView verticalGridView;
    private VerticalGridViewGenreAdapter adapter;
    private ArrayList<Genre> list = new ArrayList<>();
    private GenreSelectorPresenter presenter = new GenreSelectorPresenter();
    private View rootView;
    private static final String TAG = GenreSelectorFragment.class.getSimpleName();


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

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        presenter.updateGenres(this);
        Log.e("3ss",list.size()+"");
    }

    @Override
    public void updateGenres(ArrayList<Genre> genre) {
        list.addAll(genre);
        adapter = new VerticalGridViewGenreAdapter(getContext(), list);
        verticalGridView.setHasFixedSize(true);
        verticalGridView.setAdapter(adapter);

    }
}

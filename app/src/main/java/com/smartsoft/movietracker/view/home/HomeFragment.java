package com.smartsoft.movietracker.view.home;

import android.content.Context;
import android.os.Bundle;
import android.telecom.Connection;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.leanback.app.VerticalGridFragment;
import androidx.leanback.app.VerticalGridSupportFragment;
import androidx.leanback.widget.ArrayObjectAdapter;
import androidx.leanback.widget.VerticalGridPresenter;
import androidx.leanback.widget.VerticalGridView;
import androidx.recyclerview.widget.GridLayoutManager;

import com.smartsoft.movietracker.MainActivity;
import com.smartsoft.movietracker.R;
import com.smartsoft.movietracker.model.Genre;
import com.smartsoft.movietracker.model.GenreResult;
import com.smartsoft.movietracker.model.Movie;
import com.smartsoft.movietracker.presenter.home.HomePresenter;
import com.smartsoft.movietracker.utils.Constant;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public class HomeFragment extends Fragment implements HomePresenter.View {

    VerticalGridView verticalGridView;
    VerticalGridViewGenreAdapter adapter;
    ArrayList<Genre> list = new ArrayList<>();
    private HomePresenter presenter = new HomePresenter();
    private View rootView;
    private static final String TAG = HomeFragment.class.getSimpleName();


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
    public void onAttach(Context context) {
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

package com.smartsoft.movietracker.view.genre;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.leanback.widget.VerticalGridView;

import com.smartsoft.movietracker.R;
import com.smartsoft.movietracker.interfaces.ToolbarListener;
import com.smartsoft.movietracker.model.genre.Genre;
import com.smartsoft.movietracker.presenter.GenreSelectorPresenter;
import com.smartsoft.movietracker.utils.Constant;
import com.smartsoft.movietracker.utils.FragmentNavigation;
import com.smartsoft.movietracker.view.BaseFragment;

import java.util.ArrayList;

public class GenreSelectorFragment extends BaseFragment implements GenreSelectorPresenter.GenreSelectorInterface, ToolbarListener {

    private VerticalGridView verticalGridView;
    private VerticalGridViewGenreAdapter adapter;
    private static final String TAG = GenreSelectorFragment.class.getSimpleName();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        GenreSelectorPresenter presenter = new GenreSelectorPresenter();
        presenter.updateGenres(this);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_base, container, false);
        }
        initViews();
        initEmitters();
        setToolbarView(this);
        setTitle(rootView.getContext().getString(R.string.choose_genre_textView));
        initGridView();
        return rootView;

    }

    private void initGridView() {
        verticalGridView = rootView.findViewById(R.id.gridView_container);
        verticalGridView.setNumColumns(Constant.GridView.COLUMN_NUM5);
        verticalGridView.setItemSpacing((int) rootView.getContext().getResources().getDimension(R.dimen.spacing));
    }

    @Override
    public void onResume() {
        super.onResume();
        if (adapter != null) {
            Log.e(TAG, rootView.getContext().getString(R.string.onResume) + adapter.getBundle());
            adapter.setList();

        }
    }

    @Override
    public void InternetConnected() {

    }

    @Override
    public void updateGenres(ArrayList<Genre> genre) {
        adapter = new VerticalGridViewGenreAdapter(getActivity(), genre);
        verticalGridView.setHasFixedSize(true);
        verticalGridView.setAdapter(adapter);

    }

    @Override
    public void onSortButtonClicked(Dialog dialog) {
        dialog.dismiss();
    }

    @Override
    public void onSearchButtonClicked() {
        ArrayList<Genre> selectedGenres;
        selectedGenres = adapter.getSelectedGenres();
        if(selectedGenres.size() > 0){
            Bundle bundle = new Bundle();
            bundle.putSerializable(getString(R.string.selectedGenres), selectedGenres);
            FragmentNavigation.getInstance().showMovieNavigationFragment(bundle);
            return;
        }
        Toast.makeText(rootView.getContext(), R.string.no_selected_genres, Toast.LENGTH_SHORT).show();
    }
}

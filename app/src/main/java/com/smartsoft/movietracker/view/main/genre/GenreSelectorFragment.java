package com.smartsoft.movietracker.view.main.genre;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.leanback.widget.ArrayObjectAdapter;
import androidx.leanback.widget.ItemBridgeAdapter;
import androidx.leanback.widget.PresenterSelector;
import androidx.leanback.widget.SinglePresenterSelector;
import androidx.leanback.widget.VerticalGridView;

import com.smartsoft.movietracker.R;
import com.smartsoft.movietracker.interfaces.ToolbarListener;
import com.smartsoft.movietracker.model.genre.Genre;
import com.smartsoft.movietracker.presenter.GenreSelectorPresenter;
import com.smartsoft.movietracker.utils.Constant;
import com.smartsoft.movietracker.utils.FragmentNavigation;
import com.smartsoft.movietracker.view.main.BaseMainNavigationFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.http.GET;

public class GenreSelectorFragment extends BaseMainNavigationFragment implements GenreSelectorPresenter.GenreSelectorInterface, ToolbarListener {

    private static final String TAG = GenreSelectorFragment.class.getSimpleName();
    @BindView(R.id.gridView_container)
    VerticalGridView verticalGridView;
    private ArrayList<Genre> genreList;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        genreList = new ArrayList<>();
        GenreSelectorPresenter genreSelectorPresenter = new GenreSelectorPresenter();
        genreSelectorPresenter.updateGenres(this);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_base, container, false);
            ButterKnife.bind(this, rootView);
            initViews(this, View.VISIBLE, rootView.getContext().getString(R.string.choose_genre_textView));
        }

        initGridView();
        return rootView;

    }

    private void initGridView() {

        verticalGridView.setNumColumns(Constant.GridView.COLUMN_NUM5);
        verticalGridView.setItemSpacing((int) rootView.getContext().getResources().getDimension(R.dimen.spacing));
    }

    @Override
    public void onInternetConnected() {

    }

    @Override
    public void updateGenres(ArrayList<Genre> genre) {
        GenreGridViewPresenter genreGridViewPresenter = new GenreGridViewPresenter();

        ArrayObjectAdapter objectAdapter = new ArrayObjectAdapter();

        for(Genre it: genre){
            genreList.add(it);
            objectAdapter.add(it);
        }

        PresenterSelector presenterSelector = new SinglePresenterSelector(genreGridViewPresenter);

        ItemBridgeAdapter adapter = new ItemBridgeAdapter();
        adapter.setAdapter(objectAdapter);
        adapter.setPresenter(presenterSelector);

        verticalGridView.setHasFixedSize(true);
        verticalGridView.setAdapter(adapter);

    }


    private ArrayList<Genre> setSelectedGenres() {
        ArrayList<Genre> selectedGenres = new ArrayList<>();
        for(Genre it: genreList){
            if(it.isActivated() && !selectedGenres.contains(it)){
                selectedGenres.add(it);
            }
        }
        return selectedGenres;
    }

    @Override
    public void onSortButtonClicked(Dialog dialog) {
        dialog.dismiss();
    }

    @Override
    public void onSearchButtonClicked() {
        ArrayList<Genre> selectedGenres = setSelectedGenres();
        if (selectedGenres.size() > 0) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(getString(R.string.selectedGenres), selectedGenres);
            FragmentNavigation.getInstance().showMovieNavigationFragment(bundle);
            return;
        }
        Toast.makeText(rootView.getContext(), R.string.no_selected_genres, Toast.LENGTH_SHORT).show();
    }
}

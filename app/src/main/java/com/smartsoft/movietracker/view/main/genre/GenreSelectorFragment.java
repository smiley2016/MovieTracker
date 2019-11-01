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

/**
 * This class is a extended from {@link BaseMainNavigationFragment}
 * and this function handle the {@link Genre}s selection
 * by the user
 */
public class GenreSelectorFragment extends BaseMainNavigationFragment implements GenreSelectorPresenter.GenreSelectorInterface, ToolbarListener {

    /**
     * Reference to the {@link VerticalGridView}
     */
    @BindView(R.id.gridView_container)
    VerticalGridView verticalGridView;

    /**
     * This list holds the downloaded {@link Genre}s from the API
     * because this variable will be passed away to
     * {@link com.smartsoft.movietracker.view.main.navigation.MovieNavigationFragment}
     */
    private ArrayList<Genre> genreList;

    /**
     * Reference to the {@link GenreSelectorFragment}'s presenter
     * who makes the data handling in the background and it is a
     * MVP architectural pattern base class object
     */
    private GenreSelectorPresenter genreSelectorPresenter;

    /**
     * This variable saves all the genres for the
     * for the {@link GenreGridViewPresenter}
     */
    private ArrayObjectAdapter objectAdapter;

    /**
     * Lifecycle function
     * This function create the fragment without view
     *
     * @param savedInstanceState Fragment state
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        genreList = new ArrayList<>();
        objectAdapter = new ArrayObjectAdapter();
        genreSelectorPresenter = new GenreSelectorPresenter();
        genreSelectorPresenter.updateGenres(this);
    }

    /**
     * After the {@link GenreSelectorFragment#onCreate(Bundle)} function calls
     * the app this function to make with {@link LayoutInflater}
     * a view from the {@link R.layout#fragment_base}
     * @param inflater Object that makes view from XML file
     * @param container Where the view will be inflated
     * @param savedInstanceState Fragment state
     * @return The inflated view
     */
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

    /**
     * Inflater function of the {@link #verticalGridView}
     */
    private void initGridView() {

        verticalGridView.setNumColumns(Constant.GridView.COLUMN_NUM5);
        verticalGridView.setItemSpacing((int) rootView.getContext().getResources().getDimension(R.dimen.spacing));
    }

    /**
     * THis function calls when the Internet comes back.
     * Inside the function the app decide if the
     * {@link #objectAdapter} is null and it's size not equal to
     * zero because this means that the internet loss has became
     * before the data loaded
     */
    @Override
    public void onInternetConnected() {
        super.onInternetConnected();
        if (objectAdapter != null && objectAdapter.size() == 0) {
            genreSelectorPresenter.updateGenres(this);
        }
    }

    /**
     * This function initialize the {@link #verticalGridView} of the
     * fragment and fills up the array object adapter with the downloaded
     * {@link Genre}s. Initialize the {@link PresenterSelector} and the
     * {@link ItemBridgeAdapter}
     * @param genre
     */
    @Override
    public void updateGenres(ArrayList<Genre> genre) {
        GenreGridViewPresenter genreGridViewPresenter = new GenreGridViewPresenter();

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

    /**
     * This function calls when the user click on the search
     * button. This function set the {@link Genre#isActivated} variable
     * for each element in the genreList which Genres
     * the user selected
     * @return An {@link ArrayList<Genre>} with the selected genres
     */
    private ArrayList<Genre> setSelectedGenres() {
        ArrayList<Genre> selectedGenres = new ArrayList<>();
        for(Genre it: genreList){
            if(it.isActivated() && !selectedGenres.contains(it)){
                selectedGenres.add(it);
            }
        }
        return selectedGenres;
    }

    /**
     * This function calls when the user set a sort/order type
     * to the downloadable {@link com.smartsoft.movietracker.model.movie.Movie}s
     * @param dialog is used for to dismiss the dialog when program jump to the implementation
     */
    @Override
    public void onSortButtonClicked(Dialog dialog) {
        dialog.dismiss();
    }

    /**
     * When the user clicks on the {@link com.smartsoft.movietracker.view.toolbar.ToolbarView#search}
     * this function calls to make bundle for the
     * {@link com.smartsoft.movietracker.view.main.navigation.MovieNavigationFragment}
     * with the selected genres and the all genres.
     * And calls the Fragment creator, namely {@link FragmentNavigation}
     * to create a new instance from {@link com.smartsoft.movietracker.view.main.navigation.MovieNavigationFragment}
     * and replace the current one to this.
     */
    @Override
    public void onSearchButtonClicked() {
        ArrayList<Genre> selectedGenres = setSelectedGenres();
        if (selectedGenres.size() > 0) {
            Bundle bundle = new Bundle();
            bundle.putSerializable(getString(R.string.selectedGenres), selectedGenres);
            bundle.putSerializable(rootView.getContext().getString(R.string.allGenres), genreList);
            FragmentNavigation.getInstance().showMovieNavigationFragment(bundle);
            return;
        }
        Toast.makeText(rootView.getContext(), R.string.no_selected_genres, Toast.LENGTH_SHORT).show();
    }

    /**
     * This function calls when the user wants to
     * search about a {@link com.smartsoft.movietracker.model.movie.Movie}
     * in this case the app have to make bundle for the new Fragment
     * which will be instantiated
     * In this case the function pass a bundle with the {@link #genreList}
     * and the text ({@link BaseMainNavigationFragment#getSearchText()}) what the user
     * search for
     */
    @Override
    public void onSearch() {
        Bundle bundle = new Bundle();
        bundle.putString(rootView.getContext().getString(R.string.search), super.getSearchText());
        bundle.putSerializable(rootView.getContext().getString(R.string.allGenres), genreList);
        FragmentNavigation.getInstance().showMovieNavigationFragment(bundle);

    }
}

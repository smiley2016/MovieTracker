package com.smartsoft.movietracker.view.detail;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.leanback.widget.ArrayObjectAdapter;
import androidx.leanback.widget.ClassPresenterSelector;
import androidx.leanback.widget.ItemBridgeAdapter;
import androidx.leanback.widget.VerticalGridView;

import com.bumptech.glide.Glide;
import com.smartsoft.movietracker.R;
import com.smartsoft.movietracker.interfaces.DetailPageInterface;
import com.smartsoft.movietracker.model.MovieDetails;
import com.smartsoft.movietracker.model.cast.CastList;
import com.smartsoft.movietracker.model.genre.Genre;
import com.smartsoft.movietracker.model.movie.Movie;
import com.smartsoft.movietracker.model.review.ReviewList;
import com.smartsoft.movietracker.model.video.VideoList;
import com.smartsoft.movietracker.presenter.DetailPagePresenter;
import com.smartsoft.movietracker.utils.Constant;

import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailPageFragment extends Fragment implements DetailPageInterface.DetailPageViewInterface {
    private static final String TAG = DetailPageFragment.class.getName();

    private Movie movie;
    private ArrayList<Genre> genres;
    private ArrayObjectAdapter objectAdapter;
    private DetailPagePresenter dPresenter;
    private View rootView;

    @BindView(R.id.background_image)
    ImageView background;

    @Override
    @SuppressWarnings("unchecked")
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            movie = (Movie) getArguments().getSerializable("movie");
            genres = (ArrayList<Genre>) getArguments().getSerializable(getString(R.string.genres));
            Log.e(TAG, ""+movie);
        }

        dPresenter = new DetailPagePresenter(this);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        if(rootView == null){
            rootView = inflater.inflate(R.layout.fragment_detail_page, container, false);
            ButterKnife.bind(this, rootView);
            initializeViews();
            getAllData();
        }

        return rootView;
    }


    private void getAllData() {
        dPresenter.loadData(movie.getId());
    }

    private void initializeViews() {

        Glide.with(rootView.getContext()).load(Constant.API.IMAGE_ORIGINAL_BASE_URL + movie.getBackdropPath()).into(background);

        ArrayList<String> currentMovieGenres = new ArrayList<>();

        for(int i = 0; i<genres.size(); ++i){
            for(int j = 0; j<movie.getGenreIds().size(); ++j){
                if (movie.getGenreIds().get(j).equals(genres.get(i).getId())){
                    currentMovieGenres.add(genres.get(i).getName());
                }
            }

        }

        VerticalGridView verticalGridView = rootView.findViewById(R.id.detail_page_grid_view);

        objectAdapter = new ArrayObjectAdapter();
        objectAdapter.add(movie);

        MovieVerticalGridPresenter movieVerticalGridPresenter = new MovieVerticalGridPresenter(rootView.getContext(), currentMovieGenres, dPresenter);
        CastVerticalGridPresenter castVerticalGridPresenter = new CastVerticalGridPresenter(rootView.getContext());
        ReviewVerticalGridPresenter reviewVerticalGridPresenter = new ReviewVerticalGridPresenter(rootView.getContext());
        VideoVerticalGridPresenter videoVerticalGridPresenter = new VideoVerticalGridPresenter(rootView.getContext());

        ClassPresenterSelector presenterSelector = new ClassPresenterSelector();
        presenterSelector.addClassPresenter(Movie.class, movieVerticalGridPresenter);
        presenterSelector.addClassPresenter(CastList.class, castVerticalGridPresenter);
        presenterSelector.addClassPresenter(ReviewList.class, reviewVerticalGridPresenter);
        presenterSelector.addClassPresenter(VideoList.class, videoVerticalGridPresenter);

        ItemBridgeAdapter itemBridgeAdapter = new ItemBridgeAdapter(objectAdapter, presenterSelector);
        verticalGridView.setAdapter(itemBridgeAdapter);

    }

    @Override
    public void loadData(MovieDetails movieDetails) {
        CastList cast = new CastList(movieDetails.getCasts());
        ReviewList list = new ReviewList(movieDetails.getReviews());
        VideoList videoList = new VideoList(movieDetails.getVideos());
        objectAdapter.add(cast);
        objectAdapter.add(list);
        objectAdapter.add(videoList);

    }

    @Override
    public void backPressed() {
        Objects.requireNonNull(getActivity()).onBackPressed();
    }

    @Override
    public void onPause() {
        super.onPause();
    }
}

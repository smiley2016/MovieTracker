package com.smartsoft.movietracker.view.detail;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.leanback.widget.ArrayObjectAdapter;
import androidx.leanback.widget.ClassPresenterSelector;
import androidx.leanback.widget.ItemBridgeAdapter;
import androidx.leanback.widget.VerticalGridView;

import com.bumptech.glide.Glide;
import com.smartsoft.movietracker.R;
import com.smartsoft.movietracker.interfaces.DetailPageInterface;
import com.smartsoft.movietracker.model.MovieDetails;
import com.smartsoft.movietracker.model.cast.CastList;
import com.smartsoft.movietracker.model.movie.Movie;
import com.smartsoft.movietracker.model.review.ReviewList;
import com.smartsoft.movietracker.model.video.VideoList;
import com.smartsoft.movietracker.presenter.DetailPagePresenter;
import com.smartsoft.movietracker.utils.Constant;
import com.smartsoft.movietracker.view.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailPageFragment extends BaseFragment implements DetailPageInterface.DetailPageViewInterface {
    private Movie movie;
    private ArrayObjectAdapter objectAdapter;
    private DetailPagePresenter dPresenter;
    private View rootView;

    private MovieDetailPresenter mPresenter;
    private CastPresenter castPresenter;
    private ReviewPresenter reviewPresenter;
    private VideoPresenter videoPresenter;

    private ClassPresenterSelector presenterSelector;
    private ItemBridgeAdapter itemBridgeAdapter;

    @BindView(R.id.background_image)
    ImageView background;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        objectAdapter = new ArrayObjectAdapter();

        presenterSelector = new ClassPresenterSelector();

        mPresenter = new MovieDetailPresenter(getContext());
        castPresenter = new CastPresenter(getContext());
        reviewPresenter = new ReviewPresenter(getContext());
        videoPresenter = new VideoPresenter(getContext());

        presenterSelector.addClassPresenter(Movie.class, mPresenter);
        presenterSelector.addClassPresenter(CastList.class, castPresenter);
        presenterSelector.addClassPresenter(ReviewList.class, reviewPresenter);
        presenterSelector.addClassPresenter(VideoList.class, videoPresenter);

        dPresenter = new DetailPagePresenter(this);


        itemBridgeAdapter = new ItemBridgeAdapter(objectAdapter, presenterSelector);

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(rootView == null){
            rootView = inflater.inflate(R.layout.fragment_detail_page, container, false);
        }

        ButterKnife.bind(this, rootView);

        if (getArguments() != null) {
            movie = (Movie) getArguments().getSerializable("movie");

            objectAdapter.add(movie);
        }

        initViews();


        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getAllData();


        VerticalGridView verticalGridView = rootView.findViewById(R.id.detail_page_grid_view);
        verticalGridView.setAdapter(itemBridgeAdapter);
    }

    private void getAllData() {
       dPresenter.loadData(movie.getId());
    }

    protected void initViews() {
        Glide.with(rootView.getContext()).load(Constant.API.IMAGE_ORIGINAL_BASE_URL + movie.getBackdropPath()).into(background);


    }

    @Override
    public void loadData(MovieDetails movieDetails) {
        objectAdapter.add(movieDetails.getCasts());
        objectAdapter.add(movieDetails.getReviews());
        objectAdapter.add(movieDetails.getVideos());
    }




}

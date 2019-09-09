package com.smartsoft.movietracker.view.detail;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.leanback.widget.HorizontalGridView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.smartsoft.movietracker.R;
import com.smartsoft.movietracker.interfaces.detail.DetailPageInterface;
import com.smartsoft.movietracker.model.cast.Cast;
import com.smartsoft.movietracker.model.movie.Movie;
import com.smartsoft.movietracker.model.review.Review;
import com.smartsoft.movietracker.model.video.Video;
import com.smartsoft.movietracker.presenter.DetailPagePresenter;
import com.smartsoft.movietracker.utils.Constant;

import java.io.Serializable;
import java.util.ArrayList;

public class DetailPageActivity extends FragmentActivity implements DetailPageInterface.DetailPageViewInterface, Serializable {

    private DetailPagePresenter presenter;
    private HorizontalGridView castGridView, reviewGridView, videoGridView;
    private DetailPageCastAdapter cAdapter;
    private DetailPageReviewAdapter rAdapter;
    private DetailPageVideoAdapter sAdapter;
    private TextView programTitle, details, plot;
    private Movie movie;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_page);
        initViews();
    }

    private void initViews() {

        movie = (Movie) getIntent().getSerializableExtra("Movie");

        ScrollView view = findViewById(R.id.detail_page_scroll_view);
        Glide.with(this).load(Constant.Common.IMAGE_BASE_URL+movie.getBackdropPath()).into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                view.setBackground(resource);
            }
        });

        programTitle = findViewById(R.id.program_Title);
        programTitle.setText(movie.getTitle());

        details = findViewById(R.id.movie_Description);
        details.setText(String.format("Year %s | IMDb%s", movie.getReleaseDate(), movie.getVoteAverage()));

        plot = findViewById(R.id.plot);
        plot.setText(movie.getOverview());

        castGridView = findViewById(R.id.cast_Actors);
        castGridView.setNumRows(1);
        castGridView.setItemSpacing(8);

        reviewGridView = findViewById(R.id.review_GridView);
        reviewGridView.setItemSpacing(8);
        reviewGridView.setNumRows(8);

        videoGridView = findViewById(R.id.videos_GridView);
        videoGridView.setItemSpacing(8);
        videoGridView.setNumRows(1);

        startGridViewAdapters();


    }

    public void startGridViewAdapters(){


        presenter = new DetailPagePresenter(this);

        cAdapter = new DetailPageCastAdapter(this);
        presenter.downloadCast(movie.getId());

        rAdapter = new DetailPageReviewAdapter(this);
        presenter.downloadReviews(movie.getId());

        sAdapter = new DetailPageVideoAdapter(this);
        presenter.downloadVideos(movie.getId());

        castGridView.setAdapter(cAdapter);
        reviewGridView.setAdapter(rAdapter);
        videoGridView.setAdapter(sAdapter);

    }

    @Override
    public void updateCast(ArrayList<Cast> cast) {
        cAdapter.addAllToList(cast);
    }

    @Override
    public void updateReviews(ArrayList<Review> reviews) {
       rAdapter.addAllToList(reviews);
    }

    @Override
    public void updateVideos(ArrayList<Video> videos) {
        sAdapter.addAllToList(videos);
    }
}

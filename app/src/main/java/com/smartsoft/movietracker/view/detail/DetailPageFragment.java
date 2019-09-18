package com.smartsoft.movietracker.view.detail;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.leanback.widget.HorizontalGridView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.smartsoft.movietracker.R;
import com.smartsoft.movietracker.interfaces.DetailPageInterface;
import com.smartsoft.movietracker.model.cast.Cast;
import com.smartsoft.movietracker.model.movie.Movie;
import com.smartsoft.movietracker.model.review.Review;
import com.smartsoft.movietracker.model.video.Video;
import com.smartsoft.movietracker.presenter.DetailPagePresenter;
import com.smartsoft.movietracker.utils.Constant;
import com.smartsoft.movietracker.view.BaseFragment;

import java.util.ArrayList;

public class DetailPageFragment extends BaseFragment implements DetailPageInterface.DetailPageViewInterface{

    private HorizontalGridView castGridView, reviewGridView, videoGridView;
    private DetailPageCastAdapter cAdapter;
    private DetailPageReviewAdapter rAdapter;
    private DetailPageVideoAdapter sAdapter;
    private TextView plotTextView;
    private String plot, releaseDate, title, backDropPath;
    private int movieId;
    private Double voteAverage;
    private Bundle bundle;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(rootView == null){
            rootView = inflater.inflate(R.layout.fragment_detail_page, container, false);
        }
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(getArguments() != null){

            title = getArguments().getString("movieTitle");
            plot = getArguments().getString("moviePlot");
            voteAverage = getArguments().getDouble("movieRate");
            releaseDate = getArguments().getString("movieReleaseDate");
            backDropPath = getArguments().getString("movieBackDropPath");
            movieId = getArguments().getInt("movieId");
        }
        this.initViews();
    }

    protected void initViews() {

        ImageView view = rootView.findViewById(R.id.background_image);
        Glide.with(this).load(Constant.API.IMAGE_ORIGINAL_BASE_URL+backDropPath).into(new SimpleTarget<Drawable>() {
            @Override
            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                view.setBackground(resource);
            }
        });

        TextView programTitle = rootView.findViewById(R.id.program_Title);
        programTitle.setText(title);

        TextView details = rootView.findViewById(R.id.movie_Description);
        details.setText(String.format("Year %s | IMDb%s", releaseDate, voteAverage));


        plotTextView = rootView.findViewById(R.id.plot);
        plotTextView.setText(plot);

        Button addWatchList = rootView.findViewById(R.id.add_watchlist_button);
        addWatchList.requestFocus();
        addWatchList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(plotTextView.getMaxLines() == 3){
                    addWatchList.setForeground(rootView.getContext().getDrawable(R.drawable.collapse_icon));
                    plotTextView.setMaxLines(Integer.MAX_VALUE);

                }else{
                    addWatchList.setForeground(rootView.getContext().getDrawable(R.drawable.add_watchlist_icon));
                    plotTextView.setMaxLines(3);
                }

            }
        });

        castGridView = rootView.findViewById(R.id.cast_Actors);
        castGridView.setNumRows(1);
        castGridView.setVerticalSpacing(8);

        reviewGridView = rootView.findViewById(R.id.review_GridView);
        reviewGridView.setItemSpacing(8);
        reviewGridView.setVerticalSpacing(8);

        videoGridView = rootView.findViewById(R.id.videos_GridView);
        videoGridView.setNumRows(1);
        videoGridView.setItemSpacing(8);

        startGridViewAdapters();


    }

    public void startGridViewAdapters(){


        DetailPagePresenter presenter = new DetailPagePresenter(this);

        cAdapter = new DetailPageCastAdapter(rootView.getContext());
        presenter.downloadCast(movieId);

        rAdapter = new DetailPageReviewAdapter(rootView.getContext());
        presenter.downloadReviews(movieId);

        sAdapter = new DetailPageVideoAdapter(rootView.getContext());
        presenter.downloadVideos(movieId);

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

    public Bundle getVideoAdaptersBundle(){
        return sAdapter.getBundle();
    }
}

package com.smartsoft.movietracker.interfaces;

import com.smartsoft.movietracker.model.MovieDetails;
import com.smartsoft.movietracker.model.cast.Cast;
import com.smartsoft.movietracker.model.review.Review;
import com.smartsoft.movietracker.model.video.Video;

import java.util.ArrayList;

public interface DetailPageInterface {

    interface DetailPagePresenterInterface{
        void loadData(int movieId);
    }

    interface DetailPageViewInterface{
        void loadData(MovieDetails movieDetails);
    }
}

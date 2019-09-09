package com.smartsoft.movietracker.interfaces.detail;

import com.smartsoft.movietracker.model.cast.Cast;
import com.smartsoft.movietracker.model.review.Review;
import com.smartsoft.movietracker.model.video.Video;

import java.util.ArrayList;

public interface DetailPageInterface {

    public interface DetailPagePresenterInterface{
        void downloadCast(int movie_id);
        void downloadReviews(int movie_id);
        void downloadVideos(int movie_id);
    }

    public interface DetailPageViewInterface{
        void updateCast(ArrayList<Cast> cast);
        void updateReviews(ArrayList<Review> reviews);
        void updateVideos(ArrayList<Video> videos);
    }
}

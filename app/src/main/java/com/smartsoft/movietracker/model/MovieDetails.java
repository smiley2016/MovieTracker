package com.smartsoft.movietracker.model;

import com.smartsoft.movietracker.model.cast.Cast;
import com.smartsoft.movietracker.model.review.Review;
import com.smartsoft.movietracker.model.video.Video;

import java.util.ArrayList;

public class MovieDetails {

    private ArrayList<Cast> casts;
    private ArrayList<Review> reviews;
    private ArrayList<Video> videos;

    public MovieDetails(ArrayList<Cast> casts, ArrayList<Review> reviews, ArrayList<Video> videos) {
        this.casts = casts;
        this.reviews = reviews;
        this.videos = videos;
    }

    public ArrayList<Cast> getCasts() {
        return casts;
    }

    public ArrayList<Review> getReviews() {
        return reviews;
    }

    public ArrayList<Video> getVideos() {
        return videos;
    }
}

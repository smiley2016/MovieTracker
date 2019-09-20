package com.smartsoft.movietracker.model;

import com.smartsoft.movietracker.model.cast.Cast;
import com.smartsoft.movietracker.model.review.Review;
import com.smartsoft.movietracker.model.video.Video;

import java.util.ArrayList;

public class MovieDetails {

    private ArrayList<Cast> casts = new ArrayList<>();
    private ArrayList<Review> reviews = new ArrayList<>();
    private ArrayList<Video> videos = new ArrayList<>();

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

    public void setCasts(ArrayList<Cast> casts) {
        this.casts = casts;
    }

    public void setReviews(ArrayList<Review> reviews) {
        this.reviews = reviews;
    }

    public void setVideos(ArrayList<Video> videos) {
        this.videos = videos;
    }
}

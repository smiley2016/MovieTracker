package com.smartsoft.movietracker.model.review;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.smartsoft.movietracker.model.movie.MovieResult;

import java.util.ArrayList;

public class ReviewResult {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("page")
    @Expose
    private Integer page;
    @SerializedName("results")
    @Expose
    private ArrayList<Review> reviews;

    public ReviewResult(Integer id, Integer page, ArrayList<Review> reviews) {
        this.id = id;
        this.page = page;
        this.reviews = reviews;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public ArrayList<Review> getReviews() {
        return reviews;
    }

    public void setReviews(ArrayList<Review> movieResults) {
        this.reviews = movieResults;
    }
}

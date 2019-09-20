package com.smartsoft.movietracker.model.review;

import java.util.ArrayList;

public class ReviewList {

    private ArrayList<Review> review;

    public ReviewList(ArrayList<Review> review) {
        this.review = review;
    }

    public ArrayList<Review> getReview() {
        return review;
    }
}

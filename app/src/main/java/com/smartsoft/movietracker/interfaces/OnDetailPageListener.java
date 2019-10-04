package com.smartsoft.movietracker.interfaces;

import com.smartsoft.movietracker.model.MovieDetails;

public interface OnDetailPageListener {

    void displayData(MovieDetails movieDetails);

    void backPressed();
}

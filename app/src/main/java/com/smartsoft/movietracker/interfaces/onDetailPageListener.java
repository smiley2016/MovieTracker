package com.smartsoft.movietracker.interfaces;

import com.smartsoft.movietracker.model.MovieDetails;

public interface onDetailPageListener {

    void loadData(MovieDetails movieDetails);

    void backPressed();
}

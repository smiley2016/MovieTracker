package com.smartsoft.movietracker.interfaces;

import com.smartsoft.movietracker.model.MovieDetails;

public interface DetailPageInterface {

        void loadData(MovieDetails movieDetails);

        void backPressed();
}

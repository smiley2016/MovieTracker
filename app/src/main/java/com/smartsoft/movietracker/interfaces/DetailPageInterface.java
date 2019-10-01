package com.smartsoft.movietracker.interfaces;

import com.smartsoft.movietracker.model.MovieDetails;

public interface DetailPageInterface {

    interface DetailPagePresenterInterface {
        void loadData(int movieId);

        void backPressed();
    }

    interface DetailPageViewInterface {
        void loadData(MovieDetails movieDetails);

        void backPressed();

    }
}

package com.smartsoft.movietracker.view.detail;

public interface DetailVideoGridInterface {

    interface VideoGridView {
        void startPlayerActivity(String videoId);
    }

    interface VideoGridIntPresenter {
        void startPlayerActivity(String videoId);
    }
}

package com.smartsoft.movietracker.interfaces;

public interface DetailVideoGridInterface {

    interface VideoGridView {
        void startPlayer(String videoId);
    }

    interface VideoGridIntPresenter {
        void startPlayer(String videoId);
    }
}

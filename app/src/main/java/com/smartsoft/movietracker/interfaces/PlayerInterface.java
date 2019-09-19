package com.smartsoft.movietracker.interfaces;

public interface PlayerInterface {

    interface PlayerView{
        void setPlaylistVisibility(int visibility);
        void startNewVideo(int position);
    }

    interface PlayerPresenter{
        void setPlayListVisibility(int visibility);
        void startNewVideo(int position);
    }
}

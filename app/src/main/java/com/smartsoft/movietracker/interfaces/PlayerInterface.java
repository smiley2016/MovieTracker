package com.smartsoft.movietracker.interfaces;

public interface PlayerInterface {

    public interface PlayerView{
        void setPlaylistVisibility(int visibility);
    }

    public interface PlayerPresenter{
        void setPlayListVisibility(int visibility);
    }
}

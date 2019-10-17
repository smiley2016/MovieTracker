package com.smartsoft.movietracker.presenter;

import com.smartsoft.movietracker.interfaces.PlayerInterface;

public class PlayerPresenter {

    private PlayerInterface playerInterface;

    public PlayerPresenter(PlayerInterface playerInterface) {
        this.playerInterface = playerInterface;
    }


    public void setPlayListVisibility(int visibility) {
        playerInterface.setPlaylistVisibility(visibility);
    }

    public void startNewVideo(int position) {
        playerInterface.startNewVideo(position);
    }


}

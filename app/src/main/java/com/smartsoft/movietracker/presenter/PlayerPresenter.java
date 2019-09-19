package com.smartsoft.movietracker.presenter;

import com.smartsoft.movietracker.interfaces.PlayerInterface;

public class PlayerPresenter implements PlayerInterface.PlayerPresenter {

    private PlayerInterface.PlayerView playerInterface;

    public PlayerPresenter(PlayerInterface.PlayerView playerInterface) {
        this.playerInterface = playerInterface;
    }

    @Override
    public void setPlayListVisibility(int visibility) {
        playerInterface.setPlaylistVisibility(visibility);
    }

    @Override
    public void startNewVideo(int position) {
        playerInterface.startNewVideo(position);
    }


}

package com.smartsoft.movietracker.presenter;

import com.smartsoft.movietracker.interfaces.PlayerInterface;

/**
 * @see PlayerPresenter handle some Player method
 * @see #setPlayListVisibility(int)
 * @see #startNewVideo(int)
 */
public class PlayerPresenter {

    /**
     * PlayerInterface variable to communicate with
     * {@link com.smartsoft.movietracker.view.player.PlayerFragment}
     */
    private PlayerInterface playerInterface;

    /**
     * Class constructor
     * Save the
     * @see #playerInterface
     */
    public PlayerPresenter(PlayerInterface playerInterface) {
        this.playerInterface = playerInterface;
    }

    /**
     * Set the {@link com.google.android.exoplayer2.SimpleExoPlayer}
     * PlayList on the corresponded visibility
     * @param visibility is a variable that contains visibility properties
     */
    public void setPlayListVisibility(int visibility) {
        playerInterface.setPlaylistVisibility(visibility);
    }

    /**
     * Call the interface implementation in the respected Fragment to
     * Start new video from list which has an index like
     * @param position (is contain the selected video index).
     */
    public void startNewVideo(int position) {
        playerInterface.startNewVideo(position);
    }


}

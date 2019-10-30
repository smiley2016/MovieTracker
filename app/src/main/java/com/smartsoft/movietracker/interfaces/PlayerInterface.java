package com.smartsoft.movietracker.interfaces;

/**
 * This interface handle the playList visibility in
 * @see #setPlaylistVisibility(int) function.
 * @see #startNewVideo(int) function handle to start the new
 * video what the user is selected.
 */

public interface PlayerInterface {

    void setPlaylistVisibility(int visibility);

    void startNewVideo(int position);

}

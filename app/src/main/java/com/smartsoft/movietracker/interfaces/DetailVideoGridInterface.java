package com.smartsoft.movietracker.interfaces;

/**
 * DetailVideoGridInterface handle the start of the
 * videoPlayer with a respective video. This is used in
 * VideoHorizontalGridPresenter) and in VideoVerticalGridPresenter
 */

public interface DetailVideoGridInterface {

        /**
         * @param videoId is used for determine exactly the video what will be play
         *                by the {@link com.google.android.exoplayer2.SimpleExoPlayer}
         * @see #startPlayer(String) is a funtcion
         * where determinate the position of video which
         * is selected by the user. This position will be
         * the playIndex for the player.
         * With {@link android.os.Bundle} the program passes the playIndex
         * and the extracted youtubeLinks to
         * to Fragment Instance creator
         * {@link com.smartsoft.movietracker.utils.FragmentNavigation }
         * which class pass the bundle to the newly created
         * {@link com.smartsoft.movietracker.view.player.PlayerFragment}
         */
        void startPlayer(String videoId);

}

package com.smartsoft.movietracker.interfaces;

import com.smartsoft.movietracker.model.MovieDetails;

/**
 * {@link OnDetailPageListener} interface handle
 * the youtube links extraction into download Url
 * and fills up the objectAdapter with the downloaded data
 * @see #backPressed() function is used for cancel the
 * asyncTask if it is running when the back is pressed
 */

public interface OnDetailPageListener {

    /**
     * displayData() function is used for
     * create new object instances from
     * {@link com.smartsoft.movietracker.model.cast.CastList}
     * {@link com.smartsoft.movietracker.model.review.ReviewList}
     * {@link com.smartsoft.movietracker.model.video.VideoList}
     * which classes is used for save the
     * {@link java.util.ArrayList} from the
     * {@link com.smartsoft.movietracker.model.cast.Cast}
     * {@link com.smartsoft.movietracker.model.review.Review}
     * {@link com.smartsoft.movietracker.model.video.Video}
     * models.
     *
     *<p> From TheMovieDbApi we are getting the URI's of the videos but for the ExoPlayer we need
     * the downloaded URLs. In that case we are using a third party library
     * {@link at.huber.youtubeExtractor.YouTubeExtractor}</p>
     *
     * The Youtube extractor is an async Task execution which at the gives to us
     * the perfect download URL for every single video.
     *
     * At the final of the function we are fill up the object adapter
     *
     *
     *  @param movieDetails is used as a current storage in what
     *      *     we temporary saving the downloaded data (more specifically in this variable we
     *      *                     are saving the arrayList of the models)
     */

    void displayData(MovieDetails movieDetails);

    /**
     * This function is implemented because is there a possibility
     * when the user just want to leave the
     * {@link com.smartsoft.movietracker.view.detail.DetailPageFragment}
     * and in this case we have to stop the
     * {@link at.huber.youtubeExtractor.YouTubeExtractor}
     */
    void backPressed();
}

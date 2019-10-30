package com.smartsoft.movietracker.presenter;

import android.util.Log;

import com.smartsoft.movietracker.interfaces.OnDetailPageListener;
import com.smartsoft.movietracker.model.MovieDetails;
import com.smartsoft.movietracker.service.ApiController;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * @see DetailPagePresenter is a presenter class
 * it communicate with his/her own Fragment
 * {@link com.smartsoft.movietracker.view.detail.DetailPageFragment}
 * This class are using to do every API query that what need's in the DetailPageFragment.
 */

public class DetailPagePresenter {
    /**
     * @see #TAG is used for {@link Log}. It saves the class name.
     */
    private static final String TAG = DetailPagePresenter.class.getName();

    /**
     * @see #detailPageViewInterface is used for call the
     * interface functions because with this we can pass the downloaded data to
     * {@link com.smartsoft.movietracker.view.detail.DetailPageFragment}
     */
    private OnDetailPageListener detailPageViewInterface;

    /**
     * Class Constructor.
     * @param detailPageViewInterface is for to communicate between
     * {@link com.smartsoft.movietracker.view.detail.DetailPageFragment}
     * {@link DetailPagePresenter} and send data to each other
     */
    public DetailPagePresenter(OnDetailPageListener detailPageViewInterface) {
        this.detailPageViewInterface = detailPageViewInterface;
    }

    /** @see #loadData(int) is used for download the
     * {@link com.smartsoft.movietracker.model.cast.Cast}
     * {@link com.smartsoft.movietracker.model.review.Review}
     * {@link com.smartsoft.movietracker.model.video.Video}
     * data from the API.
     * More specifically in this function we are using {@link Observable} and it's zipper method
     * because in this class we need to download Cast, Review and video list one after the other and we need to wait
     * for the stream finishes the query. By this fact the program has to save in an object all these lists
     * and here it uses {@link MovieDetails} class where can save all three lists.
     * At final it calls the interface displayData() funtion which is implemented in
     * {@link com.smartsoft.movietracker.view.detail.DetailPageFragment}.
     * @param movieId contain the ID which was used for downloading
     *                the casts, reviews and videos for the current movie.
     */
    public void loadData(int movieId) {
        Observable.zip(
                ApiController.getInstance().getCast(movieId),
                ApiController.getInstance().getReviews(movieId),
                ApiController.getInstance().getVideos(movieId), MovieDetails::new)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<MovieDetails>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(MovieDetails movieDetails) {
                        Log.e(TAG, "DetailPagePresenter" + movieDetails);

                        detailPageViewInterface.displayData(movieDetails);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "DetailPagePresenter", e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * This little function stop the {@link at.huber.youtubeExtractor.YouTubeExtractor}
     * async task when the user click on the back button if this task is running.
     */
    public void backPressed() {
        detailPageViewInterface.backPressed();
    }


}

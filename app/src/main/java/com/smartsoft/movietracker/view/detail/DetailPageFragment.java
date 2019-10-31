package com.smartsoft.movietracker.view.detail;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.leanback.widget.ArrayObjectAdapter;
import androidx.leanback.widget.ClassPresenterSelector;
import androidx.leanback.widget.ItemBridgeAdapter;
import androidx.leanback.widget.VerticalGridView;

import com.bumptech.glide.Glide;
import com.smartsoft.movietracker.R;
import com.smartsoft.movietracker.interfaces.OnDetailPageListener;
import com.smartsoft.movietracker.model.MovieDetails;
import com.smartsoft.movietracker.model.cast.CastList;
import com.smartsoft.movietracker.model.genre.Genre;
import com.smartsoft.movietracker.model.movie.Movie;
import com.smartsoft.movietracker.model.review.ReviewList;
import com.smartsoft.movietracker.model.video.VideoList;
import com.smartsoft.movietracker.presenter.DetailPagePresenter;
import com.smartsoft.movietracker.utils.Constant;
import com.smartsoft.movietracker.view.BaseFragment;

import java.util.ArrayList;
import java.util.Objects;

import at.huber.youtubeExtractor.VideoMeta;
import at.huber.youtubeExtractor.YouTubeExtractor;
import at.huber.youtubeExtractor.YtFile;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @see DetailPageFragment is a {@link androidx.fragment.app.Fragment}
 * that handle the app lifeCycle in the presented section
 * Load the views with the downloaded data
 * Initialize the presenters
 * Extracting the video URLs
 */
public class DetailPageFragment extends BaseFragment implements OnDetailPageListener {

    /**
     * Youtube resolution tags
     */
    private static final int[] youtubeResolutionTags = {248, 46, 96, 95, 22, 18, 83, 82};

    @BindView(R.id.background_image)
    ImageView background;
    @BindView(R.id.detail_page_grid_view)
    VerticalGridView verticalGridView;

    /**
     * Current Movie which displayed
     */
    private Movie movie;

    /**
     * Contains all the genres
     */
    private ArrayList<Genre> allGenres;

    /**
     * The adapter where the app load the downloaded data
     */
    private ArrayObjectAdapter objectAdapter;

    /**
     * The class presenter who does the Data downloading
     */
    private DetailPagePresenter dPresenter;

    /**
     * A third party library object which makes download URL from URIs
     */
    private YouTubeExtractor youTubeExtractor;

    /**
     * Contains Youtube URIs
     */
    private ArrayList<Uri> youtubeLinks;

    /**
     * Fragment Lifecycle function where initialize
     * @see #youtubeLinks #dPresenter
     * @param savedInstanceState Save the state of the Fragment
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        youtubeLinks = new ArrayList<>();
        dPresenter = new DetailPagePresenter(this);
    }

    /**
     * When the Internet comes back this overrode
     * function calls to make some changes on the attributes
     */
    @Override
    public void onInternetConnected() {
        super.onInternetConnected();
        if (objectAdapter != null && objectAdapter.size() == 0) {
            getAllData();
        }
    }

    /**
     * @see androidx.fragment.app.Fragment#onCreateView(LayoutInflater, ViewGroup, Bundle)
     * is a function that calls when the Fragment is created but it has no view. This means
     * that this function calls {@link LayoutInflater} to make from the XML file a view
     * @param inflater The {@link LayoutInflater} object
     * @param container The fragment holder where the XML file will be painted
     * @param savedInstanceState Fragment state container
     * @return A view that contains all properties and elements from the XML file.
     */
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_detail_page, container, false);
            ButterKnife.bind(this, rootView);
            initializeViews();
            getAllData();
        }
        return rootView;
    }

    /**
     * Fragment lifecycle function
     * Usage for save the Bundles
     * @param context Where running the Fragment
     */
    @Override
    @SuppressWarnings("unchecked")
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        allGenres = new ArrayList<>();
        if (getArguments() != null) {
            movie = (Movie) getArguments().getSerializable(getString(R.string.movie));
            allGenres = (ArrayList<Genre>) getArguments().getSerializable(getString(R.string.allGenres));
        }
    }

    /**
     * This function start the data downloading from the API
     */
    private void getAllData() {
        dPresenter.loadData(movie.getId());
    }

    /**
     * View initializer
     * Loads the images, initializes the presenter for {@link VerticalGridView}
     * Initializes {@link ItemBridgeAdapter}
     * Initializes {@link ClassPresenterSelector}
     * set the {@link VerticalGridView} adapter
     */
    private void initializeViews() {

        Glide.with(rootView.getContext()).load(Constant.API.IMAGE_ORIGINAL_BASE_URL + movie.getBackdropPath()).into(background);

        objectAdapter = new ArrayObjectAdapter();
        objectAdapter.add(movie);

        MovieVerticalGridPresenter movieVerticalGridPresenter = new MovieVerticalGridPresenter(allGenres, dPresenter);
        CastVerticalGridPresenter castVerticalGridPresenter = new CastVerticalGridPresenter();
        ReviewVerticalGridPresenter reviewVerticalGridPresenter = new ReviewVerticalGridPresenter();
        VideoVerticalGridPresenter videoVerticalGridPresenter = new VideoVerticalGridPresenter(youtubeLinks);

        ClassPresenterSelector presenterSelector = new ClassPresenterSelector();
        presenterSelector.addClassPresenter(Movie.class, movieVerticalGridPresenter);
        presenterSelector.addClassPresenter(CastList.class, castVerticalGridPresenter);
        presenterSelector.addClassPresenter(ReviewList.class, reviewVerticalGridPresenter);
        presenterSelector.addClassPresenter(VideoList.class, videoVerticalGridPresenter);

        ItemBridgeAdapter itemBridgeAdapter = new ItemBridgeAdapter(objectAdapter, presenterSelector);
        verticalGridView.setAdapter(itemBridgeAdapter);

    }

    /**
     * This function fills the {@link #objectAdapter} after the data is downloaded,
     * When the Videos downloaded the function calls the {@link YouTubeExtractor} async tast
     * to generate download URLs from Youtube URIs.
     * @param movieDetails is used as a current storage in what
     *      *     we temporary saving the downloaded data (more specifically in this variable we
     */
    @SuppressLint("StaticFieldLeak")
    @Override
    public void displayData(MovieDetails movieDetails) {

        CastList cast = new CastList(movieDetails.getCasts());
        ReviewList list = new ReviewList(movieDetails.getReviews());

        objectAdapter.add(cast);
        objectAdapter.add(list);

        for (int i = 0; i < movieDetails.getVideos().size(); ++i) {
            String youtubeLink = String.format(getString(R.string.YoutubeBaseUrl), movieDetails.getVideos().get(i).getKey());
            int finalI = i;
            youTubeExtractor = new YouTubeExtractor(rootView.getContext()) {
                @Override
                protected void onExtractionComplete(SparseArray<YtFile> ytFiles, VideoMeta videoMeta) {
                    if (ytFiles != null) {

                        String downloadUrl = getDownloadUrlForVideos(ytFiles);
                        if (downloadUrl == null) {
                            movieDetails.getVideos().remove(movieDetails.getVideos().get(finalI));
                            return;
                        }
                        Uri uri = Uri.parse(downloadUrl);
                        youtubeLinks.add(uri);
                    }
                }
            };
            youTubeExtractor.extract(youtubeLink, true, true);

        }
        VideoList videoList = new VideoList(movieDetails.getVideos());
        objectAdapter.add(videoList);
    }

    /**
     * This function tries to make download URL from {@link SparseArray}
     * element based on the {@link #youtubeResolutionTags}
     * @param ytFiles The
     * @return links generated without {@link #youtubeResolutionTags}
     */
    private String getDownloadUrlForVideos(SparseArray<YtFile> ytFiles) {
        for (int tag : youtubeResolutionTags) {
            try {
                return ytFiles.get(tag).getUrl();
            } catch (Exception ignore) {
            }
        }
        return null;
    }

    /**
     * This function is overrode from {@link OnDetailPageListener}
     * and used for cancelling the Youtube extraction if it didn't
     * finish yet when the user click the back button.
     */
    @Override
    public void backPressed() {
        if(youTubeExtractor != null){
            youTubeExtractor.cancel(true);
        }

        Objects.requireNonNull(getActivity()).onBackPressed();
    }

    /**
     * This function is a fragment lifecycle function and it calls when
     * the {@link DetailPageFragment} is leaved by the user and never will comes back
     * Especially it has no need, then this delete from the memory.
     * And before the fragment will be destroyed it cancels the {@link YouTubeExtractor}
     * if needs.
     */
    @Override
    public void onDestroy() {
        if(youTubeExtractor != null){
            youTubeExtractor.cancel(true);
        }
        super.onDestroy();
    }
}

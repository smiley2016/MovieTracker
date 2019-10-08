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

public class DetailPageFragment extends BaseFragment implements OnDetailPageListener {
    private static final String TAG = DetailPageFragment.class.getName();
    private static final int[] youtubeResolutionTags = {248, 46, 96, 95, 22, 18, 83, 82};
    @BindView(R.id.background_image)
    ImageView background;
    @BindView(R.id.detail_page_grid_view)
    VerticalGridView verticalGridView;
    private Movie movie;
    private ArrayList<Genre> selectedGenres;
    private ArrayObjectAdapter objectAdapter;
    private DetailPagePresenter dPresenter;
    private YouTubeExtractor youTubeExtractor;
    private ArrayList<Uri> youtubeLinks;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        youtubeLinks = new ArrayList<>();
        dPresenter = new DetailPagePresenter(this);
    }

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

    @Override
    public void InternetConnected() {

    }

    @Override
    @SuppressWarnings("unchecked")
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (getArguments() != null) {
            selectedGenres = new ArrayList<>();
            movie = (Movie) getArguments().getSerializable(getString(R.string.movie));
            selectedGenres = (ArrayList<Genre>) getArguments().getSerializable(getString(R.string.selectedGenres));
        }
    }

    private void getAllData() {
        dPresenter.loadData(movie.getId());
    }

    private void initializeViews() {

        Glide.with(rootView.getContext()).load(Constant.API.IMAGE_ORIGINAL_BASE_URL + movie.getBackdropPath()).into(background);

        ArrayList<String> currentMovieGenres = new ArrayList<>();

        for (int i = 0; i < selectedGenres.size(); ++i) {
            for (int j = 0; j < movie.getGenreIds().size(); ++j) {
                if (movie.getGenreIds().get(j).equals(selectedGenres.get(i).getId())) {
                    currentMovieGenres.add(selectedGenres.get(i).getName());
                }
            }

        }


        objectAdapter = new ArrayObjectAdapter();
        objectAdapter.add(movie);

        MovieVerticalGridPresenter movieVerticalGridPresenter = new MovieVerticalGridPresenter(rootView.getContext(), currentMovieGenres, dPresenter);
        CastVerticalGridPresenter castVerticalGridPresenter = new CastVerticalGridPresenter(rootView.getContext());
        ReviewVerticalGridPresenter reviewVerticalGridPresenter = new ReviewVerticalGridPresenter(rootView.getContext());
        VideoVerticalGridPresenter videoVerticalGridPresenter = new VideoVerticalGridPresenter(rootView.getContext(), youtubeLinks);

        ClassPresenterSelector presenterSelector = new ClassPresenterSelector();
        presenterSelector.addClassPresenter(Movie.class, movieVerticalGridPresenter);
        presenterSelector.addClassPresenter(CastList.class, castVerticalGridPresenter);
        presenterSelector.addClassPresenter(ReviewList.class, reviewVerticalGridPresenter);
        presenterSelector.addClassPresenter(VideoList.class, videoVerticalGridPresenter);

        ItemBridgeAdapter itemBridgeAdapter = new ItemBridgeAdapter(objectAdapter, presenterSelector);
        verticalGridView.setAdapter(itemBridgeAdapter);

    }

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

    private String getDownloadUrlForVideos(SparseArray<YtFile> ytFiles) {
        for (int tag : youtubeResolutionTags) {
            try {
                return ytFiles.get(tag).getUrl();
            } catch (Exception ignore) {
            }
        }
        return null;
    }

    @Override
    public void backPressed() {
        Objects.requireNonNull(getActivity()).onBackPressed();
    }

    @Override
    public void onDestroy() {
        youTubeExtractor.cancel(true);
        super.onDestroy();
    }
}

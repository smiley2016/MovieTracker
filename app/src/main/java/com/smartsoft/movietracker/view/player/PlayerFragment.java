package com.smartsoft.movietracker.view.player;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.leanback.widget.ArrayObjectAdapter;
import androidx.leanback.widget.ClassPresenterSelector;
import androidx.leanback.widget.HorizontalGridView;
import androidx.leanback.widget.ItemBridgeAdapter;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.smartsoft.movietracker.R;
import com.smartsoft.movietracker.interfaces.PlayerInterface;
import com.smartsoft.movietracker.model.video.Video;
import com.smartsoft.movietracker.presenter.PlayerPresenter;
import com.smartsoft.movietracker.view.BaseFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.smartsoft.movietracker.utils.Utils.pxFromDp;

public class PlayerFragment extends BaseFragment implements PlayerInterface {

    @BindView(R.id.video_view)
    PlayerView playerView;

    @BindView(R.id.player_video_title)
    TextView title;

    @BindView(R.id.recommended_videos_gridView)
    HorizontalGridView hGridView;

    @BindView(R.id.player_progressBar)
    ProgressBar progressBar;
    @BindView(R.id.text_frame_layout)
    FrameLayout videoTitleFrameLayout;
    private SimpleExoPlayer player;
    private ArrayList<Video> videos;
    private long playbackPosition;
    private int currentWindow;
    private int playIndex;
    private boolean playWhenReady = true;
    private ArrayList<Uri> youtubeLinks;
    private PlayerVerticalGridPresenter vPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        PlayerPresenter presenter = new PlayerPresenter(this);
        vPresenter = new PlayerVerticalGridPresenter(getContext(), presenter, videos);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_player, container, false);
        }
        ButterKnife.bind(this, rootView);
        initializeViews();
        return rootView;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (getArguments() != null) {
            videos = (ArrayList<Video>) getArguments().getSerializable(getString(R.string.videos));
            youtubeLinks = (ArrayList<Uri>) getArguments().getSerializable(getString(R.string.youtubeLinks));
            playIndex = getArguments().getInt(getString(R.string.playIndex));
        }
    }

    private void initializeViews() {

        title.setText(videos.get(playIndex).getName());

        hGridView.setItemSpacing((int) rootView.getContext().getResources().getDimension(R.dimen.spacing));

        ArrayObjectAdapter objectAdapter = new ArrayObjectAdapter();

        for (Video it : videos) {
            objectAdapter.add(it);
        }

        hGridView.setAdapter(
                new ItemBridgeAdapter(objectAdapter,
                        new ClassPresenterSelector()
                                .addClassPresenter(Video.class, vPresenter))
        );
    }


    private void initializePlayer() {

        player = ExoPlayerFactory.newSimpleInstance(
                new DefaultRenderersFactory(rootView.getContext()),
                new DefaultTrackSelector(), new DefaultLoadControl());

        makeListFromURIs();
    }


    @SuppressLint("StaticFieldLeak")
    private void makeListFromURIs() {
        MediaSource[] mediaSource = new MediaSource[youtubeLinks.size()];

        for (int i = 0; i < youtubeLinks.size(); ++i) {
            mediaSource[i] = buildMediaSource(youtubeLinks.get(i));
        }
        setMediaSource(mediaSource);

    }

    private void setMediaSource(MediaSource[] mediaSource) {
        currentWindow = playIndex;
        playerView.setPlayer(player);
        ConcatenatingMediaSource concatenatingMediaSource = new ConcatenatingMediaSource(mediaSource);
        player.prepare(concatenatingMediaSource, true, true);
        if (playIndex != -1) {
            player.seekTo(currentWindow, playbackPosition);
        }

        player.setPlayWhenReady(playWhenReady);

        progressBar.setVisibility(View.GONE);
    }

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory(rootView.getContext().getString(R.string.ExoplayerCodeLab))).
                createMediaSource(uri);
    }


    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > Build.VERSION_CODES.M) {
            initializePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        hideSystemUi();
        if ((Util.SDK_INT <= Build.VERSION_CODES.M || player == null)) {
            initializePlayer();
        }
    }

    @Override
    public void InternetConnected() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();


    }

    private void hideSystemUi() {
        playerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= Build.VERSION_CODES.M) {
            releasePlayer();
        }
    }

    private void showPlayList() {
        if (videoTitleFrameLayout.getLayoutParams() instanceof ConstraintLayout.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) videoTitleFrameLayout.getLayoutParams();
            p.setMargins((int) rootView.getResources().getDimension(R.dimen.playlist_margin_zero),
                    (int) rootView.getResources().getDimension(R.dimen.show_playlist_margin_offset_top),
                    (int) rootView.getResources().getDimension(R.dimen.playlist_margin_zero),
                    (int) rootView.getContext().getResources().getDimension(R.dimen.playlist_margin_offset_bottom));
            videoTitleFrameLayout.requestLayout();
        }
    }

    private void hidePlayList() {

        if (videoTitleFrameLayout.getLayoutParams() instanceof ConstraintLayout.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) videoTitleFrameLayout.getLayoutParams();
            p.setMargins((int) rootView.getResources().getDimension(R.dimen.playlist_margin_zero),
                    (int) pxFromDp(rootView.getContext(), (int)rootView.getResources()
                            .getDimension(R.dimen.show_playlist_margin_offset_top)),
                    (int) rootView.getResources().getDimension(R.dimen.playlist_margin_zero),
                    (int) pxFromDp(rootView.getContext(), rootView.getContext().getResources()
                            .getDimension(R.dimen.playlist_margin_offset_bottom)));
            videoTitleFrameLayout.requestLayout();
        }

    }


    private void releasePlayer() {
        if (player != null) {
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            playWhenReady = player.getPlayWhenReady();
            player.release();
            player = null;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > Build.VERSION_CODES.M) {
            releasePlayer();
        }
    }


    @Override
    public void setPlaylistVisibility(int visibility) {
        if (visibility == View.VISIBLE) {
            showPlayList();
        } else {
            hidePlayList();
        }
    }

    public void startNewVideo(int position) {
        currentWindow = position;
        if (playIndex != -1) {
            player.seekTo(currentWindow, playbackPosition);
            player.setPlayWhenReady(playWhenReady);
        }
    }
}

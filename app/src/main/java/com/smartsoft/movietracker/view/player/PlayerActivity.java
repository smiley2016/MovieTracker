package com.smartsoft.movietracker.view.player;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.leanback.widget.HorizontalGridView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

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
import com.smartsoft.movietracker.model.video.Video;
import com.smartsoft.movietracker.presenter.MovieNavigationPresenter;

import java.util.ArrayList;

import at.huber.youtubeExtractor.VideoMeta;
import at.huber.youtubeExtractor.YouTubeExtractor;
import at.huber.youtubeExtractor.YtFile;

import static com.smartsoft.movietracker.utils.Util.pxFromDp;

public class PlayerActivity extends AppCompatActivity {

    private PlayerView playerView;
    private SimpleExoPlayer player;
    private ArrayList<Video> videos;

    private long playbackPosition;
    private int currentWindow;
    private int playIndex;
    private boolean playWhenReady = true;

    private ProgressBar progressBar;
    private FrameLayout videoTitleFrameLayout;
    private HorizontalGridView hGridView;
    private PlayerGridViewAdapter adapter;
    private TextView title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        playerView = findViewById(R.id.video_view);
        videos = (ArrayList<Video>) getIntent().getSerializableExtra("video");
        playIndex = getIntent().getIntExtra("playIndex", -1);
        initViews();
    }

    private void initViews() {

        title = findViewById(R.id.player_video_title);
        title.setText(videos.get(playIndex).getName());

        hGridView = findViewById(R.id.recommended_videos_gridView);
        hGridView.setNumRows(1);
        hGridView.setItemSpacing(8);


        progressBar = findViewById(R.id.player_progressBar);

        videoTitleFrameLayout = findViewById(R.id.text_frame_layout);

    }


    private void initializePlayer() {

        player = ExoPlayerFactory.newSimpleInstance(
                new DefaultRenderersFactory(this),
                new DefaultTrackSelector(), new DefaultLoadControl());

        makeListFromURIs();
    }


    @SuppressLint("StaticFieldLeak")
    public void makeListFromURIs() {
        MediaSource[] mediaSource = new MediaSource[videos.size()];

        for (int i = 0; i < videos.size(); ++i) {
            String youtubeLink = String.format("https://youtube.com/watch?v=%s", videos.get(i).getKey());
            int finalI = i;
            new YouTubeExtractor(this) {
                @Override
                protected void onExtractionComplete(SparseArray<YtFile> ytFiles, VideoMeta videoMeta) {
                    if (ytFiles != null) {
                        int itag = 22;
                        String downloadUrl = ytFiles.get(itag).getUrl();
                        Uri uri = Uri.parse(downloadUrl);
                        mediaSource[finalI] = buildMediaSource(uri);
                        if (finalI == videos.size() - 1) {
                            setMediaSource(mediaSource);
                        }

                    }
                }
            }.extract(youtubeLink, true, true);
        }
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

        adapter = new PlayerGridViewAdapter(videos, this);
        hGridView.setAdapter(adapter);
    }

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory("exoplayer-codelab")).
                createMediaSource(uri);
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initializePlayer();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        hideSystemUi();
        if ((Util.SDK_INT <= 23 || player == null)) {
            initializePlayer();
        }
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
    protected void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    public void showPlayList() {
        if (videoTitleFrameLayout.getLayoutParams() instanceof ConstraintLayout.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) videoTitleFrameLayout.getLayoutParams();
            p.setMargins(0, (int) pxFromDp(this, 232), 0, (int)pxFromDp(this, 22));
            videoTitleFrameLayout.requestLayout();
        }
    }

    public void hidePlayList() {

        if (videoTitleFrameLayout.getLayoutParams() instanceof ConstraintLayout.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) videoTitleFrameLayout.getLayoutParams();
            p.setMargins(0, (int) pxFromDp(this, 332), 0, (int) pxFromDp(this, 22));
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
    protected void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

}

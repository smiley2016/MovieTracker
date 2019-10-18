package com.smartsoft.movietracker.view.player;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.leanback.widget.Presenter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.smartsoft.movietracker.R;
import com.smartsoft.movietracker.model.video.Video;
import com.smartsoft.movietracker.presenter.PlayerPresenter;
import com.smartsoft.movietracker.utils.Constant;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PlayerVerticalGridPresenter extends Presenter {

    private Context mContext;
    private PlayerPresenter presenter;
    private ArrayList<Video> videos;

    PlayerVerticalGridPresenter(PlayerPresenter presenter, ArrayList<Video> videos) {
        this.presenter = presenter;
        this.videos = videos;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        mContext = parent.getContext();
        return new PresenterViewHolder(View.inflate(mContext, R.layout.video_element, null));
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Object item) {
        PresenterViewHolder holder = (PresenterViewHolder) viewHolder;
        Video video = ((Video) item);
        holder.bind(video, videos);
    }

    @Override
    public void onUnbindViewHolder(ViewHolder viewHolder) {

    }

    class PresenterViewHolder extends ViewHolder {

        @BindView(R.id.video_title)
        TextView videoTitle;
        @BindView(R.id.video_thumbnail)
        ImageView videoThumbnail;
        @BindView(R.id.video_element_layout)
        ConstraintLayout layout;


        PresenterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bind(Video video, ArrayList<Video> videos) {

            Glide.with(mContext).load(Constant.API.BASE_YOUTUBE_URL_FOR_PICTURE + video.getKey() + Constant.API.YOUTUBE_THUMBNAIL).listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    videoThumbnail.setImageDrawable(mContext.getDrawable(R.drawable.error));
                    Log.e("3ss", String.valueOf(e));
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    return false;
                }
            }).into(videoThumbnail);

            videoTitle.setText(video.getName());

            layout.setOnFocusChangeListener((view, b) -> {
                if (b) {
                    presenter.setPlayListVisibility(View.VISIBLE);
                } else {
                    presenter.setPlayListVisibility(View.INVISIBLE);
                }
            });

            layout.setOnClickListener(view -> presenter.startNewVideo(videos.indexOf(video)));

        }
    }
}

package com.smartsoft.movietracker.view.player;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

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

public class PlayerGridViewAdapter extends RecyclerView.Adapter<PlayerGridViewAdapter.Holder> {


    private ArrayList<Video> videos;
    private Context ctx;
    private PlayerPresenter presenter;

    PlayerGridViewAdapter(ArrayList<Video> videos, Context ctx, PlayerPresenter presenter) {
        this.videos = videos;
        this.ctx = ctx;
        this.presenter = presenter;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(ctx).inflate(R.layout.video_element, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.bind(videos.get(position), ctx, presenter, position);
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    static class Holder extends RecyclerView.ViewHolder{

        private TextView videoTitle;
        private ImageView videoThumbnail;
        private ConstraintLayout layout;

        Holder(@NonNull View itemView) {
            super(itemView);

            videoThumbnail = itemView.findViewById(R.id.video_thumbnail);
            videoTitle = itemView.findViewById(R.id.video_title);
            layout = itemView.findViewById(R.id.video_element_layout);
        }

        void bind(Video video, Context ctx, PlayerPresenter presenter, int position){
            Glide.with(ctx).load(Constant.API.BASE_YOUTUBE_URL_FOR_PICTURE + video.getKey() + Constant.API.YOUTUBE_THUMBNAIL).listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    videoThumbnail.setImageDrawable(ctx.getDrawable(R.drawable.error));
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
                if(b){
                    presenter.setPlayListVisibility(View.VISIBLE);
                }else{
                    presenter.setPlayListVisibility(View.INVISIBLE);
                }
            });

            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    presenter.startNewVideo(position);
                }
            });
        }
    }
}

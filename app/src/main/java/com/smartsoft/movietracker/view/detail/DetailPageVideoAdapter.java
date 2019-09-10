package com.smartsoft.movietracker.view.detail;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
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
import com.smartsoft.movietracker.presenter.DetailPagePresenter;
import com.smartsoft.movietracker.utils.Constant;
import com.smartsoft.movietracker.view.player.PlayerActivity;

import java.util.ArrayList;

public class DetailPageVideoAdapter extends RecyclerView.Adapter<DetailPageVideoAdapter.Holder> {

    private Context ctx;
    private ArrayList<Video> videos = new ArrayList<>();


    public DetailPageVideoAdapter(Context ctx) {
        this.ctx = ctx;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(ctx).inflate(R.layout.video_element, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.bind(ctx, videos.get(position));
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    public void addAllToList(ArrayList<Video> videos) {
        this.videos.addAll(videos);
        notifyDataSetChanged();
    }

    public static class Holder extends RecyclerView.ViewHolder{

        private TextView videoTitle;
        private ImageView substract;
        private ImageView videoThumbnail;
        private ConstraintLayout layout;
        private ProgressBar progressBar;

        public Holder(@NonNull View itemView) {

            super(itemView);
            videoThumbnail = itemView.findViewById(R.id.video_thumbnail);
            substract = itemView.findViewById(R.id.subtract);
            videoTitle = itemView.findViewById(R.id.video_title);
            layout = itemView.findViewById(R.id.video_element_layout);
//            progressBar = itemView.findViewById(R.id.video_progress_bar);
      }

        public void bind(Context ctx, Video video){
            Glide.with(ctx).load(Constant.Common.BASE_YOUTUBE_URL_FOR_PICTURE + video.getKey() + Constant.Common.YOUTUBE_THUMBNAIL).listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    videoThumbnail.setImageDrawable(ctx.getDrawable(R.drawable.background));
                    Log.e("3ss", e.getMessage());
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    return false;
                }
            }).into(videoThumbnail);

            videoTitle.setText(video.getName());

            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(ctx, PlayerActivity.class);
                    intent.putExtra("videoKey", video.getKey());
                    ctx.startActivity(intent);
                }
            });
        }
    }
}

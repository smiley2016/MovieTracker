package com.smartsoft.movietracker.view.detail;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.smartsoft.movietracker.R;
import com.smartsoft.movietracker.model.cast.Cast;
import com.smartsoft.movietracker.utils.Constant;
import com.smartsoft.movietracker.utils.Dialogs;

import java.util.ArrayList;

public class DetailPageCastAdapter extends RecyclerView.Adapter<DetailPageCastAdapter.Holder> {

    private ArrayList<Cast> casts;
    private Context ctx;

    public DetailPageCastAdapter(Context ctx) {
        this.ctx = ctx;
        this.casts = new ArrayList<>();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(ctx).inflate(R.layout.cast_element, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

       holder.bind(ctx, casts.get(position));
    }

    @Override
    public int getItemCount() {
        return casts.size();
    }

    public void addAllToList(ArrayList<Cast> casts){
        this.casts.addAll(casts);
        notifyDataSetChanged();
    }

    public static class Holder extends RecyclerView.ViewHolder {
        private TextView actorName;
        private ImageView actorPicture;
        private RelativeLayout layout;
        private ProgressBar progressBar;

        public Holder(@NonNull View itemView) {

            super(itemView);
            actorName = itemView.findViewById(R.id.actor_name);
            actorPicture = itemView.findViewById(R.id.actor_picture);
            layout = itemView.findViewById(R.id.cast_element_layout);
            progressBar = itemView.findViewById(R.id.cast_element_progress_bar);
        }

        public void bind(Context ctx, Cast cast) {

                    Glide.with(ctx).load(Constant.API.IMAGE_BASE_URL + cast.getProfilePath()).circleCrop().error(R.mipmap.unkown_person_round_v2_legacy_round).listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                            progressBar.setVisibility(View.GONE);
                            actorName.setVisibility(View.VISIBLE);
                            actorName.setText(cast.getName());
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                            progressBar.setVisibility(View.GONE);
                            actorName.setText(cast.getName());
                            return false;
                        }
                    }).into(actorPicture);

                layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Dialogs.startCastDialog(ctx, cast);
                    }
                });

            }
        }
    }

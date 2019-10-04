package com.smartsoft.movietracker.view.detail;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.leanback.widget.Presenter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.smartsoft.movietracker.R;
import com.smartsoft.movietracker.model.cast.Cast;
import com.smartsoft.movietracker.utils.Constant;
import com.smartsoft.movietracker.view.dialogs.CastDialog;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CastHorizontalGridPresenter extends Presenter {

    private Context mContext;


    public CastHorizontalGridPresenter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new PresenterViewHolder(View.inflate(mContext, R.layout.cast_element, null));
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Object item) {
        PresenterViewHolder holder = (PresenterViewHolder) viewHolder;
        Cast cast = ((Cast) item);
        holder.bind(mContext, cast);
    }

    @Override
    public void onUnbindViewHolder(ViewHolder viewHolder) {

    }

    class PresenterViewHolder extends ViewHolder {
        @BindView(R.id.actor_name)
        TextView actorName;

        @BindView(R.id.actor_picture)
        ImageView actorPicture;

        @BindView(R.id.cast_element_layout)
        RelativeLayout layout;

        @BindView(R.id.cast_element_progress_bar)
        ProgressBar progressBar;


        public PresenterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        void bind(Context ctx, Cast cast) {

            Glide.with(ctx)
                    .load(Constant.API.IMAGE_ORIGINAL_BASE_URL + cast.getProfilePath())
                    .circleCrop()
                    .error(R.mipmap.unkown_person_round_v2_legacy)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e,
                                                    Object model, Target<Drawable> target,
                                                    boolean isFirstResource) {
                            progressBar.setVisibility(View.GONE);
                            actorName.setVisibility(View.VISIBLE);
                            actorName.setText(cast.getName());

                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource,
                                                       Object model,
                                                       Target<Drawable> target,
                                                       DataSource dataSource,
                                                       boolean isFirstResource) {
                            progressBar.setVisibility(View.GONE);
                            actorName.setText(cast.getName());

                            return false;
                        }
                    })
                    .into(actorPicture);

            layout.setOnClickListener(view -> new CastDialog(ctx, cast).startCastDialog());


        }
    }
}

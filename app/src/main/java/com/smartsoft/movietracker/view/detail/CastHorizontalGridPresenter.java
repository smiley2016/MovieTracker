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

/**
 * @see CastHorizontalGridPresenter is
 * a {@link androidx.leanback.widget.HorizontalGridView} component
 * for handle the {@link Cast} element rollability in horizontal direction
 */
public class CastHorizontalGridPresenter extends Presenter {

    /**
     * Where the {@link CastHorizontalGridPresenter} is initialized and running.
     */
    private Context mContext;

    /**
     * This function create the from the XML cast_element layout a real view
     * @param parent The ViewGroup wherein the
     *               {@link android.view.LayoutInflater} will paints the XML
     * @return {@link PresenterViewHolder}
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        mContext = parent.getContext();
        return new PresenterViewHolder(View.inflate(parent.getContext(), R.layout.cast_element, null));
    }

    /**
     * Onto every single element the {@link CastHorizontalGridPresenter#onBindViewHolder(ViewHolder, Object)}
     * calls the {@link PresenterViewHolder#bind(Context, Cast)} function to initializes it's views
     * how behaves in the app.
     * @param viewHolder {@link androidx.leanback.widget.Presenter.ViewHolder} what holds the elements in
     * {@link androidx.leanback.widget.HorizontalGridView}
     * @param item Contains all the elements from the {@link androidx.leanback.widget.ArrayObjectAdapter}
     */
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Object item) {
        PresenterViewHolder holder = (PresenterViewHolder) viewHolder;
        Cast cast = ((Cast) item);
        holder.bind(mContext, cast);
    }

    /**
     * App doesn't use this function
     * @param viewHolder {@link androidx.leanback.widget.Presenter.ViewHolder} what holds the elements in
     *                  {@link androidx.leanback.widget.HorizontalGridView}
     */
    @Override
    public void onUnbindViewHolder(ViewHolder viewHolder) {

    }

    /**
     * Inner class is contains a {@link Cast} views of the element
     * and it's properties
     */
    class PresenterViewHolder extends ViewHolder {
        @BindView(R.id.actor_name)
        TextView actorName;

        @BindView(R.id.actor_picture)
        ImageView actorPicture;

        @BindView(R.id.cast_element_layout)
        RelativeLayout layout;

        @BindView(R.id.cast_element_progress_bar)
        ProgressBar progressBar;

        /**
         * Inner class constructor
         * @param view The view that holding the presenter
         */
        public PresenterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        /**
         * Initializer function for one element
         * @param ctx Context where the CastHorizontalGridView is running
         * @param cast {@link Cast} object which will be initialize
         */
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

            layout.setOnClickListener(view -> new CastDialog(ctx, cast));

        }
    }
}

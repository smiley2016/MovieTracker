package com.smartsoft.movietracker.view.detail;

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
import com.smartsoft.movietracker.interfaces.DetailVideoGridInterface;
import com.smartsoft.movietracker.model.video.Video;
import com.smartsoft.movietracker.utils.Constant;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @see VideoHorizontalGridPresenter is
 * a {@link androidx.leanback.widget.HorizontalGridView} component
 * for handle the {@link Video} element rollability in horizontal direction
 */
public class VideoHorizontalGridPresenter extends Presenter {

    /**
     * Where running the presenter
     */
    private Context mContext;
    /**
     * DetailVideoGridInterface reference
     */
    private DetailVideoGridInterface mInterface;

    /**
     * Class constructor
     * @param mInterface {@link #mInterface}
     */
    VideoHorizontalGridPresenter(DetailVideoGridInterface mInterface) {
        this.mInterface = mInterface;
    }

    /**
     * This function create the from the XML video_element layout a real view
     * @param parent The ViewGroup wherein the
     *               {@link android.view.LayoutInflater} will paints the XML
     * @return {@link PresenterViewHolder}
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        mContext = parent.getContext();
        return new PresenterViewHolder(View.inflate(mContext, R.layout.video_element, null));
    }

    /**
     * Onto every single element the {@link #onBindViewHolder(ViewHolder, Object)}
     * calls the {@link PresenterViewHolder#bind(Video)} function to initializes it's views
     * how behaves in the app.
     * @param viewHolder {@link androidx.leanback.widget.Presenter.ViewHolder} what holds the elements in
     * {@link androidx.leanback.widget.HorizontalGridView}
     * @param item Contains all the elements from the {@link androidx.leanback.widget.ArrayObjectAdapter}
     */
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Object item) {
        PresenterViewHolder holder = (PresenterViewHolder) viewHolder;
        Video video = ((Video) item);
        holder.bind(video);
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
     * This function calls the {@link DetailVideoGridInterface#startPlayer(String)}
     * to start that video which Id is same as videoId what gets on the input
     * @param videoId Video ID
     */
    private void startPlayerById(String videoId) {
        mInterface.startPlayer(videoId);
    }

    /**
     * Inner class is contains a {@link Video} views of the element
     * and it's properties
     */
    class PresenterViewHolder extends ViewHolder {
        @BindView(R.id.video_title)
        TextView videoTitle;
        @BindView(R.id.video_thumbnail)
        ImageView videoThumbnail;
        @BindView(R.id.video_element_layout)
        ConstraintLayout layout;


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
         * @param video {@link Video} object which will be initialize
         */
        public void bind(Video video) {

            Glide.with(mContext)
                    .load(Constant.API.BASE_YOUTUBE_URL_FOR_PICTURE
                            + video.getKey()
                            + Constant.API.YOUTUBE_THUMBNAIL)
                    .listener(new RequestListener<Drawable>() {
                        @Override
                        public boolean onLoadFailed(@Nullable GlideException e,
                                                    Object model,
                                                    Target<Drawable> target,
                                                    boolean isFirstResource) {

                            videoThumbnail.setImageDrawable(mContext.getDrawable(R.drawable.error));
                            Log.e("3ss", String.valueOf(e));
                            return false;
                        }

                        @Override
                        public boolean onResourceReady(Drawable resource,
                                                       Object model,
                                                       Target<Drawable> target,
                                                       DataSource dataSource,
                                                       boolean isFirstResource) {
                            return false;
                        }
                    })
                    .into(videoThumbnail);

            videoTitle.setText(video.getName());

            layout.setOnClickListener(view -> startPlayerById(video.getId()));
        }
    }
}

package com.smartsoft.movietracker.view.main.genre;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.leanback.widget.Presenter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.smartsoft.movietracker.R;
import com.smartsoft.movietracker.model.genre.Genre;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * This presenter handle the functionality of the
 * {@link androidx.leanback.widget.VerticalGridView} in the
 * {@link GenreSelectorFragment}
 */
public class GenreGridViewPresenter extends Presenter {

    /**
     * Where the {@link GenreGridViewPresenter} is running
     */
    private Context mContext;

    /**
     * Overrode function from the abstract parent class.
     * This handle the {@link R.layout#genre_element} will be
     * a view from XML file.
     *
     * @param parent Wherein the view will be appeared
     * @return {@link PresenterViewHolder} object
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.genre_element, parent, false);
        mContext = parent.getContext();
        return new PresenterViewHolder(view);
    }

    /**
     * Overrode from the abstract parent class
     * This handle every single view which is inflated from
     * the XML will be initialized, sets the views properties
     * @param viewHolder {@link PresenterViewHolder} object
     *                          uses the app to set the views properties
     * @param item This item ({@link Genre} model) get the function from the
     *      {@link androidx.leanback.widget.ArrayObjectAdapter}
     */
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Object item) {
        PresenterViewHolder holder = (PresenterViewHolder) viewHolder;
        holder.bind((Genre) item);
    }

    /**
     * The app doesn't use this function
     * @param viewHolder {@link PresenterViewHolder}
     */
    @Override
    public void onUnbindViewHolder(ViewHolder viewHolder) {

    }

    /**
     * Inner class for initialize the view what
     * made from {@link R.layout#genre_element}
     * and sets it's properties
     */
    class PresenterViewHolder extends ViewHolder {

        @BindView(R.id.genre_name)
        TextView genreNameTextView;
        @BindView(R.id.genre_image_view)
        ImageView genreImageView;
        @BindView(R.id.vertical_grid_view_layout_element)
        ConstraintLayout layout;
        @BindView(R.id.selected_icon)
        ImageView select_icon;

        /**
         * Inner class constructor
         * @param view inflated view
         */
        PresenterViewHolder(View view) {
            super(view);

            ButterKnife.bind(this, view);
        }

        /**
         * Here sets the properties of the inflated view
         * @param genre {@link Genre} model from the {@link androidx.leanback.widget.ArrayObjectAdapter}
         */
        void bind(Genre genre) {
            genreNameTextView.setText(genre.getName());

            Glide.with(mContext).load(R.mipmap.genre).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(genreImageView);

            layout.setOnClickListener(view -> {
                if (genre.isActivated()) {
                    genre.setActivated(false);
                    select_icon.setVisibility(View.INVISIBLE);
                } else {
                    genre.setActivated(true);
                    select_icon.setVisibility(View.VISIBLE);
                }
            });
        }
    }
}

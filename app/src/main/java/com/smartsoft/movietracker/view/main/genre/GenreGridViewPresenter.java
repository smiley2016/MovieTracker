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

public class GenreGridViewPresenter extends Presenter {

    private Context mContext;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.genre_element, parent, false);
        mContext = parent.getContext();
        return new PresenterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Object item) {
        PresenterViewHolder holder = (PresenterViewHolder) viewHolder;
        holder.bind((Genre) item);
    }

    @Override
    public void onUnbindViewHolder(ViewHolder viewHolder) {

    }

    class PresenterViewHolder extends ViewHolder {

        @BindView(R.id.genre_description)
        TextView genreNameTextView;
        @BindView(R.id.genre_image_view)
        ImageView genreImageView;
        @BindView(R.id.vertical_grid_view_layout_element)
        ConstraintLayout layout;
        @BindView(R.id.selected_icon)
        ImageView select_icon;

        PresenterViewHolder(View view) {
            super(view);

            ButterKnife.bind(this, view);
        }

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

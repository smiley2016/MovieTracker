package com.smartsoft.movietracker.view.main.navigation;

import android.content.Context;
import android.graphics.Outline;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
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
import com.smartsoft.movietracker.model.genre.Genre;
import com.smartsoft.movietracker.model.movie.Movie;
import com.smartsoft.movietracker.presenter.MovieNavigationPresenter;
import com.smartsoft.movietracker.utils.Constant;
import com.smartsoft.movietracker.utils.FragmentNavigation;
import com.smartsoft.movietracker.utils.StringUtils;

import java.util.ArrayList;
import java.util.Iterator;

import butterknife.BindView;
import butterknife.ButterKnife;

class MovieNavigationGridViewPresenter extends Presenter {

    private Context ctx;
    private MovieNavigationPresenter presenter;
    private ArrayList<Genre> selectedGenres;
    private Integer totalPages;
    private Integer size;


    MovieNavigationGridViewPresenter(MovieNavigationPresenter presenter,
                                     ArrayList<Genre> selectedGenres,
                                     Integer totalPages) {
        this.presenter = presenter;
        this.selectedGenres = selectedGenres;
        this.totalPages = totalPages;
        this.size = 0;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_element, parent, false);
        ctx = parent.getContext();
        return new PresenterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Object item) {
        PresenterViewHolder holder = (PresenterViewHolder) viewHolder;
        holder.bind((Movie) item);
    }

    @Override
    public void onUnbindViewHolder(ViewHolder viewHolder) {

    }

    void updateListSize(int size) {
        this.size += size;
    }

    void clearListSize() {
        this.size = 0;
    }


    class PresenterViewHolder extends ViewHolder {


        @BindView(R.id.poster)
        ImageView poster;
        @BindView(R.id.movie_element_card)
        ConstraintLayout layout;
        @BindView(R.id.spinner)
        ProgressBar progressBar;
        @BindView(R.id.movie_description)
        RelativeLayout detailLayout;

        @BindView(R.id.movie_release_date)
        TextView releaseDate;

        @BindView(R.id.movie_title)
        TextView title;

        @BindView(R.id.movie_genres)
        TextView genres;

        @BindView(R.id.imdb_rating)
        TextView imdbRating;

        boolean isOpen = false;

        PresenterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            detailLayout.setClipToOutline(true);

        }


        void bind(Movie movie) {


            Glide.with(ctx)
                    .load(Constant.API.IMAGE_BASE_URL + movie.getPosterPath())
                    .listener(
                            new RequestListener<Drawable>() {

                                @Override

                                public boolean onLoadFailed(@Nullable GlideException e,
                                                            Object model, Target<Drawable> target,
                                                            boolean isFirstResource) {
                                    progressBar.setVisibility(View.GONE);
                                    return false;
                                }

                                @Override
                                public boolean onResourceReady(Drawable resource,
                                                               Object model,
                                                               Target<Drawable> target,
                                                               DataSource dataSource,
                                                               boolean isFirstResource) {
                                    progressBar.setVisibility(View.GONE);
                                    return false;
                                }
                            })
                    .error(R.drawable.error)
                    .into(poster);

            layout.setOnFocusChangeListener((view, b) -> {
                if (b) {

                    if (presenter.getPosition(movie) >= (size - Constant.GridView.COLUMN_NUM7) && (presenter.getPage() <= totalPages)) {
                        presenter.loadMovieData(ctx, selectedGenres);
                    }

                    poster.setOutlineProvider(new ViewOutlineProvider() {
                        @Override
                        public void getOutline(View view, Outline outline) {
                            outline.setRoundRect(
                                    Constant.MovieNavigation.offset,
                                    Constant.MovieNavigation.offset,
                                    (view.getWidth() + (int) ctx.getResources().getDimension(R.dimen.curve_radius)),
                                    view.getHeight(),
                                    (int) ctx.getResources().getDimension(R.dimen.curve_radius));
                        }
                    });
                    poster.setClipToOutline(true);
                    presenter.onBackgroundChange(movie.getBackdropPath());
                    isOpen = false;
                } else {
                    poster.setClipToOutline(false);
                    if (detailLayout.getVisibility() == View.VISIBLE && !isOpen) {
                        Animation animation = AnimationUtils.loadAnimation(ctx, R.anim.detil_layout_close_animator);
                        detailLayout.setAnimation(animation);
                        detailLayout.setVisibility(View.GONE);

                    }

                }
            });

            layout.setOnClickListener(view -> {
                if (detailLayout.getVisibility() == View.GONE) {
                    Animation animator = AnimationUtils.loadAnimation(ctx, R.anim.detail_layout_open_animator);
                    detailLayout.setVisibility(View.VISIBLE);
                    detailLayout.setAnimation(animator);
                    isOpen = false;
                } else {
                    isOpen = true;
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(ctx.getString(R.string.movie), movie);
                    bundle.putSerializable(ctx.getString(R.string.selectedGenres), selectedGenres);
                    FragmentNavigation.getInstance().showDetailPageFragment(bundle);
                }


            });

            imdbRating.setText(String.format(ctx.getString(R.string.imdb_vote_average), String.valueOf(movie.getVoteAverage())));

            Iterator<Genre> genreIterator = selectedGenres.iterator();
            StringBuilder genreTitles = new StringBuilder();
            while (genreIterator.hasNext()) {
                genreTitles.append(genreIterator.next().getName()).append(StringUtils.COMMA_DELIMITER_WITH_SPACE);
            }
            genreTitles.replace(genreTitles.length() - 2, genreTitles.length() - 1, StringUtils.EMPTY_STRING);

            this.genres.setText(genreTitles);

            title.setText(movie.getTitle());

            String releaseYearList;
            if (movie.getReleaseDate() != null) {
                releaseYearList = movie.getReleaseDate().substring(0, movie.getReleaseDate().indexOf(StringUtils.HYPHEN_DELIMITER));
            } else {
                releaseYearList = StringUtils.EMPTY_STRING;
            }

            releaseDate.setText(releaseYearList);

        }
    }
}

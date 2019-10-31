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
import com.smartsoft.movietracker.utils.Constant;
import com.smartsoft.movietracker.utils.FragmentNavigation;
import com.smartsoft.movietracker.utils.StringUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

class MovieNavigationGridViewPresenter extends Presenter {

    private Context ctx;
    private com.smartsoft.movietracker.presenter.MovieNavigationPresenter presenter;
    private ArrayList<Genre> allGenres;
    private ArrayList<Genre> selectedGenres;
    private Integer totalPages;
    private boolean isSearched;
    private String searchForString;
    private int size;


    MovieNavigationGridViewPresenter(com.smartsoft.movietracker.presenter.MovieNavigationPresenter presenter,
                                     ArrayList<Genre> allGenres,
                                     ArrayList<Genre> selectedGenres,
                                     Integer totalPages,
                                     boolean isSearched,
                                     String searchForString) {

        this.presenter = presenter;
        this.allGenres = allGenres;
        this.selectedGenres = selectedGenres;
        this.totalPages = totalPages;
        this.isSearched = isSearched;
        this.searchForString = searchForString;
        this.size = 0;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        ctx = parent.getContext();
        View view = LayoutInflater.from(ctx).inflate(R.layout.movie_element, parent, false);
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

            boolean isLastItemInRow = false;

            if((presenter.getPosition(movie)+1) % 7 == 0){
                isLastItemInRow = true;
            }

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

            boolean finalIsLastItemInRow = isLastItemInRow;

            layout.setOnFocusChangeListener((view, b) -> {
                if (b) {

                    if (presenter.getPosition(movie) >= (size - Constant.GridView.COLUMN_NUM7) && (presenter.getPage() <= totalPages)) {
                        if (isSearched) {
                            presenter.loadMovieData(searchForString, ctx);
                        } else {
                            presenter.loadMovieData(ctx, selectedGenres);
                        }
                    }

                    poster.setOutlineProvider(new ViewOutlineProvider() {
                        @Override
                        public void getOutline(View view, Outline outline) {
                            outline.setRoundRect(
                                    Constant.MovieNavigationPresenter.offset,
                                    Constant.MovieNavigationPresenter.offset,
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
                    if (detailLayout.getVisibility() == View.VISIBLE && !isOpen ) {
                        Animation animation;
                        if(finalIsLastItemInRow){
                            animation = AnimationUtils.loadAnimation(ctx, R.anim.detail_layout_close_left_animator);
                        }else{
                            animation = AnimationUtils.loadAnimation(ctx, R.anim.detil_layout_close_right_animator);
                        }
                        detailLayout.setAnimation(animation);
                        detailLayout.setVisibility(View.GONE);

                    }

                }
            });


            layout.setOnClickListener(view -> {
                if (detailLayout.getVisibility() == View.GONE) {
                    Animation animation;
                    if(finalIsLastItemInRow){
                        animation = AnimationUtils.loadAnimation(ctx, R.anim.detail_layout_open_left_animator);
                    }else{
                        animation = AnimationUtils.loadAnimation(ctx, R.anim.detail_layout_open_right_animator);
                    }
                    detailLayout.setVisibility(View.VISIBLE);
                    detailLayout.setAnimation(animation);
                    isOpen = false;
                } else {
                    isOpen = true;
                    Bundle bundle = new Bundle();
                    bundle.putSerializable(ctx.getString(R.string.movie), movie);
                    bundle.putSerializable(ctx.getString(R.string.allGenres), allGenres);
                    FragmentNavigation.getInstance().showDetailPageFragment(bundle);
                }


            });

            imdbRating.setText(String.format(ctx.getString(R.string.imdb_vote_average), String.valueOf(movie.getVoteAverage())));

            StringBuilder genreTitles = new StringBuilder();
            if (movie.getGenreIds().size() != 0) {
                for (Genre it : allGenres) {
                    for (Integer currIt : movie.getGenreIds()) {
                        if (currIt.equals(it.getId())) {
                            genreTitles.append(it.getName()).append(StringUtils.COMMA_DELIMITER_WITH_SPACE);
                        }
                    }
                }
                genreTitles.replace(genreTitles.length() - 2, genreTitles.length() - 1, StringUtils.EMPTY_STRING);
            }


            this.genres.setText(genreTitles);

            title.setText(movie.getTitle());

            String releaseYearList;
            if (movie.getReleaseDate() != null) {
                releaseYearList = movie.getReleaseDate().substring(0, movie.getReleaseDate().indexOf(StringUtils.HYPHEN_DELIMITER) + 1)
                        .replace(StringUtils.HYPHEN_DELIMITER, StringUtils.EMPTY_STRING);
            } else {
                releaseYearList = StringUtils.EMPTY_STRING;
            }

            releaseDate.setText(releaseYearList);

        }
    }

}

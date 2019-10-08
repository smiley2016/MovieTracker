package com.smartsoft.movietracker.view.navigation;

import android.content.Context;
import android.graphics.Outline;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
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
import com.smartsoft.movietracker.model.genre.Genre;
import com.smartsoft.movietracker.model.movie.Movie;
import com.smartsoft.movietracker.presenter.BackgroundPresenter;
import com.smartsoft.movietracker.presenter.MovieNavigationPresenter;
import com.smartsoft.movietracker.utils.Constant;
import com.smartsoft.movietracker.utils.FragmentNavigation;
import com.smartsoft.movietracker.utils.StringUtils;

import java.util.ArrayList;
import java.util.Iterator;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieNavigationVerticalGridViewAdapter extends
        RecyclerView.Adapter<MovieNavigationVerticalGridViewAdapter.Holder> {

    private static final String TAG = MovieNavigationVerticalGridViewAdapter.class.getName();
    private ArrayList<Movie> movieList;
    private Context ctx;
    private MovieNavigationPresenter presenter;
    private ArrayList<Genre> selectedGenres;
    private BackgroundPresenter backgroundPresenter;

    MovieNavigationVerticalGridViewAdapter(ArrayList<Movie> movieList, Context ctx,
                                           MovieNavigationPresenter presenter,
                                           ArrayList<Genre> selectedGenres,
                                           BackgroundPresenter backgroundPresenter) {
        this.movieList = movieList;
        this.ctx = ctx;
        this.presenter = presenter;
        this.selectedGenres = selectedGenres;
        this.backgroundPresenter = backgroundPresenter;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.movie_element, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.bind(movieList.get(position), ctx, selectedGenres, backgroundPresenter);

    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    void clearAll() {
        movieList.clear();
    }

    void updateMovieList(ArrayList<Movie> movies) {
        movieList.addAll(movies);
    }


    class Holder extends RecyclerView.ViewHolder {

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

        @BindView(R.id.open_description)
        ImageView openDetailPage;

        boolean isOpen = false;

        Holder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            detailLayout.setClipToOutline(true);

        }


        void bind(Movie movie, Context ctx,
                  ArrayList<Genre> selectedGenres, BackgroundPresenter backgroundPresenter) {


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
                    if (getAdapterPosition() >= (movieList.size() - 1) - Constant.GridView.COLUMN_NUM7) {
                        presenter.loadMovieData(ctx, selectedGenres);
                    }

                    poster.setOutlineProvider(new ViewOutlineProvider() {
                        @Override
                        public void getOutline(View view, Outline outline) {
                            outline.setRoundRect(
                                    Constant.MovieNavigation.offset,
                                    Constant.MovieNavigation.offset,
                                    (int) (view.getWidth() + Constant.MovieNavigation.curveRadius),
                                    view.getHeight(),
                                    Constant.MovieNavigation.curveRadius);
                        }
                    });
                    poster.setClipToOutline(true);
                    Log.e(TAG, ctx.getString(R.string.CardViewOnFocusChangeListener));
                    backgroundPresenter.setBackground(movie.getBackdropPath());
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

            imdbRating.setText(String.format(ctx.getString(R.string.imdb_vote_average), movie.getVoteAverage()));

            Iterator<Genre> genreIterator = selectedGenres.iterator();
            StringBuilder genreTitles = new StringBuilder();
            while (genreIterator.hasNext()) {
                genreTitles.append(genreIterator.next().getName()).append(StringUtils.COMMA_DELIMITER_WITH_SPACE);
            }
            genreTitles.replace(genreTitles.length() - 2, genreTitles.length() - 1, StringUtils.EMPTY_STRING);

            this.genres.setText(genreTitles);

            title.setText(movie.getTitle());

            String[] releaseDateList = new String[Constant.MovieNavigation.releaseDateListSize];
            if (movie.getReleaseDate() != null) {
                releaseDateList = movie.getReleaseDate().split(StringUtils.HYPHEN_DELIMITER);
            } else {
                releaseDateList[0] = StringUtils.EMPTY_STRING;
            }


            releaseDate.setText(releaseDateList[0]);

        }
    }

}

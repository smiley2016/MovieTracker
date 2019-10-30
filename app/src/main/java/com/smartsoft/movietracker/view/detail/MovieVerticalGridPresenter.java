package com.smartsoft.movietracker.view.detail;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.leanback.widget.Presenter;

import com.smartsoft.movietracker.R;
import com.smartsoft.movietracker.model.genre.Genre;
import com.smartsoft.movietracker.model.movie.Movie;
import com.smartsoft.movietracker.presenter.DetailPagePresenter;
import com.smartsoft.movietracker.utils.StringUtils;

import java.util.ArrayList;
import java.util.Iterator;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieVerticalGridPresenter extends Presenter {

    private Context mContext;
    private ArrayList<Genre> allGenres;
    private DetailPagePresenter detailPagePresenter;

    MovieVerticalGridPresenter(ArrayList<Genre> allGenres,
                               DetailPagePresenter detailPagePresenter) {
        this.allGenres = allGenres;
        this.detailPagePresenter = detailPagePresenter;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        mContext = parent.getContext();
        return new PresenterViewHolder(View.inflate(mContext, R.layout.detail_movie_layout, null));
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Object item) {
        PresenterViewHolder holder = (PresenterViewHolder) viewHolder;
        holder.bind((Movie) item);
    }

    @Override
    public void onUnbindViewHolder(ViewHolder viewHolder) {

    }

    class PresenterViewHolder extends ViewHolder {
        @BindView(R.id.detail_movie_title)
        TextView title;
        @BindView(R.id.detail_movie_description)
        TextView movie_description;
        @BindView(R.id.detail_movie_plot)
        TextView plot;
        @BindView(R.id.add_watchlist_button)
        ImageView expand;
        @BindView(R.id.back_button)
        ImageView backButton;

        @BindView(R.id.detail_page_detail_movie_layout)
        ConstraintLayout constraintLayout;

        public PresenterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        public void bind(Movie movie) {
            title.setText(movie.getTitle());


            StringBuilder genreTitles = new StringBuilder();
            if(movie.getGenreIds().size() != 0){
                for(Genre it: allGenres){
                    for(Integer currIt: movie.getGenreIds()){
                        if(currIt.equals(it.getId())){
                            genreTitles.append(it.getName()).append(StringUtils.COMMA_DELIMITER_WITH_SPACE);
                        }
                    }
                }
                genreTitles.replace(genreTitles.length() - 2, genreTitles.length() - 1, StringUtils.EMPTY_STRING);
            }

            movie_description.setText(String.format("%s | %s %s | %s %s",
                    genreTitles, mContext.getString(R.string.year), movie.getReleaseDate(),
                    mContext.getString(R.string.imdb), movie.getVoteAverage()));

            plot.setText(movie.getOverview());

            expand.requestFocus();

            expand.setOnClickListener(view -> {
                if (plot.getMaxLines() == 3) {
                    expand.setForeground(mContext.getDrawable(R.drawable.collapse_icon));
                    plot.setMaxLines(Integer.MAX_VALUE);


                } else {
                    expand.setForeground(mContext.getDrawable(R.drawable.add_watchlist_icon));
                    plot.setMaxLines(3);
                }
            });

            backButton.setOnClickListener(view -> {
                detailPagePresenter.backPressed();
            });
        }
    }
}

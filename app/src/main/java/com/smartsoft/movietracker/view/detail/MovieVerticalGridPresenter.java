package com.smartsoft.movietracker.view.detail;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.leanback.widget.Presenter;

import com.smartsoft.movietracker.R;
import com.smartsoft.movietracker.model.movie.Movie;
import com.smartsoft.movietracker.presenter.DetailPagePresenter;

import java.util.ArrayList;
import java.util.Iterator;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieVerticalGridPresenter extends Presenter {

    private Context mContext;
    private ArrayList<String> currentGenres;
    private DetailPagePresenter detailPagePresenter;

    MovieVerticalGridPresenter(Context mContext,
                               ArrayList<String> currentGenres,
                               DetailPagePresenter detailPagePresenter) {
        this.mContext = mContext;
        this.currentGenres = currentGenres;
        this.detailPagePresenter = detailPagePresenter;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
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

        StringBuilder genreNames;


        public PresenterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            genreNames = new StringBuilder();

        }

        public void bind(Movie movie) {
            title.setText(movie.getTitle());


            Iterator<String> genreNameIterator = currentGenres.iterator();
            while (genreNameIterator.hasNext()) {
                if (genreNameIterator.hasNext()) {
                    genreNames.append(genreNameIterator.next()).append(", ");
                }
            }
            genreNames.replace(genreNames.length() - 2, genreNames.length() - 1, "");

            movie_description.setText(String.format("%s | Year %s | IMDb %s",
                    genreNames, movie.getReleaseDate(),
                    movie.getVoteAverage()));

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

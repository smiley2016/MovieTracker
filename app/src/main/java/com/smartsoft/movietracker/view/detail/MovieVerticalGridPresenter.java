package com.smartsoft.movietracker.view.detail;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.leanback.widget.Presenter;

import com.smartsoft.movietracker.R;
import com.smartsoft.movietracker.model.cast.Cast;
import com.smartsoft.movietracker.model.genre.Genre;
import com.smartsoft.movietracker.model.movie.Movie;
import com.smartsoft.movietracker.presenter.DetailPagePresenter;
import com.smartsoft.movietracker.utils.StringUtils;

import java.util.ArrayList;
import java.util.Iterator;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @see MovieVerticalGridPresenter is
 * a {@link androidx.leanback.widget.VerticalGridView} component
 * for handle the {@link Cast} element rollability in vertical direction
 */
public class MovieVerticalGridPresenter extends Presenter {

    /**
     * Where running the {@link MovieVerticalGridPresenter}
     */
    private Context mContext;

    /**
     * Contains the {@link Genre}s.
     */
    private ArrayList<Genre> allGenres;

    /**
     * The DetailPageFragments presenter reference
     */
    private DetailPagePresenter detailPagePresenter;

    /**
     * Class constructor
     * @param allGenres All genres
     * @param detailPagePresenter Fragment presenter reference
     */
    MovieVerticalGridPresenter(ArrayList<Genre> allGenres,
                               DetailPagePresenter detailPagePresenter) {
        this.allGenres = allGenres;
        this.detailPagePresenter = detailPagePresenter;
    }

    /**
     * This function create the from the XML detail_movie_layout layout a real view
     * @param parent The ViewGroup wherein the
     *               {@link android.view.LayoutInflater} will paints the XML
     * @return {@link MovieVerticalGridPresenter.PresenterViewHolder}
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        mContext = parent.getContext();
        return new PresenterViewHolder(View.inflate(mContext, R.layout.detail_movie_layout, null));
    }

    /**
     * Onto every single element the {@link #onBindViewHolder(ViewHolder, Object)}
     * calls the {@link MovieVerticalGridPresenter.PresenterViewHolder#bind(Movie)} function to initializes it's views
     * how behaves in the app.
     * @param viewHolder {@link androidx.leanback.widget.Presenter.ViewHolder} what holds the elements in
     * {@link androidx.leanback.widget.VerticalGridView}
     * @param item Contains all the elements from the {@link androidx.leanback.widget.ArrayObjectAdapter}
     */
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Object item) {
        PresenterViewHolder holder = (PresenterViewHolder) viewHolder;
        holder.bind((Movie) item);
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
     * Inner class is contains a {@link Movie} views of the element
     * and it's properties
     */
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

        /**
         * Inner class constructor
         * @param view The initialized element
         */
        public PresenterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        /**
         * Initializer function for one element
         * @param movie {@link Movie} object which will be initialize
         */
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

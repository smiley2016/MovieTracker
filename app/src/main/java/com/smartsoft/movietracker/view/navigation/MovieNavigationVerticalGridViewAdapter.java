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
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

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
import com.smartsoft.movietracker.presenter.MovieNavigationPresenter;
import com.smartsoft.movietracker.utils.Constant;
import com.smartsoft.movietracker.utils.FragmentNavigation;

import java.io.Serializable;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieNavigationVerticalGridViewAdapter extends RecyclerView.Adapter<MovieNavigationVerticalGridViewAdapter.Holder> implements Serializable {

    private static final String TAG = "MovieNavigationVertical";
    private ArrayList<Movie> movieList;
    private Context ctx;
    private MovieNavigationPresenter presenter;
    private ArrayList<Genre> genres;
    private Bundle bundle;

    MovieNavigationVerticalGridViewAdapter(ArrayList<Movie> movieList, Context ctx, MovieNavigationPresenter presenter, ArrayList<Genre> genres) {
        this.movieList = movieList;
        this.ctx = ctx;
        this.presenter = presenter;
        this.genres = genres;
        this.bundle = new Bundle();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.movie_element_card, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.bind(movieList.get(position), ctx, presenter, bundle, genres);
        if(position >= movieList.size()-1 % Constant.GenreSelectorFragment.COLUMN_NUM){
           presenter.updateMovieNavigationGridView(ctx, genres);
        }

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
        notifyDataSetChanged();
    }

    public Bundle getBundle() {
        return bundle;
    }


    static class Holder extends RecyclerView.ViewHolder{

        @BindView(R.id.poster)
        ImageView poster;
        @BindView(R.id.open_description)
        ImageView description;
        @BindView(R.id.movie_element_card)
        ConstraintLayout layout;
        @BindView(R.id.spinner)
        ProgressBar progressBar;
        @BindView(R.id.movie_description)
        RelativeLayout detailLayout;

        float curveRadius;

        Holder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            curveRadius = 24f;

            detailLayout.setClipToOutline(true);

        }



        void bind(Movie movie, Context ctx, MovieNavigationPresenter presenter, Bundle bundle, ArrayList<Genre> genres){


            Glide.with(ctx).load(Constant.API.IMAGE_BASE_URL +movie.getPosterPath()).listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    progressBar.setVisibility(View.GONE);
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    progressBar.setVisibility(View.GONE);
                    return false;
                }
            }).error(R.drawable.error).into(poster);



            layout.setOnFocusChangeListener((view, b) -> {
                if(b){
                    poster.setOutlineProvider(new ViewOutlineProvider() {
                        @Override
                        public void getOutline(View view, Outline outline) {
                            outline.setRoundRect(0,0,(int) (view.getWidth()+curveRadius), view.getHeight(), curveRadius);
                        }
                    });
                    poster.setClipToOutline(true);
                    presenter.setBackground(movie.getBackdropPath());
                    detailLayout.setVisibility(View.VISIBLE);
                    Log.e(TAG, ctx.getString(R.string.CardViewOnFocusChangeListener));
                    presenter.setBackground(movie.getBackdropPath());
                }else{
                    poster.setClipToOutline(false);
                    detailLayout.setVisibility(View.GONE);
                }
            });

            layout.setOnClickListener(view -> {
                bundle.putSerializable("movie", movie);
                bundle.putSerializable(ctx.getString(R.string.genres), genres);
                FragmentNavigation.getInstance().showDetailPageFragment();
            });
        }
    }

}

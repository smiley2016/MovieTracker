package com.smartsoft.movietracker.view.navigation;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.makeramen.roundedimageview.RoundedImageView;
import com.smartsoft.movietracker.R;
import com.smartsoft.movietracker.model.Movie;
import com.smartsoft.movietracker.presenter.navigation.MovieNavigationPresenter;
import com.smartsoft.movietracker.utils.Constant;
import com.smartsoft.movietracker.utils.RoundedView;

import java.util.ArrayList;

public class MovieNavigationVerticalGridViewAdapter extends RecyclerView.Adapter<MovieNavigationVerticalGridViewAdapter.Holder> {

    private static final String TAG = "MovieNavigationVertical";
    private ArrayList<Movie> movieList;
    private Context ctx;

    private MovieNavigationPresenter presenter;
    private MovieNavigationPresenter.View view;

    public MovieNavigationVerticalGridViewAdapter(ArrayList<Movie> movieList, Context ctx, MovieNavigationPresenter presenter, MovieNavigationPresenter.View view) {
        this.movieList = movieList;
        this.ctx = ctx;
        this.presenter = presenter;
        this. view = view;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(R.layout.movie_element_card, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.bind(movieList.get(position), ctx);
        if(position >= movieList.size()-1 % Constant.HomeFragment.COLUMN_NUM){
           presenter.updateMovieNavigationGridView(view);
        }

    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public void clearAll() {
        movieList.clear();
    }

    public void updateMovieList(ArrayList<Movie> movies) {
        movieList.addAll(movies);
        notifyDataSetChanged();
    }


    public static class Holder extends RecyclerView.ViewHolder{

        RoundedView poster;
        ImageView description;
        RelativeLayout layout;
        ProgressBar progressBar;
        public Holder(@NonNull View itemView) {
            super(itemView);
            poster = itemView.findViewById(R.id.poster);
            layout = itemView.findViewById(R.id.movie_element_card);
            description = itemView.findViewById(R.id.description);
            progressBar = itemView.findViewById(R.id.spinner);
        }



        public void bind(Movie movie, Context ctx){
            Glide.with(ctx).load(Constant.Common.IMAGE_BASE_URL +movie.getPosterPath()).listener(new RequestListener<Drawable>() {
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

        }
    }

}

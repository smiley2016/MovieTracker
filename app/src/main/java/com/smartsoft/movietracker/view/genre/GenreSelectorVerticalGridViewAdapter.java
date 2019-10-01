package com.smartsoft.movietracker.view.genre;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.smartsoft.movietracker.R;
import com.smartsoft.movietracker.model.genre.Genre;
import com.smartsoft.movietracker.utils.Utils;

import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.Objects;

import static com.smartsoft.movietracker.presenter.GenreSelectorPresenter.TAG;

public class GenreSelectorVerticalGridViewAdapter extends RecyclerView.Adapter<GenreSelectorVerticalGridViewAdapter.RecyclerViewHolder> {

    private Context mContext;
    private ArrayList<Genre> genreList = new ArrayList<>();

    private ArrayList<Genre> genres;
    private Bundle bundle;

    GenreSelectorVerticalGridViewAdapter(Context context, ArrayList<Genre> list) {
        this.mContext = context;
        genreList.addAll(list);
        genres = new ArrayList<>();
        this.bundle = new Bundle();

    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.genre_element_card, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.genreNameTextView.setText(genreList.get(position).getName());
        Glide.with(mContext).load(R.drawable.background).into(holder.genreImageView);
        holder.bind(genreList.get(position));
        holder.layout.setOnClickListener(view -> manageGenreToList(genreList.get(position), position));
    }


    @Override
    public int getItemCount() {
        return genreList.size();
    }

    ArrayList<Genre> getSelectedGenres() {
        return genres;
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder {
        TextView genreNameTextView;
        ImageView genreImageView;
        ConstraintLayout layout;
        ImageView select_icon;

        RecyclerViewHolder(View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.vertical_grid_view_layout_element);
            genreNameTextView = itemView.findViewById(R.id.genre_description);
            genreImageView = itemView.findViewById(R.id.genre_image_view);
            select_icon = itemView.findViewById(R.id.selected_icon);

        }


        void bind(Genre genre) {
            Log.e(TAG, mContext.getString(R.string.bind) + genre.getName() + mContext.getString(R.string.isActivated) + genre.isActivated());
            if (genre.isActivated()) {
                select_icon.setVisibility(View.VISIBLE);
            } else {
                select_icon.setVisibility(View.INVISIBLE);
            }
        }
    }


    private void manageGenreToList(Genre genres, int position) {
        if (this.genres.contains(genres)) {
            this.genres.remove(genres);
            genres.setActivated(false);
            Utils.showToast(mContext, mContext.getResources().getString(R.string.removed_genre) + " " + genres.getName());
        } else {
            this.genres.add(genres);
            genres.setActivated(true);
            Utils.showToast(mContext, mContext.getResources().getString(R.string.added_genre) + " " + genres.getName());
        }
        notifyItemChanged(position);

    }

}

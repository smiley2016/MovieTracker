package com.smartsoft.movietracker.view.genre;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.smartsoft.movietracker.R;
import com.smartsoft.movietracker.model.genre.Genre;
import com.smartsoft.movietracker.utils.Utils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.smartsoft.movietracker.presenter.GenreSelectorPresenter.TAG;

public class GenreSelectorVerticalGridViewAdapter extends RecyclerView.Adapter<GenreSelectorVerticalGridViewAdapter.RecyclerViewHolder> {

    private Context mContext;
    private ArrayList<Genre> genreList = new ArrayList<>();
    private ArrayList<Genre> selectedGenres;

    GenreSelectorVerticalGridViewAdapter(Context context, ArrayList<Genre> list) {
        this.mContext = context;
        genreList.addAll(list);
        selectedGenres = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.genre_element, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.bind(genreList.get(position), position);
    }


    @Override
    public int getItemCount() {
        return genreList.size();
    }

    ArrayList<Genre> getSelectedGenres() {
        return selectedGenres;
    }

    void setSelectedGenres() {
        for(Genre it: genreList){
            if(it.isActivated() && !selectedGenres.contains(it)){
                selectedGenres.add(it);
            }
        }
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.genre_description)
        TextView genreNameTextView;
        @BindView(R.id.genre_image_view)
        ImageView genreImageView;
        @BindView(R.id.vertical_grid_view_layout_element)
        ConstraintLayout layout;
        @BindView(R.id.selected_icon)
        ImageView select_icon;

        RecyclerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }


        void bind(Genre genre, int position) {
            genreNameTextView.setText(genreList.get(position).getName());

            Glide.with(mContext).load(R.mipmap.genre).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(genreImageView);

            layout.setOnClickListener(view -> {
                if(genre.isActivated()){
                    genre.setActivated(false);
                    select_icon.setVisibility(View.INVISIBLE);
                }else{
                    genre.setActivated(true);
                    select_icon.setVisibility(View.VISIBLE);
                }
            });
        }
    }

}

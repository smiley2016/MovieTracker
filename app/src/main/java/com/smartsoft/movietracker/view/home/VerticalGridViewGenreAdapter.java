package com.smartsoft.movietracker.view.home;

import android.content.Context;
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
import com.smartsoft.movietracker.utils.Constant;
import com.smartsoft.movietracker.utils.Util;

import java.util.ArrayList;

public class VerticalGridViewGenreAdapter extends RecyclerView.Adapter<VerticalGridViewGenreAdapter.RecyclerViewHolder> {

    private Context mContext;
    private ArrayList<Genre> genreList = new ArrayList<>();
    private boolean isFirstStart;

    VerticalGridViewGenreAdapter(Context context, ArrayList<Genre> list) {
        this.mContext = context;
        genreList.addAll(list);

    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =  LayoutInflater.from(mContext).inflate(R.layout.genre_element_card, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.genreNameTextView.setText(genreList.get(position).getName());
        Glide.with(mContext).load(R.drawable.background).into(holder.genreImageView);

        setCheckImageVisibility(holder, position);
        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                manageGenreToList(holder, genreList.get(position));
            }
        });

    }

    private void isFirstStart(RecyclerViewHolder holder){
        if(isFirstStart){
            isFirstStart = false;
            holder.layout.requestFocus();
        }
    }


    @Override
    public int getItemCount() {
        return genreList.size();
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder{
        TextView genreNameTextView;
        ImageView genreImageView;
        ConstraintLayout layout;
        ImageView select_icon;
        RecyclerViewHolder(View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.vertical_grid_view_layout_element);
            genreNameTextView = itemView.findViewById(R.id.genre_description);
            genreImageView =  itemView.findViewById(R.id.genre_image_view);
            select_icon = itemView.findViewById(R.id.selected_icon);

        }

    }

    private void setCheckImageVisibility(RecyclerViewHolder viewHolder, int position){
        if(!Constant.Genre.genre.isEmpty() && Constant.Genre.genre.indexOf(genreList.get(position)) != -1){
            viewHolder.select_icon.setVisibility(View.VISIBLE);
        }else {
            viewHolder.select_icon.setVisibility(View.INVISIBLE);
        }
    }


    private void manageGenreToList(RecyclerViewHolder viewHolder, Genre genres) {
        if (Constant.Genre.genre.contains(genres)) {
            Constant.Genre.genre.remove(genres);
            viewHolder.select_icon.setVisibility(View.INVISIBLE);
            Util.showToast(mContext, mContext.getResources().getString(R.string.removed_genre) + " " + genres.getName());
        } else {
            Constant.Genre.genre.add(genres);
            viewHolder.select_icon.setVisibility(View.VISIBLE);
            Util.showToast(mContext, mContext.getResources().getString(R.string.added_genre) + " " + genres.getName());
        }
    }
}

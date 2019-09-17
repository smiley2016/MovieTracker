package com.smartsoft.movietracker.view.home;

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
import com.smartsoft.movietracker.utils.Constant;
import com.smartsoft.movietracker.utils.Util;

import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;

import static com.smartsoft.movietracker.presenter.GenreSelectorPresenter.TAG;

public class VerticalGridViewGenreAdapter extends RecyclerView.Adapter<VerticalGridViewGenreAdapter.RecyclerViewHolder> {

    private Context mContext;
    private ArrayList<Genre> genreList = new ArrayList<>();
    private boolean isFirstStart;

    private ArrayList<Integer> genreIds;
    private Bundle bundle;

    VerticalGridViewGenreAdapter(Context context, ArrayList<Genre> list) {
        this.mContext = context;
        genreList.addAll(list);
        genreIds = new ArrayList<>();
        this.bundle = new Bundle();

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
        holder.bind(genreList.get(position));
        holder.layout.setOnClickListener(view -> manageGenreToList(genreList.get(position), position));


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


        public void bind(Genre genre) {
            Log.e(TAG, "bind: "+genre.getName() + " isActivated " + genre.isActivated());
            if(genre.isActivated()){
                select_icon.setVisibility(View.VISIBLE);
            }else{
                select_icon.setVisibility(View.INVISIBLE);
            }
        }
    }


    public void setBundle(){
        bundle.putIntegerArrayList("genreIds", genreIds);
    }

    public Bundle getBundle(){
        return bundle;
    }


    private void manageGenreToList(Genre genres, int position) {
        if (Constant.Genre.genre.contains(genres)) {
            Constant.Genre.genre.remove(genres);
            genres.setActivated(false);
            Util.showToast(mContext, mContext.getResources().getString(R.string.removed_genre) + " " + genres.getName());
        } else {
            genreIds.add(genres.getId());
            genres.setActivated(true);
            Util.showToast(mContext, mContext.getResources().getString(R.string.added_genre) + " " + genres.getName());
        }
        notifyItemChanged(position);

    }

    public void setList(){
        if(bundle != null && !CollectionUtils.isEmpty(genreIds)){
            for(Integer it: genreIds){
                if(getGenreById(it) != null){
                    getGenreById(it).setActivated(true);
                }
            }
        }
        notifyDataSetChanged();
    }

    public Genre getGenreById(Integer id){
        for(Genre it: genreList){
            if(it.getId().equals(id)){
                return it;
            }
        }
        return null;
    }
}

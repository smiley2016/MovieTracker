package com.smartsoft.movietracker.model.genre;

import java.util.ArrayList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GenreResult {

    @SerializedName("genres")
    @Expose
    private ArrayList<Genre> genres = null;

    public GenreResult(ArrayList<Genre> genres) {
        this.genres = genres;
    }

    public ArrayList<Genre> getGenres() {
        return genres;
    }

    public void setGenres(ArrayList<Genre> genres) {
        this.genres = genres;
    }
}

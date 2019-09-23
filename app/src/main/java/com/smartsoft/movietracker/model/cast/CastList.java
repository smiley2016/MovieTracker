package com.smartsoft.movietracker.model.cast;

import java.util.ArrayList;

public class CastList {
    private ArrayList<Cast> casts;

    public CastList(ArrayList<Cast> cast) {
        this.casts = cast;
    }

    public ArrayList<Cast> getCasts() {
        return casts;
    }

    public void setCasts(ArrayList<Cast> casts) {
        this.casts = casts;
    }
}

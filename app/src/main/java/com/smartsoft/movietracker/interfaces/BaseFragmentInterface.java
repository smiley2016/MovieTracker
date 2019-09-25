package com.smartsoft.movietracker.interfaces;

import android.graphics.drawable.Drawable;

import com.smartsoft.movietracker.model.genre.Genre;

import java.util.ArrayList;

public interface BaseFragmentInterface {

    interface Presenter{
        void setTitle();
        void setBackground(String image);
        void setBackground(Drawable img);
    }

    interface BaseFragmentView{
        void setTitle();
        void setTitle(StringBuilder genreTitle);
        void setBackground(String image);
        void setBackground(Drawable img);
    }



}

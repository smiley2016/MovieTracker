package com.smartsoft.movietracker.interfaces;

import android.graphics.drawable.Drawable;

import io.reactivex.Flowable;

public interface BaseFragmentInterface {

    public interface Presenter{
        void setTitle();
        void setBackground(String image);
        void setBackground(Drawable img);
    }

    public interface BaseFragmentView{
        void setTitle();
        void setBackground(String image);
        void setBackground(Drawable img);
    }



}

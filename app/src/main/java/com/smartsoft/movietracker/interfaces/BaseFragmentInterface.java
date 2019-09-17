package com.smartsoft.movietracker.interfaces;

import android.graphics.drawable.Drawable;

import io.reactivex.Flowable;

public interface BaseFragmentInterface {

    void setTitle();
    void setBackground(String image);
    void setBackground(Drawable img);
    void setVisibleSearchIcon();
    void setInvisibleSearchIcon();
}

package com.smartsoft.movietracker.interfaces;

import android.graphics.drawable.Drawable;

public interface BaseFragmentInterface {

    interface Presenter{
        void setTitle();
        void setBackground(String image);
        void setBackground(Drawable img);
    }

    interface BaseFragmentView{
        void setTitle();
        void setBackground(String image);
        void setBackground(Drawable img);
    }



}

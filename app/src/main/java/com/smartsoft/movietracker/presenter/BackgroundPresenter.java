package com.smartsoft.movietracker.presenter;

import com.smartsoft.movietracker.interfaces.OnBackgroundChangeListener;

public class BackgroundPresenter {

    private OnBackgroundChangeListener onBackgroundChangeListener;

    public BackgroundPresenter(OnBackgroundChangeListener onBackgroundChangeListener) {
        this.onBackgroundChangeListener = onBackgroundChangeListener;
    }

    public void setBackground(String path) {
        onBackgroundChangeListener.onBackgroundChange(path);
    }
}

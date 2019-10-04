package com.smartsoft.movietracker.presenter;

import com.smartsoft.movietracker.interfaces.onBackgroundChangeListener;

public class BackgroundPresenter {

    private onBackgroundChangeListener onBackgroundChangeListener;

    public BackgroundPresenter(onBackgroundChangeListener onBackgroundChangeListener) {
        this.onBackgroundChangeListener = onBackgroundChangeListener;
    }

    public void setBackground(String path) {
        onBackgroundChangeListener.setBackground(path);
    }
}

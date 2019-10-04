package com.smartsoft.movietracker.presenter;

import com.smartsoft.movietracker.interfaces.BackgroundManagerInterface;

public class BackgroundPresenter {

    private BackgroundManagerInterface backgroundManagerInterface;

    public BackgroundPresenter(BackgroundManagerInterface backgroundManagerInterface) {
        this.backgroundManagerInterface = backgroundManagerInterface;
    }

    public void setBackground(String path) {
        backgroundManagerInterface.setBackground(path);
    }
}

package com.smartsoft.movietracker.presenter;

import com.smartsoft.movietracker.interfaces.MainActivityBackgroundInterface;

public class MainActivityBackgroundPresenter {

    private MainActivityBackgroundInterface backgroundInterface;

    public MainActivityBackgroundPresenter(MainActivityBackgroundInterface backgroundInterface) {
        this.backgroundInterface = backgroundInterface;
    }

    public void setBackground(String image){
        backgroundInterface.setBackground(image);
    }
}

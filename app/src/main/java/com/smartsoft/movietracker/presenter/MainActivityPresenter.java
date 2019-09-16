package com.smartsoft.movietracker.presenter;

import com.smartsoft.movietracker.interfaces.MainActivityInterface;

public class MainActivityPresenter {

    private MainActivityInterface backgroundInterface;

    public MainActivityPresenter(MainActivityInterface backgroundInterface) {
        this.backgroundInterface = backgroundInterface;
    }

    public void setBackground(String image){
        backgroundInterface.setBackground(image);
    }

    public void setTitle(){
        backgroundInterface.setTitle();
    }

}

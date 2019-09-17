package com.smartsoft.movietracker.presenter;

import com.smartsoft.movietracker.interfaces.BaseFragmentInterface;

public class MainActivityPresenter {

    private BaseFragmentInterface backgroundInterface;

    public MainActivityPresenter(BaseFragmentInterface backgroundInterface) {
        this.backgroundInterface = backgroundInterface;
    }

    public void setBackground(String image){
        backgroundInterface.setBackground(image);
    }

    public void setTitle(){
        backgroundInterface.setTitle();
    }

}

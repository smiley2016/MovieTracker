package com.smartsoft.movietracker.service;

import android.graphics.drawable.Drawable;

import com.smartsoft.movietracker.interfaces.BaseFragmentInterface;
import com.smartsoft.movietracker.model.genre.Genre;
import com.smartsoft.movietracker.presenter.BaseFragmentPresenter;
import com.smartsoft.movietracker.utils.Constant;

import java.util.Iterator;

public class BaseFragmentComponentSettings {
    private static BaseFragmentComponentSettings sInstance;
    private BaseFragmentInterface.BaseFragmentView baseFragmentInterface;
    private BaseFragmentPresenter presenter;

    public static BaseFragmentComponentSettings getInstance(){
        if(sInstance == null){
            sInstance = new BaseFragmentComponentSettings();
        }
        return sInstance;
    }

    public void initComponents(BaseFragmentInterface.BaseFragmentView baseFragmentInterface, BaseFragmentPresenter presenter){
        this.baseFragmentInterface = baseFragmentInterface;
        this.presenter = presenter;
    }


    public void setTitle() {
        presenter.setTitle();
    }


    public void setBackground(String image) {

        presenter.setBackground(image);

    }


    public void setBackground(Drawable img){
        presenter.setBackground(img);

    }



}

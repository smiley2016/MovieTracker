package com.smartsoft.movietracker.presenter;

import android.graphics.drawable.Drawable;

import com.smartsoft.movietracker.interfaces.BaseFragmentInterface;
import com.smartsoft.movietracker.view.BaseFragment;

public class BaseFragmentPresenter implements BaseFragmentInterface.Presenter{

    private BaseFragmentInterface.BaseFragmentView baseFragmentInterface;

    public BaseFragmentPresenter(BaseFragmentInterface.BaseFragmentView baseFragmentInterface) {
        this.baseFragmentInterface = baseFragmentInterface;
    }

    public void setTitle(){
        baseFragmentInterface.setTitle();
    }

    public void setBackground(String image){
        baseFragmentInterface.setBackground(image);
    }


   public void setBackground(Drawable img){
        baseFragmentInterface.setBackground(img);
    }

}

package com.smartsoft.movietracker;

import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.smartsoft.movietracker.utils.FragmentNavigation;

public class MainActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentNavigation.getInstance().initAttributes(this);
        FragmentNavigation.getInstance().showGenreSelectorFragment();
    }




}





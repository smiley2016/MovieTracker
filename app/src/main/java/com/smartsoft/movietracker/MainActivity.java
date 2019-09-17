package com.smartsoft.movietracker;

import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.smartsoft.movietracker.interfaces.BaseFragmentInterface;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class MainActivity extends FragmentActivity {

    private static final String TAG = "MainActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_base);
        
    }




}





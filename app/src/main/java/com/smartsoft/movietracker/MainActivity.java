package com.smartsoft.movietracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.smartsoft.movietracker.model.Genre;
import com.smartsoft.movietracker.service.RunOnBackgroundService;
import com.smartsoft.movietracker.utils.Constant;
import com.smartsoft.movietracker.utils.FragmentNavigation;
import com.smartsoft.movietracker.view.home.HomeFragment;

import java.util.Iterator;

public class MainActivity extends FragmentActivity {

    public TextView text;
    private ImageView search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        FragmentNavigation.getInstance(this).showHomeFragment(text);

    }

    public void initViews(){
        text = findViewById(R.id.choose_textView);
        search = findViewById(R.id.toolbar_search);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentNavigation.getInstance(getApplicationContext()).showMovieNavigationFragment();
                Iterator<Genre> it = Constant.Genre.genre.iterator();
                StringBuilder genreString = new StringBuilder();
                while(it.hasNext()){
                    genreString.append(it.next().getName()).append(" - ");
                }
                genreString.replace(genreString.length()-3, genreString.length()-1, "");
                text.setText(genreString);
            }
        });

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        FragmentNavigation.getInstance(this).onBackPressed(this, findViewById(R.id.movie_genres_gridView_fragment));
    }
}





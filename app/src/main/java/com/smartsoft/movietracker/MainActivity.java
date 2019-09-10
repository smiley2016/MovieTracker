package com.smartsoft.movietracker;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.bumptech.glide.Glide;
import com.smartsoft.movietracker.interfaces.MainActivityBackgroundInterface;
import com.smartsoft.movietracker.model.genre.Genre;
import com.smartsoft.movietracker.presenter.MainActivityBackgroundPresenter;
import com.smartsoft.movietracker.utils.Constant;
import com.smartsoft.movietracker.utils.Debouncer;
import com.smartsoft.movietracker.utils.FragmentNavigation;

import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class MainActivity extends FragmentActivity implements MainActivityBackgroundInterface {

    public TextView text;
    private ImageView search;
    private ImageView background;

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

        FragmentNavigation.getInstance(this).setBackgroundPresenter(this, new MainActivityBackgroundPresenter(this));
        background = findViewById(R.id.activity_background);
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    @Override
    public void setBackground(String image) {

//        Debouncer debouncer = new Debouncer();
//        debouncer.debounce(image, new Runnable() {
//            @Override
//            public void run() {
//               runOnUiThread(callback.callback());
//        }, 300, TimeUnit.MILLISECONDS);
//
//        myCallback callback = new myCallback() {
//            @Override
//            public void callback() {
//                Glide.with(ctx).load(Constant.Common.IMAGE_ORIGINAL_BASE_URL + image)
//                        .placeholder(background.getDrawable())
//                        .transition(withCrossFade(500))
//                        .into(background);
//            }
//            });
//        }
//
//    }
//
//    public interface myCallback{
//        void callback(Context ctx);
//    }
    }
}





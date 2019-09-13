package com.smartsoft.movietracker;

import android.graphics.drawable.Drawable;
import android.media.midi.MidiDeviceService;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.smartsoft.movietracker.interfaces.MainActivityInterface;
import com.smartsoft.movietracker.model.genre.Genre;
import com.smartsoft.movietracker.utils.Constant;
import com.smartsoft.movietracker.utils.Dialogs;
import com.smartsoft.movietracker.utils.FragmentNavigation;
import com.smartsoft.movietracker.view.home.GenreSelectorFragment;
import com.smartsoft.movietracker.view.navigation.MovieNavigationFragment;

import java.util.Iterator;
import java.util.concurrent.TimeUnit;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class MainActivity extends FragmentActivity implements MainActivityInterface {

    private static final String TAG = "MainActivity";
    public TextView text;
    private ImageView background;
    private ObservableEmitter<String> urlStreamEmitter;
    private ObservableEmitter<Drawable> drawableStreamEmitter;
    private Drawable placeholderDrawable;
    private ImageView search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();


    }


    public void initViews() {
        FragmentNavigation.getInstance(this).showHomeFragment();

        text = findViewById(R.id.choose_textView);
        search = findViewById(R.id.toolbar_search);
        search.setOnClickListener(view -> {
            if (!Constant.Genre.genre.isEmpty()) {
                FragmentNavigation.getInstance(this).showMovieNavigationFragment();
                setInvisibleSearchIcon();
            } else {
                Toast.makeText(MainActivity.this, "Please choose at least a genre", Toast.LENGTH_SHORT).show();
            }

        });
        background = findViewById(R.id.activity_background);

        ImageView settings = findViewById(R.id.toolbar_settings);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(FragmentNavigation.getInstance(MainActivity.this).getCurrentFragment() instanceof MovieNavigationFragment) {
                    Dialogs.startToolbarSettingsDialog(MainActivity.this, (MovieNavigationFragment) FragmentNavigation.getInstance(MainActivity.this).getCurrentFragment());
                    Constant.MovieNavigationFragment.isSorted = true;
                    Constant.API.sortList.clear();
                }else{
                    Dialogs.startToolbarSettingsDialog(MainActivity.this, null);
                    Constant.API.sortList.clear();
                }
            }
        });



        Observable.create((ObservableOnSubscribe<String>) emitter -> urlStreamEmitter = emitter)
                .debounce(1000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String s) {
                        Glide.with(getBaseContext()).asDrawable().load(s).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).transition(withCrossFade(500)).into(new SimpleTarget<Drawable>() {
                            @Override
                            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                if (placeholderDrawable != null)
                                    Glide.with(getBaseContext()).load(s).placeholder(placeholderDrawable).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).transition(withCrossFade(500)).into(background);
                                else {
                                    Glide.with(getBaseContext()).load(s).placeholder(R.drawable.background).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).transition(withCrossFade(500)).into(background);
                                }
                                placeholderDrawable = resource;
                            }
                        });

                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

        Observable.create((ObservableOnSubscribe<Drawable>) emitter -> {
            drawableStreamEmitter = emitter;
        })
                .debounce(1000, TimeUnit.MILLISECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Drawable>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Drawable drawable) {
                        Log.e(TAG, "found image "+drawable);
                        Log.e(TAG, "found second image"+placeholderDrawable);
                        if(drawable != null){
                            Glide.with(getBaseContext()).load(drawable).placeholder(placeholderDrawable).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).transition(withCrossFade(500)).into(background);
                        }
                        placeholderDrawable = drawable;
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e(TAG, "onStart: ");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    @Override
    public void setTitle() {
        Iterator<Genre> it = Constant.Genre.genre.iterator();
        StringBuilder genreString = new StringBuilder();
        while (it.hasNext()) {
            if(it.hasNext()){
                genreString.append(it.next().getName()).append(" - ");
            }
        }
        text.setText(genreString);
    }

    @Override
    public void setBackground(String image) {

        urlStreamEmitter.onNext(Constant.API.IMAGE_ORIGINAL_BASE_URL + image);

    }

    @Override
    public void setBackground(Drawable img){
        drawableStreamEmitter.onNext(img);

    }

    @Override
    public void setVisibleSearchIcon() {
        search.setVisibility(View.VISIBLE);
    }

    @Override
    public void setInvisibleSearchIcon() {
        search.setVisibility(View.INVISIBLE);
    }


}





package com.smartsoft.movietracker;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
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
import com.smartsoft.movietracker.presenter.MainActivityPresenter;
import com.smartsoft.movietracker.presenter.MovieNavigationPresenter;
import com.smartsoft.movietracker.utils.Constant;
import com.smartsoft.movietracker.utils.FragmentNavigation;
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
    private Drawable drawable;
    private ImageView search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        Log.e(TAG, Thread.currentThread().toString());
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
                                if (drawable != null)
                                    Glide.with(getBaseContext()).load(s).placeholder(drawable).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).transition(withCrossFade(500)).into(background);
                                else {
                                    Glide.with(getBaseContext()).load(s).placeholder(R.drawable.background).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).transition(withCrossFade(500)).into(background);
                                }
                                drawable = resource;
                            }
                        });

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
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

        urlStreamEmitter.onNext(Constant.Common.IMAGE_ORIGINAL_BASE_URL + image);

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





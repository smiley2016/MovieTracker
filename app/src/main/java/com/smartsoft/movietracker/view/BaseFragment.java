package com.smartsoft.movietracker.view;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.smartsoft.movietracker.R;
import com.smartsoft.movietracker.interfaces.BaseFragmentInterface;
import com.smartsoft.movietracker.model.genre.Genre;
import com.smartsoft.movietracker.utils.Constant;
import com.smartsoft.movietracker.utils.FragmentNavigation;

import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class BaseFragment extends Fragment implements BaseFragmentInterface {

    private static final String TAG = "MainActivity";


    @BindView(R.id.choose_textView)
    public TextView text;

    @BindView(R.id.fragment_base_background)
    public ImageView background;

    @BindView(R.id.toolbar_search)
    public ImageView search;

    @BindView(R.id.toolbar_settings)
    public ImageView settings;

    protected View rootView;


    private ObservableEmitter<String> urlStreamEmitter;
    private ObservableEmitter<Drawable> drawableStreamEmitter;
    private Drawable placeholderDrawable;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initViews();
        FragmentNavigation.getInstance(getContext()).showHomeFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        if(rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_base, container, false);
        }
        return rootView;
    }

    public void initViews() {
        FragmentNavigation.getInstance(getContext()).showHomeFragment();

        search.setOnClickListener(view -> {
            FragmentNavigation.getInstance(getContext()).showMovieNavigationFragment();
            setInvisibleSearchIcon();


        });

        settings.setOnClickListener(view -> {
            FragmentNavigation.getInstance(getContext()).startToolbarSettingsDialog();
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
                        Glide.with(getContext()).asDrawable().load(s).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).transition(withCrossFade(500)).into(new SimpleTarget<Drawable>() {
                            @Override
                            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                if (placeholderDrawable != null)
                                    Glide.with(getContext()).load(s).placeholder(placeholderDrawable).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).transition(withCrossFade(500)).into(background);
                                else {
                                    Glide.with(getContext()).load(s).placeholder(R.drawable.background).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).transition(withCrossFade(500)).into(background);
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
                            Glide.with(getContext()).load(drawable).placeholder(placeholderDrawable).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).transition(withCrossFade(500)).into(background);
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
    public void onStart() {
        super.onStart();
        Log.e(TAG, "onStart: ");
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

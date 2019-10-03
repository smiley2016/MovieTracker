package com.smartsoft.movietracker.view;

import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.smartsoft.movietracker.R;
import com.smartsoft.movietracker.interfaces.BaseFragmentInterface;
import com.smartsoft.movietracker.interfaces.ToolbarListener;
import com.smartsoft.movietracker.utils.Constant;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public abstract class BaseFragment extends Fragment implements BaseFragmentInterface {

    private static final String TAG = BaseFragment.class.getName();

    protected View rootView;

    private ObservableEmitter<String> urlStreamEmitter;
    private Drawable placeholderDrawable;

    @BindView(R.id.base_toolbar)
    @Nullable
    ToolbarView toolbarView;

    @Nullable
    @BindView(R.id.choose_textView)
    TextView text;

    @Nullable
    @BindView(R.id.fragment_base_background)
    ImageView background;

    protected void initEmitters() {
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
                        Log.d(TAG, "Context when load image: " + getContext() + "  " + s);
                        Glide.with(rootView.getContext()).asDrawable().load(s).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).transition(withCrossFade(500)).into(new CustomTarget<Drawable>() {
                            @Override
                            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                if (placeholderDrawable != null)
                                    Glide.with(rootView.getContext()).load(s).placeholder(placeholderDrawable).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).transition(withCrossFade(500)).into(background);
                                else {
                                    Glide.with(rootView.getContext()).load(s).placeholder(R.drawable.background).diskCacheStrategy(DiskCacheStrategy.NONE).skipMemoryCache(true).transition(withCrossFade(500)).into(background);
                                }
                                placeholderDrawable = resource;
                            }

                            @Override
                            public void onLoadCleared(@Nullable Drawable placeholder) {

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

    }

    protected void initViews() {
        ButterKnife.bind(this, rootView);

    }

    @Override
    public void setTitle(String genreTitle) {
        text.setText(genreTitle);
    }

    @Override
    public void setBackground(String image) {

        urlStreamEmitter.onNext(Constant.API.IMAGE_ORIGINAL_BASE_URL + image);

    }


    protected void setToolbarSearchButtonVisibility(int visibility) {
        if(toolbarView != null){
            toolbarView.setVisibleSearchIcon(visibility);
        }

    }

    protected void setToolbarView(ToolbarListener listener) throws NullPointerException {
        toolbarView.setListener(listener);
    }

    public abstract void InternetConnected();

}

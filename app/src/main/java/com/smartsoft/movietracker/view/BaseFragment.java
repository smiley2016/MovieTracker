package com.smartsoft.movietracker.view;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.smartsoft.movietracker.MainActivity;
import com.smartsoft.movietracker.utils.NetworkChangeReceiver;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public abstract class BaseFragment extends Fragment {

    private static final String TAG = BaseFragment.class.getName();

    protected View rootView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public abstract void InternetConnected();



}

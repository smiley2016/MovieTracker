package com.smartsoft.movietracker.utils;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.smartsoft.movietracker.MainActivity;
import com.smartsoft.movietracker.R;
import com.smartsoft.movietracker.view.NoInternetFragment;
import com.smartsoft.movietracker.view.detail.DetailPageFragment;
import com.smartsoft.movietracker.view.genre.GenreSelectorFragment;
import com.smartsoft.movietracker.view.navigation.MovieNavigationFragment;
import com.smartsoft.movietracker.view.player.PlayerFragment;

public class FragmentNavigation {
    private static final String TAG = FragmentNavigation.class.getSimpleName();
    private static FragmentNavigation sInstance;
    private FragmentManager mFragmentManager;
    private int mMainActivityFragmentContainer;
    private Bundle bundle;
    private Context ctx;

    public static FragmentNavigation getInstance() {
        if (sInstance == null) {
            sInstance = new FragmentNavigation();
        }

        return sInstance;
    }

    private FragmentNavigation() {
        mMainActivityFragmentContainer = R.id.fragment_holder;
    }

    public void initAttributes(Activity activity) {
        mFragmentManager = ((MainActivity) activity).getSupportFragmentManager();
        this.ctx = activity;
        bundle = new Bundle();
    }


    public void showGenreSelectorFragment() {
        Fragment myCurrentFragment = Fragment.instantiate(ctx, GenreSelectorFragment.class.getName(), null);
        replaceFragment(myCurrentFragment, mMainActivityFragmentContainer, false);
        Log.e(TAG, ctx.getString(R.string.genreSelectorFragment) + myCurrentFragment);
    }


    public void showMovieNavigationFragment(Bundle bundle) {

        Fragment myCurrentFragment = Fragment.instantiate(ctx, MovieNavigationFragment.class.getName(), bundle);
        replaceFragment(myCurrentFragment, mMainActivityFragmentContainer, true);
    }

    public void showDetailPageFragment(Bundle bundle) {
        Fragment myCurrentFragment = Fragment.instantiate(ctx, DetailPageFragment.class.getName(), bundle);
        replaceFragment(myCurrentFragment, mMainActivityFragmentContainer, true);
    }

    public void showPlayerFragment(Bundle bundle) {
        Fragment myCurrentFragment;
        this.bundle = bundle;

        myCurrentFragment = Fragment.instantiate(ctx, PlayerFragment.class.getName(), this.bundle);
        replaceFragment(myCurrentFragment, mMainActivityFragmentContainer, true);
    }

    public void showNoInternetFragment() {
        Fragment myCurrentFragment;
        myCurrentFragment = Fragment.instantiate(ctx, NoInternetFragment.class.getName(), bundle);
        addFragment(myCurrentFragment);
    }

    private void addFragment(Fragment myCurrentFragment) {
        mFragmentManager.beginTransaction().add(myCurrentFragment, myCurrentFragment.getTag()).commit();
    }

    public Fragment getCurrentFragment() {
        return mFragmentManager.findFragmentById(R.id.fragment_holder);

    }

    public void removeFragment(Fragment fragment) {
        mFragmentManager.beginTransaction().remove(fragment).commit();
    }

    private void replaceFragment(Fragment fragment, int container, boolean addToBackStack) {
        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();

        mFragmentTransaction.setReorderingAllowed(false);

        mFragmentTransaction.replace(container, fragment, fragment.getTag());

        if (addToBackStack) {
            mFragmentTransaction.addToBackStack(fragment.getClass().getSimpleName());
        }
        try {
            mFragmentTransaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


}

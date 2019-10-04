package com.smartsoft.movietracker.utils;

import android.app.Activity;
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

    private FragmentNavigation() {
        mMainActivityFragmentContainer = R.id.fragment_holder;
    }

    public static FragmentNavigation getInstance() {
        if (sInstance == null) {
            sInstance = new FragmentNavigation();
        }

        return sInstance;
    }

    private Fragment setFragmentArguments(Fragment fragment, Bundle bundle) {
        if(bundle == null){
            fragment.setArguments(new Bundle());
            return fragment;
        }
        fragment.setArguments(bundle);
        return fragment;
    }

    public void initAttributes(Activity activity) {
        mFragmentManager = ((MainActivity) activity).getSupportFragmentManager();
    }


    public void showGenreSelectorFragment() {
        Fragment myCurrentFragment = setFragmentArguments(new GenreSelectorFragment(), null);
        replaceFragment(myCurrentFragment, mMainActivityFragmentContainer, false);
        Log.e(TAG, "showGenreSelectorFragment:" + myCurrentFragment);
    }


    public void showMovieNavigationFragment(Bundle bundle) {
        Fragment myCurrentFragment = setFragmentArguments(new MovieNavigationFragment(), bundle);
        replaceFragment(myCurrentFragment, mMainActivityFragmentContainer, true);
    }

    public void showDetailPageFragment(Bundle bundle) {
        Fragment myCurrentFragment = setFragmentArguments(new DetailPageFragment(), bundle);
        replaceFragment(myCurrentFragment, mMainActivityFragmentContainer, true);
    }

    public void showPlayerFragment(Bundle bundle) {
        Fragment myCurrentFragment = setFragmentArguments(new PlayerFragment(), bundle);
        replaceFragment(myCurrentFragment, mMainActivityFragmentContainer, true);
    }

    void showNoInternetFragment() {
        Fragment myCurrentFragment = setFragmentArguments(new NoInternetFragment(), null);
        addFragment(myCurrentFragment);
    }

    private void addFragment(Fragment myCurrentFragment) {
        mFragmentManager.beginTransaction().add(myCurrentFragment, myCurrentFragment.getTag()).commit();
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

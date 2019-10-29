package com.smartsoft.movietracker.utils;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.smartsoft.movietracker.MainActivity;
import com.smartsoft.movietracker.R;
import com.smartsoft.movietracker.view.detail.DetailPageFragment;
import com.smartsoft.movietracker.view.internet.NoInternetDialogFragment;
import com.smartsoft.movietracker.view.main.genre.GenreSelectorFragment;
import com.smartsoft.movietracker.view.main.navigation.MovieNavigationFragment;
import com.smartsoft.movietracker.view.player.PlayerFragment;

public class FragmentNavigation {
    private static final String TAG = FragmentNavigation.class.getSimpleName();
    private static FragmentNavigation sInstance;
    private FragmentManager mFragmentManager;
    private int mMainActivityFragmentContainer;
    private FragmentTransaction mFragmentTransaction;

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
        fragment.setArguments(bundle);
        return fragment;
    }

    public void initAttributes(Activity activity) {
        mFragmentManager = ((MainActivity) activity).getSupportFragmentManager();
    }


    public void showGenreSelectorFragment() {
        Fragment myCurrentFragment = setFragmentArguments(new GenreSelectorFragment(), new Bundle());
        replaceFragment(myCurrentFragment, mMainActivityFragmentContainer);
    }


    public void showMovieNavigationFragment(Bundle bundle) {
        Fragment myCurrentFragment = setFragmentArguments(new MovieNavigationFragment(), bundle);
        replaceFragment(myCurrentFragment, mMainActivityFragmentContainer);
    }

    public void showDetailPageFragment(Bundle bundle) {
        Fragment myCurrentFragment = setFragmentArguments(new DetailPageFragment(), bundle);
        replaceFragment(myCurrentFragment, mMainActivityFragmentContainer);
    }

    public void showPlayerFragment(Bundle bundle) {
        Fragment myCurrentFragment = setFragmentArguments(new PlayerFragment(), bundle);
        replaceFragment(myCurrentFragment, mMainActivityFragmentContainer);
    }

    public void showNoInternetFragment() {
        DialogFragment noInternetFragment = new NoInternetDialogFragment();
        noInternetFragment.setArguments(new Bundle());
        noInternetFragment.show(mFragmentManager, Constant.FragmentNavigation.DialogFragment);
    }

    public Fragment getCurrentFragment() {
        return mFragmentManager.findFragmentById(mMainActivityFragmentContainer);
    }

    public void removeNoInternetDialogFragmentFromBackStack() {
        Fragment myCurrentFragment = mFragmentManager.findFragmentByTag(Constant.FragmentNavigation.DialogFragment);
        mFragmentTransaction = mFragmentManager.beginTransaction();

        if (myCurrentFragment != null) {
            mFragmentTransaction.remove(myCurrentFragment);
        }
        mFragmentTransaction.addToBackStack(null);
    }

    public void dismissFragment() {
        Fragment myCurrentFragment = mFragmentManager.findFragmentByTag(Constant.FragmentNavigation.DialogFragment);

        if (myCurrentFragment != null) {
            ((NoInternetDialogFragment) myCurrentFragment).dismiss();
        }
    }

    public int getBackStackEntryCount() {
        return mFragmentManager.getBackStackEntryCount();
    }


    private void replaceFragment(Fragment fragment, int container) {
        mFragmentTransaction = mFragmentManager.beginTransaction();

        mFragmentTransaction.setReorderingAllowed(false);

        mFragmentTransaction.replace(container, fragment, fragment.getTag());

        mFragmentTransaction.addToBackStack(fragment.getClass().getSimpleName());

        try {
            mFragmentTransaction.commit();
            Log.d(TAG, "replaceFragment: ");
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        }

    }


}

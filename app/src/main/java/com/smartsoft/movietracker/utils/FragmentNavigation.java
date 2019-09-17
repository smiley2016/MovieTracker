package com.smartsoft.movietracker.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Switch;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.smartsoft.movietracker.MainActivity;
import com.smartsoft.movietracker.R;
import com.smartsoft.movietracker.view.BaseFragment;
import com.smartsoft.movietracker.view.home.GenreSelectorFragment;
import com.smartsoft.movietracker.view.navigation.MovieNavigationFragment;

import butterknife.BindInt;
import butterknife.BindView;

public class FragmentNavigation {
    private static final String TAG = FragmentNavigation.class.getSimpleName();
    private static FragmentNavigation sInstance;
    private FragmentManager mFragmentManager;
    private int mMainActivityFragmentContainer;
    private int mFragmentHolder;
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
        mFragmentHolder = R.id.gridView_container;
    }

    public void initAttributes(Activity activity){
        mFragmentManager =  ((MainActivity)activity).getSupportFragmentManager();
        this.ctx = activity;
    }

    public void clearInstance(){
        sInstance = null;
    }

    public void showBaseFragment(){
        Fragment myCurrentFragment = Fragment.instantiate(ctx, BaseFragment.class.getName(), bundle);
        replaceFragment(myCurrentFragment, mMainActivityFragmentContainer, true);
    }


    public void showHomeFragment(){
        Fragment myCurrentFragment = new GenreSelectorFragment();
        replaceFragment(myCurrentFragment, mFragmentHolder, false);
        Log.e(TAG, "showHomeFragment: " + myCurrentFragment );
    }

    public void showMovieNavigationFragment(){

        Fragment myCurrentFragment = getCurrentFragment();
        if( myCurrentFragment instanceof GenreSelectorFragment){
            ((GenreSelectorFragment)myCurrentFragment).setAdapterBundle();
            bundle = new Bundle();
            if(((GenreSelectorFragment)myCurrentFragment).getAdapterBundle() != null){
                bundle = ((GenreSelectorFragment)myCurrentFragment).getAdapterBundle();
            }
        }

        myCurrentFragment = Fragment.instantiate(ctx, MovieNavigationFragment.class.getName(), bundle);
        replaceFragment(myCurrentFragment, mFragmentHolder, true);
    }

    private Fragment getCurrentFragment(){
        return mFragmentManager.findFragmentById(R.id.gridView_container);

    }

    private void replaceFragment(Fragment fragment, int container, boolean addToBackStack) {
        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
        // if there is layout_fragment to replace, then replace it:
        mFragmentTransaction.setReorderingAllowed(false);
        mFragmentTransaction.replace(container, fragment, fragment.getTag());
        if(addToBackStack){
            mFragmentTransaction.addToBackStack(fragment.getClass().getSimpleName());
        }
        try {
            mFragmentTransaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }


        //mFragmentManager.executePendingTransactions();


    }






}

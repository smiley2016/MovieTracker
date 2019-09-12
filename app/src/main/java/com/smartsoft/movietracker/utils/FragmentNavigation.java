package com.smartsoft.movietracker.utils;

import android.content.Context;
import android.os.Handler;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.smartsoft.movietracker.MainActivity;
import com.smartsoft.movietracker.R;
import com.smartsoft.movietracker.interfaces.MainActivityInterface;
import com.smartsoft.movietracker.presenter.MainActivityPresenter;
import com.smartsoft.movietracker.view.home.GenreSelectorFragment;
import com.smartsoft.movietracker.view.navigation.MovieNavigationFragment;

public class FragmentNavigation extends Fragment {
    private static final String TAG = FragmentNavigation.class.getSimpleName();
    private static FragmentNavigation sInstance;
    private static FragmentManager mFragmentManager;
    private static int mMainActivityFragmentContainer;
    private static Context ctx;


    public static FragmentNavigation getInstance(Context context) {
        ctx = context;
        if (sInstance == null) {
            mMainActivityFragmentContainer = R.id.movie_genres_gridView_fragment;
            sInstance = new FragmentNavigation();
            mFragmentManager = ((MainActivity)context).getSupportFragmentManager();
        }

        return sInstance;
    }

    public void clearInstance(){
        sInstance = null;
    }


    public void showHomeFragment(){
        replaceFragment(new GenreSelectorFragment(), mMainActivityFragmentContainer, false);
    }

    public void showMovieNavigationFragment(){
        replaceFragment(new MovieNavigationFragment(), mMainActivityFragmentContainer, true);
    }



    private void replaceFragment(Fragment fragment, int container, boolean addToBackStack) {
        if(!((MainActivity)ctx).isDestroyed()){
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


            mFragmentManager.executePendingTransactions();
        }


    }




}

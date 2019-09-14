package com.smartsoft.movietracker.utils;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.smartsoft.movietracker.MainActivity;
import com.smartsoft.movietracker.R;
import com.smartsoft.movietracker.view.home.GenreSelectorFragment;
import com.smartsoft.movietracker.view.navigation.MovieNavigationFragment;

public class BaseFragment extends Fragment {
    private static final String TAG = BaseFragment.class.getSimpleName();
    private static Handler mHandler = new Handler();
    private static Context ctx;
    private static BaseFragment sInstance;
    private static FragmentManager mFragmentManager;
    private static FragmentTransaction mFragmentTransaction;
    public static int mMainActivityFragmentContainer;
    protected View rootView;


    public static BaseFragment getInstance(Context context) {
        ctx = context;
        if (sInstance == null) {
            mMainActivityFragmentContainer = R.id.movie_genres_gridView_fragment;
            sInstance = new BaseFragment();
            mFragmentManager = ((MainActivity) context).getSupportFragmentManager();
        }

        return sInstance;
    }


    public void showHomeFragment(TextView textView){
        textView.setText(ctx.getResources().getString(R.string.choose_genre_textView));
        replaceFragment(new GenreSelectorFragment(), mMainActivityFragmentContainer, false);
    }

    public void showMovieNavigationFragment(){
        replaceFragment(new MovieNavigationFragment(), mMainActivityFragmentContainer, true);
    }

    public void popBackstack() {
        mFragmentManager.popBackStack();
    }

    private void addFragment(Fragment fragment, int container) {
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.add(container, fragment, fragment.getTag());
        mFragmentTransaction.addToBackStack(null);
        try {
            mFragmentTransaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void removeFragment(Fragment fragment) {
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.remove(fragment);
        mFragmentTransaction.commit();
    }

    private void replaceFragment(Fragment fragment, int container, boolean addToBackStack) {
        mFragmentTransaction = mFragmentManager.beginTransaction();
        Fragment topFragment = mFragmentManager.findFragmentById(container);

            // if there is fragment to replace, then replace it:
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

    public Fragment getCurrentFragment(int container) {
        return mFragmentManager.findFragmentById(container);
    }


    public void onBackPressed(MainActivity activity, View view) {

        // If Home page is open: double press exit:
        if( getCurrentFragment(mMainActivityFragmentContainer) instanceof GenreSelectorFragment) {
            activity.moveTaskToBack(true);
            return;
        }

        if( getCurrentFragment(mMainActivityFragmentContainer) instanceof MovieNavigationFragment) {
            showHomeFragment(activity.text);
        }
    }

}

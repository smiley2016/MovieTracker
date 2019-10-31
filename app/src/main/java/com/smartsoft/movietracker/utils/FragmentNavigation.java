package com.smartsoft.movietracker.utils;

import android.app.Activity;
import android.os.Bundle;
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

/**
 * A base util class the FragmentNavigation
 * Is used for to handle the {@link Fragment} replacement,
 * setArguments for they.
 */

public class FragmentNavigation {

    /**
     * The Singleton class instance
     */
    private static FragmentNavigation sInstance;

    /**
     * FragmentManager for the fragment methods
     */
    private FragmentManager mFragmentManager;

    /**
     * Here load the fragments on ui (Holder)
     */
    private int mMainActivityFragmentContainer;

    /**
     * This variable used for the fragment transaction methods.
     * Especially #add(), #remove(), add() methods and so on
     */
    private FragmentTransaction mFragmentTransaction;

    /**
     * Private constructor of the class.
     * Here sets the program the
     * fragment holder
     */
    private FragmentNavigation() {
        mMainActivityFragmentContainer = R.id.fragment_holder;
    }

    /**
     * The singleton class base function
     * @see #getInstance() is used for
     * make a new instance from the {@link FragmentNavigation}
     * @return the class new instance if the instance was null,
     * or if the instance wasn't null then the already instantiated
     * instance will be returned
     */
    public static FragmentNavigation getInstance() {
        if (sInstance == null) {
            sInstance = new FragmentNavigation();
        }

        return sInstance;
    }

    /**
     * @see #setFragmentArguments(Fragment, Bundle) function
     * set the Bundle as an argument to the fragment which just now was instantiated
     * and the current fragment will be replaced onto this fragment
     * @param fragment just now instantiated fragment
     * @param bundle Bundle which will be set as argument to fragments
     * @return the instantiated fragment with arguments.
     */
    private Fragment setFragmentArguments(Fragment fragment, Bundle bundle) {
        fragment.setArguments(bundle);
        return fragment;
    }

    /**
     * Here makes reference to FragmentManager
     * @param activity is used to get the reference from the activity because
     * it has fragmentManager
     */
    public void initAttributes(Activity activity) {
        mFragmentManager = ((MainActivity) activity).getSupportFragmentManager();
    }

    /**
     * Here instantiate the new {@link GenreSelectorFragment}, add the arguments as
     * {@link Bundle}, here calls the
     * @see #replaceFragment(Fragment, int) onto the newly created fragment
     */
    public void showGenreSelectorFragment() {
        Fragment myCurrentFragment = setFragmentArguments(new GenreSelectorFragment(), new Bundle());
        replaceFragment(myCurrentFragment, mMainActivityFragmentContainer);
    }

    /**
     * Here instantiate the new {@link MovieNavigationFragment}, add the arguments as
     * {@link Bundle}, here calls the
     * @see #replaceFragment(Fragment, int) onto the newly created fragment
     */
    public void showMovieNavigationFragment(Bundle bundle) {
        Fragment myCurrentFragment = setFragmentArguments(new MovieNavigationFragment(), bundle);
        replaceFragment(myCurrentFragment, mMainActivityFragmentContainer);
    }

    /**
     * Here instantiate the new {@link DetailPageFragment}, add the arguments as
     * {@link Bundle}, here calls the
     * @see #replaceFragment(Fragment, int) onto the newly created fragment
     */
    public void showDetailPageFragment(Bundle bundle) {
        Fragment myCurrentFragment = setFragmentArguments(new DetailPageFragment(), bundle);
        replaceFragment(myCurrentFragment, mMainActivityFragmentContainer);
    }

    /**
     * Here instantiate the new {@link PlayerFragment}, add the arguments as
     * {@link Bundle}, here calls the
     * @see #replaceFragment(Fragment, int) onto the newly created fragment
     */
    public void showPlayerFragment(Bundle bundle) {
        Fragment myCurrentFragment = setFragmentArguments(new PlayerFragment(), bundle);
        replaceFragment(myCurrentFragment, mMainActivityFragmentContainer);
    }

    /**
     * Here instantiate the new {@link NoInternetDialogFragment}, add the arguments as
     * {@link Bundle}, here calls the
     * @see #replaceFragment(Fragment, int) onto the newly created fragment
     */
    public void showNoInternetFragment() {
        DialogFragment noInternetFragment = new NoInternetDialogFragment();
        noInternetFragment.setArguments(new Bundle());
        noInternetFragment.show(mFragmentManager, Constant.FragmentNavigation.DialogFragment);
    }

    /**
     * This function search in the fragment holder
     * which fragment is in it
     * @return the fragment which is loaded in the holder
     */
    public Fragment getCurrentFragment() {
        return mFragmentManager.findFragmentById(mMainActivityFragmentContainer);
    }

    /**
     * If something went wrong and the {@link NoInternetDialogFragment}
     * remained on the UI then this function delete it from
     * the backstack.
     */
    public void removeNoInternetDialogFragmentFromBackStack() {
        Fragment myCurrentFragment = mFragmentManager.findFragmentByTag(Constant.FragmentNavigation.DialogFragment);
        mFragmentTransaction = mFragmentManager.beginTransaction();

        if (myCurrentFragment != null) {
            mFragmentTransaction.remove(myCurrentFragment);
        }
        mFragmentTransaction.addToBackStack(null);
    }

    /**
     * If sometimes the internet just gone
     * then we call the {@link NoInternetDialogFragment}
     * and when the internet come back we have to dismiss the
     * {@link NoInternetDialogFragment} from the UI, and there calls the app
     * @see #dismissFragment() function
     */
    public void dismissFragment() {
        Fragment myCurrentFragment = mFragmentManager.findFragmentByTag(Constant.FragmentNavigation.DialogFragment);

        if (myCurrentFragment != null) {
            ((NoInternetDialogFragment) myCurrentFragment).dismiss();
        }
    }


    /**
     * With this function we check how many fragments
     * was added to the backstack
     * @return number of fragments in backstack (size of the backstack)
     */
    public int getBackStackEntryCount() {
        return mFragmentManager.getBackStackEntryCount();
    }

    /**
     * The fragment replacement function is used for
     * replace a fragment to another fragment into fragment holder
     * @param fragment is the newly instantiated and replaceable {@link Fragment}
     * @param container is the fragment holder where makes the replacement
     */
    private void replaceFragment(Fragment fragment, int container) {
        mFragmentTransaction = mFragmentManager.beginTransaction();

        mFragmentTransaction.setReorderingAllowed(false);

        mFragmentTransaction.replace(container, fragment, fragment.getTag());

        mFragmentTransaction.addToBackStack(fragment.getClass().getSimpleName());

        try {
            mFragmentTransaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}

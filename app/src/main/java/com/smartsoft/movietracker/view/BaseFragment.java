package com.smartsoft.movietracker.view;

import android.view.View;

import androidx.fragment.app.Fragment;

import com.smartsoft.movietracker.utils.FragmentNavigation;

/**
 * This is the main fragment who is extended
 * from the {@link Fragment} and contains the most based functions of the
 * app that used in every Fragment
 */
public abstract class BaseFragment extends Fragment {

    /**
     * This contains the inflated View
     */
    protected View rootView;

    /**
     * This function dismiss the {@link com.smartsoft.movietracker.view.internet.NoInternetDialogFragment}
     * when the Internet comes back but it has overrode in the extended
     * fragments
     */
    public void onInternetConnected() {
        FragmentNavigation.getInstance().dismissFragment();
    }

    /**
     * This function calls the {@link FragmentNavigation#removeNoInternetDialogFragmentFromBackStack()}
     * because this checks if the {@link com.smartsoft.movietracker.view.internet.NoInternetDialogFragment}
     * is still in the backStack or not, and finally create a new instance from the
     * {@link com.smartsoft.movietracker.view.internet.NoInternetDialogFragment}
     * if there is "no internet" and that's the most important because this function will be called
     * in this situation.
     */
    public void onInternetDisconnected() {

        FragmentNavigation.getInstance().removeNoInternetDialogFragmentFromBackStack();

        FragmentNavigation.getInstance().showNoInternetFragment();
    }


}

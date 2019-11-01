package com.smartsoft.movietracker;

import android.app.Activity;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.smartsoft.movietracker.utils.FragmentNavigation;
import com.smartsoft.movietracker.utils.NetworkChangeReceiver;

/**
 * This Activity start the app, and handle the {@link NetworkChangeReceiver}
 * class for the internet connection
 */
public class MainActivity extends FragmentActivity {

    /**
     * Reference to the overrode {@link android.content.BroadcastReceiver}
     */
    private NetworkChangeReceiver networkChangeReceiver;

    /**
     * Activity lifecycle function
     * This function create the Activity and it's content view
     * And calls the Fragment creator to initializes it
     * and create the new and first instance from
     * {@link com.smartsoft.movietracker.view.main.genre.GenreSelectorFragment}
     *
     * @param savedInstanceState Activity state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentNavigation.getInstance().initAttributes(this);
        FragmentNavigation.getInstance().showGenreSelectorFragment();
    }

    /**
     * Activity function calls when the user click the back button
     * In this case the app decide if the user on the first
     * fragment or not. If is on the first fragment then it will exit the app.
     * If not then just move back onto an earlier fragment
     */
    @Override
    public void onBackPressed() {
        if (FragmentNavigation.getInstance().getBackStackEntryCount() == 1) {
            finishAffinity();
        } else {
            super.onBackPressed();
        }
    }

    /**
     * Here instantiated the {@link NetworkChangeReceiver}
     */
    @Override
    protected void onResume() {
        super.onResume();
        networkChangeReceiver = new NetworkChangeReceiver();
        registerNetworkBroadcastReceiver();
    }

    /**
     * Here registers the app the receiver
     */
    private void registerNetworkBroadcastReceiver() {
        getApplicationContext().registerReceiver(networkChangeReceiver,
                new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    /**
     * Here unregisters the app the receiver
     */
    private void unregisterNetworkBroadcastReceiver() {
        try {
            getApplicationContext().unregisterReceiver(networkChangeReceiver);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    /**
     * If the Activity state is moved on the {@link Activity#onPause()}
     * then the app has to unregister the {@link NetworkChangeReceiver}
     * because it's called when the user put into the background the app
     * or exited from it.
     */
    @Override
    protected void onPause() {
        super.onPause();
        unregisterNetworkBroadcastReceiver();
    }

}





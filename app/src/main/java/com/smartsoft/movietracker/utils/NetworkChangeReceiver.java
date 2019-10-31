package com.smartsoft.movietracker.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.smartsoft.movietracker.view.BaseFragment;

/**
 * @see NetworkChangeReceiver class is a type of a BoadcastReceiver
 * wtih what we can check if there is internet connection or not
 */
public class NetworkChangeReceiver extends BroadcastReceiver {

    /**
     * This parameter is used when the app is just started
     * and this time the {@link NetworkChangeReceiver} is registered
     * and checks if there is internet or not.
     * Especially we use for stop the Receiver to calls
     * anything from the current fragment
     */
    private boolean firstLaunch;

    /**
     * Public class constructor
     * @see #firstLaunch variable here will be set
     */
    public NetworkChangeReceiver() {
        this.firstLaunch = true;
    }

    /**
     * The {@link BroadcastReceiver#onReceive(Context, Intent)} function called when
     * This method is called when the BroadcastReceiver is receiving an Intent
     * broadcast.
     * When the internet gone or come back in this {@link BroadcastReceiver#onReceive(Context, Intent)}
     * function the app calls the correspond methods from current fragment which is extended from
     * {@link BaseFragment}
     * Here handles the app the {@link NetworkChangeReceiver#firstLaunch} problem.
     * Exactly at the first launch in the App life, the app doesn't do anything.
     * @param context The Context in which the receiver is running.
     * @param intent The Intent being received.
     */
    @Override
    public void onReceive(Context context, Intent intent) {

        try {
            if (!firstLaunch && Utils.isOnline(context)) {
                ((BaseFragment) FragmentNavigation.getInstance().getCurrentFragment()).onInternetConnected();
            }
            if (!Utils.isOnline(context)) {
                firstLaunch = false;
                ((BaseFragment) FragmentNavigation.getInstance().getCurrentFragment()).onInternetDisconnected();
            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}

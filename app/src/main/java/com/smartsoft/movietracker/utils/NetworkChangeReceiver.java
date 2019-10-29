package com.smartsoft.movietracker.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.smartsoft.movietracker.view.BaseFragment;

public class NetworkChangeReceiver extends BroadcastReceiver {

    private boolean firstLaunch;

    public NetworkChangeReceiver() {
        this.firstLaunch = true;

    }

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

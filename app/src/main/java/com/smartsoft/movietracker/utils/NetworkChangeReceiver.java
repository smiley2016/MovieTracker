package com.smartsoft.movietracker.utils;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.smartsoft.movietracker.R;

public class NetworkChangeReceiver extends BroadcastReceiver {

    private static final String TAG = NetworkChangeReceiver.class.getName();

    private static NetworkChangeReceiver sInstance;

    public static NetworkChangeReceiver getInstance() {
        if(sInstance == null){
            sInstance = new NetworkChangeReceiver();
        }
        return sInstance;
    }

    @Override
    public void onReceive(Context context, Intent intent) {

        try {
            if (Utils.isOnline(context)) {
                Utils.showToast(context, context.getString(R.string.internet_connected));
            } else {
                Utils.showToast(context, context.getString(R.string.no_internet_connection));
                FragmentNavigation.getInstance().showNoInternetFragment();

            }
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }


}

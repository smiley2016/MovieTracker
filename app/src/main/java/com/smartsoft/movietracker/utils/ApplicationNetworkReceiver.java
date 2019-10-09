package com.smartsoft.movietracker.utils;

import android.app.Application;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Handler;

public class ApplicationNetworkReceiver extends Application {

    private NetworkChangeReceiver networkChangeReceiver;
    private Handler handler;
    private Runnable runnable;

    @Override
    public void onCreate() {
        super.onCreate();
        initNetworkHandler();
        networkHandler();
    }

    private void initNetworkHandler() {

        handler = new Handler();
        runnable = () -> {
            if (getApplicationContext() != null) {
                networkChangeReceiver = new NetworkChangeReceiver();
                registerNetworkBroadcastReceiver();
            }
        };
    }

    private void registerNetworkBroadcastReceiver() {
        getApplicationContext().registerReceiver(networkChangeReceiver,
                new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    private void unregisterNetworkBroadcastReceiver() {
        try {
            getApplicationContext().unregisterReceiver(networkChangeReceiver);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    private void networkHandler() {
        handler.removeCallbacks(runnable);
        handler.postDelayed(runnable, 2000);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        unregisterNetworkBroadcastReceiver();
    }

}

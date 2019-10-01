package com.smartsoft.movietracker;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;

import androidx.fragment.app.FragmentActivity;

import com.smartsoft.movietracker.utils.FragmentNavigation;
import com.smartsoft.movietracker.utils.NetworkChangeReceiver;

public class MainActivity extends FragmentActivity {

    private NetworkChangeReceiver networkChangeReceiver;
    private Handler handler;
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentNavigation.getInstance().initAttributes(this);
        FragmentNavigation.getInstance().showGenreSelectorFragment();
        initNetworkHandler();
        networkHandler();
    }

    private void initNetworkHandler() {

        handler = new Handler();
        runnable = () -> {
            if (getApplicationContext() != null) {
                networkChangeReceiver = new NetworkChangeReceiver(MainActivity.this);
                registerNetworkBroadcastReceiver();
            }
        };
    }

    private void registerNetworkBroadcastReceiver() {
        registerReceiver(networkChangeReceiver,
                new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
    }

    private void unregisterNetworkBroadcastReceiver() {
        try {
            unregisterReceiver(networkChangeReceiver);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    public void networkHandler() {
        handler.removeCallbacks(runnable);
        handler.postDelayed(runnable, 2000);
    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterNetworkBroadcastReceiver();
    }


}





package com.smartsoft.movietracker;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;

import com.smartsoft.movietracker.utils.FragmentNavigation;
import com.smartsoft.movietracker.utils.NetworkChangeReceiver;

public class MainActivity extends FragmentActivity {

    private NetworkChangeReceiver networkChangeReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentNavigation.getInstance().initAttributes(this);
        FragmentNavigation.getInstance().showGenreSelectorFragment();
    }

    @Override
    public void onBackPressed() {
        if (FragmentNavigation.getInstance().getBackStackEntryCount() == 1) {
            finishAffinity();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        networkChangeReceiver = new NetworkChangeReceiver();
        registerNetworkBroadcastReceiver();
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

    @Override
    protected void onPause() {
        super.onPause();
        unregisterNetworkBroadcastReceiver();
    }

}





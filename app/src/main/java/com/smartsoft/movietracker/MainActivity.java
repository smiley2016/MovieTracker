package com.smartsoft.movietracker;

import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;

import androidx.fragment.app.FragmentActivity;

import com.smartsoft.movietracker.utils.ApplicationNetworkReceiver;
import com.smartsoft.movietracker.utils.FragmentNavigation;
import com.smartsoft.movietracker.utils.NetworkChangeReceiver;

public class MainActivity extends FragmentActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentNavigation.getInstance().initAttributes(this);
        FragmentNavigation.getInstance().showGenreSelectorFragment();
    }


}





package com.smartsoft.movietracker.view;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.smartsoft.movietracker.utils.FragmentNavigation;

public abstract class BaseFragment extends Fragment {

    protected View rootView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void onInternetConnected() {
        FragmentNavigation.getInstance().dismissFragment();
    }

    public void onInternetDisconnected() {

        FragmentNavigation.getInstance().removeNoInternetDialogFragmentFromBackStack();

        FragmentNavigation.getInstance().showNoInternetFragment();
    }


}

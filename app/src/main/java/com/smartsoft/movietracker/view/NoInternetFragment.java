package com.smartsoft.movietracker.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.smartsoft.movietracker.R;
import com.smartsoft.movietracker.utils.FragmentNavigation;
import com.smartsoft.movietracker.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NoInternetFragment extends Fragment {

    @BindView(R.id.try_again_button)
    Button tryAgain;

    @BindView(R.id.exit_button)
    Button exit;

    private View rootView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        if(rootView == null){
            rootView = inflater.inflate(R.layout.no_internet, container, false);
        }
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initializeViews();
    }

    private void initializeViews() {
        tryAgain.setOnClickListener(v -> {
            if(Utils.isOnline(rootView.getContext())){
                FragmentNavigation.getInstance().removeFragment(this);
            }
        });

        exit.setOnClickListener(v -> getActivity().finishAffinity());
    }
}

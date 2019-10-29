package com.smartsoft.movietracker.view.internet;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.smartsoft.movietracker.MainActivity;
import com.smartsoft.movietracker.R;
import com.smartsoft.movietracker.utils.FragmentNavigation;
import com.smartsoft.movietracker.utils.Utils;
import com.smartsoft.movietracker.view.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NoInternetDialogFragment extends DialogFragment {

    @BindView(R.id.try_again_button)
    Button tryAgain;

    @BindView(R.id.exit_button)
    Button exit;

    private View view;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.FullScreenDialogStyle);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.no_internet, container, false);
        ButterKnife.bind(this, view);
        initViews();
        return view;
    }

    private void initViews() {
        exit.setOnClickListener(v -> ((MainActivity) view.getContext()).finishAffinity());

        tryAgain.setOnClickListener(v -> {
            if (Utils.isOnline(view.getContext())) {
                ((BaseFragment) FragmentNavigation.getInstance().getCurrentFragment()).onInternetConnected();
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        Dialog dialog = getDialog();
        if (dialog != null && dialog.getWindow() != null) {
            int width = ViewGroup.LayoutParams.MATCH_PARENT;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            dialog.getWindow().setLayout(width, height);
        }
    }
}

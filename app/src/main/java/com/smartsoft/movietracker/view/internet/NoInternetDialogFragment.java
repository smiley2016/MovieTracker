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

/**
 * This fragment will be appeared on the UI
 * when the internet gone.
 */
public class NoInternetDialogFragment extends DialogFragment {

    /**
     * Try again button for the user
     * if he/she wants to try again if there is
     * internet or not
     */
    @BindView(R.id.try_again_button)
    Button tryAgain;

    /**
     * The exit button is used for if there is no internet
     * connection then the user can make a decision wait for
     * the internet comes back or exit from the app because
     * in this case the user can't do anything in the app because
     * the app freezes itself until the internet comes back
     */
    @BindView(R.id.exit_button)
    Button exit;

    /**
     * This {@link View} used for the context
     */
    private View view;

    /**
     * {@link DialogFragment} lifecycle function
     * Here sets the app the fragment style
     *
     * @param savedInstanceState
     */
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_FRAME, R.style.FullScreenDialogStyle);
    }

    /**
     * When the fragment created but has no view calls the {@link DialogFragment#onCreateView(LayoutInflater, ViewGroup, Bundle)}
     * function makes with {@link LayoutInflater} from the XML file a real view
     * @param inflater object that makes view from the XML
     * @param container The holder where will be inflated the view
     * @param savedInstanceState {@link DialogFragment} state
     * @return The inflated view
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.no_internet, container, false);
        ButterKnife.bind(this, view);
        initViews();
        return view;
    }

    /**
     * Initializer function
     */
    private void initViews() {
        exit.setOnClickListener(v -> ((MainActivity) view.getContext()).finishAffinity());

        tryAgain.setOnClickListener(v -> {
            if (Utils.isOnline(view.getContext())) {
                ((BaseFragment) FragmentNavigation.getInstance().getCurrentFragment()).onInternetConnected();
            }
        });

    }

    /**
     * THis function used to make the
     * {@link NoInternetDialogFragment} width and height
     * to {@link ViewGroup.LayoutParams#MATCH_PARENT}
     * because without this the default {@link DialogFragment}
     * is implemented that the if you set it's size fullscreen
     * it has a function that decrease the fullscreen size a little bit
     * for the user see that's a dialog
     */
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

package com.smartsoft.movietracker.utils;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.Switch;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.smartsoft.movietracker.MainActivity;
import com.smartsoft.movietracker.R;
import com.smartsoft.movietracker.view.home.GenreSelectorFragment;
import com.smartsoft.movietracker.view.navigation.MovieNavigationFragment;

import butterknife.BindInt;
import butterknife.BindView;

public class FragmentNavigation extends Fragment {
    private static final String TAG = FragmentNavigation.class.getSimpleName();
    private static FragmentNavigation sInstance;
    private static FragmentManager mFragmentManager;

    private static Context ctx;

    @BindView(R.id.fragment_holder)
    public RelativeLayout mMainActivityFragmentContainer;

    @BindView(R.id.gridView_container)
    public FrameLayout mFragmentHolder;

    private Bundle bundle;

    public static FragmentNavigation getInstance(Context context) {
        ctx = context;
        if (sInstance == null) {
            sInstance = new FragmentNavigation();
            mFragmentManager = ((MainActivity)context).getSupportFragmentManager();
        }

        return sInstance;
    }

    public void clearInstance(){
        sInstance = null;
    }


    public void showHomeFragment(){
        Fragment myCurrentFragment = Fragment.instantiate(ctx, GenreSelectorFragment.class.getName(), null);
        replaceFragment(myCurrentFragment, mFragmentHolder.getId(), false);

    }

    public void showMovieNavigationFragment(){

        Fragment myCurrentFragment = getCurrentFragment();
        if( myCurrentFragment instanceof GenreSelectorFragment){
            ((GenreSelectorFragment)myCurrentFragment).setAdapterBundle();
            bundle = new Bundle();
            if(((GenreSelectorFragment)myCurrentFragment).getAdapterBundle() != null){
                bundle = ((GenreSelectorFragment)myCurrentFragment).getAdapterBundle();
            }
        }

        myCurrentFragment = Fragment.instantiate(ctx, MovieNavigationFragment.class.getName(), bundle);
        replaceFragment(myCurrentFragment, mFragmentHolder.getId(), true);
    }

    private Fragment getCurrentFragment(){
        return mFragmentManager.findFragmentById(R.id.gridView_container);

    }

    private void replaceFragment(Fragment fragment, int container, boolean addToBackStack) {
        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
        // if there is layout_fragment to replace, then replace it:
        mFragmentTransaction.setReorderingAllowed(false);
        mFragmentTransaction.replace(container, fragment, fragment.getTag());
        if(addToBackStack){
            mFragmentTransaction.addToBackStack(fragment.getClass().getSimpleName());
        }
        try {
            mFragmentTransaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }


        mFragmentManager.executePendingTransactions();


    }

    public void startToolbarSettingsDialog() {
        final Dialog dialog = new Dialog(ctx);
        dialog.setContentView(R.layout.toolbar_settings_dialog);

        SharedPreferences sortSp = new SharedPreferences(ctx, ctx.getString(R.string.sortBy));
        SharedPreferences orderSp = new SharedPreferences(ctx, ctx.getString(R.string.orderBy));

        sortSp.setActiveButtonWithSharedPref(dialog);
        orderSp.setOrderBySwitchWithSharedPref(dialog);

        RadioGroup radioGroup = dialog.findViewById(R.id.radio_button_group);
        Switch orderBy = dialog.findViewById(R.id.switch_order_by);

        orderBy.setOnCheckedChangeListener((compoundButton, b) -> {
            if (b) {
                orderSp.writeOnStorage(ctx.getString(R.string.orderBy), ".desc");
            } else {
                orderSp.writeOnStorage(ctx.getString(R.string.orderBy), ".asc");
            }
        });

        radioGroup.setOnCheckedChangeListener((radioGroup1, checked) -> {
            sortSp.updateSharedPrefForSort(checked);
            Log.e(TAG, "startToolbarSettingsDialog: " + checked);
        });

        Button sort = dialog.findViewById(R.id.toolbar_settings_sort_button);
        sort.setOnClickListener(view -> {
            if (Constant.MovieNavigationFragment.sortFromMovieNavFragment) {
                Constant.API.PAGE = 0;
                ((MovieNavigationFragment)getCurrentFragment()).getPresenter().updateMovieNavigationGridView(ctx, bundle.getIntegerArrayList("genreIds"));
                Constant.MovieNavigationFragment.sortFromMovieNavFragment = false;
            }
            dialog.dismiss();

        });
        dialog.show();
    }




}

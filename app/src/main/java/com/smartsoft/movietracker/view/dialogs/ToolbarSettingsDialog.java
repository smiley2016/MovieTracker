package com.smartsoft.movietracker.view.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import com.smartsoft.movietracker.R;
import com.smartsoft.movietracker.interfaces.ToolbarListener;
import com.smartsoft.movietracker.utils.Constant;
import com.smartsoft.movietracker.utils.SharedPreferences;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ToolbarSettingsDialog {
    private static final String TAG = ToolbarSettingsDialog.class.getName();
    @BindView(R.id.radio_button_group)
    RadioGroup radioGroup;
    @BindView(R.id.toolbar_settings_sort_button)
    Button sort;
    @BindView(R.id.switch_order_by)
    Switch orderBy;
    private ToolbarListener listener;
    private boolean isChanged = false;
    private Context context;

    public ToolbarSettingsDialog(ToolbarListener listener, Context context) {
        this.listener = listener;
        this.context = context;
        startToolbarSettingsDialog(context);
    }

    public void startToolbarSettingsDialog(Context ctx) {
        final Dialog dialog = new Dialog(ctx);
        dialog.setContentView(R.layout.toolbar_settings_dialog);

        initViews(dialog);

        SharedPreferences sortSp = new SharedPreferences(ctx, ctx.getString(R.string.sortBy));
        SharedPreferences orderSp = new SharedPreferences(ctx, ctx.getString(R.string.orderBy));

        setActiveButtonWithSharedPref(sortSp, ctx);
        setOrderBySwitchWithSharedPref(orderSp);

        setSort(radioGroup, sortSp);

        sort.setOnClickListener(view -> {
            setOrder(orderBy, orderSp);
            if (isChanged) {
                listener.onSortButtonClicked(dialog);
            }
        });

        dialog.show();
    }

    private void initViews(Dialog dialog) {
        ButterKnife.bind(this, dialog);
    }

    private void setOrder(Switch orderBy, SharedPreferences orderSp) {
        if (orderBy.isChecked()) {
            orderSp.writeOnStorage(context.getString(R.string.desc));
        } else {
            orderSp.writeOnStorage(context.getString(R.string.asc));
        }
    }

    private void setSort(RadioGroup radioGroup, SharedPreferences sortSp) {
        radioGroup.setOnCheckedChangeListener((radioGroup1, checked) ->
                updateSharedPrefForSort(checked, sortSp)

        );
    }

    private void updateSharedPrefForSort(int checked, SharedPreferences sortSp) {
        switch (checked) {
            case R.id.radio_button_title: {
                sortSp.writeOnStorage(context.getString(R.string.originalTitle));
                Log.e(TAG, context.getString(R.string.startToolbarSettingsDialog) + checked);
                isChanged = true;
                break;
            }
            case R.id.radio_button_rating: {
                sortSp.writeOnStorage(context.getString(R.string.popularity));
                Log.e(TAG, context.getString(R.string.startToolbarSettingsDialog) + checked);
                isChanged = true;
                break;
            }
            case R.id.radio_button_release_date: {
                sortSp.writeOnStorage(context.getString(R.string.releaseDate));
                Log.e(TAG, context.getString(R.string.startToolbarSettingsDialog) + checked);
                isChanged = true;
                break;
            }
            case R.id.radio_button_vote_average: {
                sortSp.writeOnStorage(context.getString(R.string.voteAverage));
                Log.e(TAG, context.getString(R.string.startToolbarSettingsDialog) + checked);
                isChanged = true;
                break;
            }
        }
    }

    private void setOrderBySwitchWithSharedPref(SharedPreferences orderSp) {
        String value = orderSp.ReadFromStorage();
        if (value.equals(context.getString(R.string.asc))) {
            orderBy.setChecked(false);
        } else {
            orderBy.setChecked(true);
        }
    }

    private void setActiveButtonWithSharedPref(SharedPreferences sp, Context context) {

        String sort = sp.ReadFromStorage();

        switch (sort) {
            case Constant.Sort.popularity: {
                radioGroup.check(R.id.radio_button_rating);
                break;
            }
            case Constant.Sort.voteAverage: {
                radioGroup.check(R.id.radio_button_vote_average);
                break;
            }
            case Constant.Sort.releaseDate: {
                radioGroup.check(R.id.radio_button_release_date);
                break;
            }
            case Constant.Sort.originalTitle: {
                radioGroup.check(R.id.radio_button_title);
                break;
            }
            default: {

                Toast.makeText(context, R.string.sortError, Toast.LENGTH_SHORT).show();
                Log.e(TAG, context.getString(R.string.setActiveButtonWithSharedPref) + sort);
                break;
            }
        }


    }


}

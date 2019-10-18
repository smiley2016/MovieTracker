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
    private String sortBy;

    public ToolbarSettingsDialog(ToolbarListener listener, Context context) {
        this.listener = listener;
        this.context = context;
        startToolbarSettingsDialog(context);
    }

    private void startToolbarSettingsDialog(Context ctx) {
        final Dialog dialog = new Dialog(ctx);
        dialog.setContentView(R.layout.toolbar_settings_dialog);

        initViews(dialog);

        SharedPreferences sortSp = new SharedPreferences(ctx, ctx.getString(R.string.sortBy));
        SharedPreferences orderSp = new SharedPreferences(ctx, ctx.getString(R.string.orderBy));

        setActiveButtonWithSharedPref(sortSp, ctx);
        setOrderBySwitchWithSharedPref(orderSp);

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> setSort(checkedId));

        sort.setOnClickListener(view -> {
            setOrder(orderBy, orderSp);
            if (isChanged) {
                sortSp.writeOnStorage(sortBy);
                listener.onSortButtonClicked(dialog);
            }else{
                dialog.dismiss();
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

    private void setSort(int checked) {
        switch (checked) {
            case R.id.radio_button_title: {
                sortBy = context.getString(R.string.originalTitle);
                isChanged = true;
                break;
            }
            case R.id.radio_button_rating: {
                sortBy = context.getString(R.string.popularity);
                isChanged = true;
                break;
            }
            case R.id.radio_button_release_date: {
                sortBy = context.getString(R.string.releaseDate);
                isChanged = true;
                break;
            }
            case R.id.radio_button_vote_average: {
                sortBy = context.getString(R.string.voteAverage);
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
                Log.e(TAG, "setActiveButtonWithSharedPref:" + sort);
                break;
            }
        }
    }
}

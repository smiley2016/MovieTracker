package com.smartsoft.movietracker.view.home;

import android.content.Context;
import android.media.Image;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.smartsoft.movietracker.R;
import com.smartsoft.movietracker.interfaces.ToolbarListener;
import com.smartsoft.movietracker.utils.FragmentNavigation;
import com.smartsoft.movietracker.utils.ToolbarDialog;

public class ToolbarView extends RelativeLayout {
    private ImageView settings;
    private ImageView search;
    private ImageView logo;
    private ToolbarListener listener;
    private LayoutInflater mInflater;

    public ToolbarView(Context context) {
        this(context, null);
    }

    public ToolbarView(Context context, AttributeSet attrs) {

        this(context, attrs, 0);
    }

    public ToolbarView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public ToolbarView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mInflater = LayoutInflater.from(context);
        initViews();
    }

    private void initViews() {
        View v = mInflater.inflate(R.layout.toolbar_view_layout, this, true);

        search = v.findViewById(R.id.toolbar_search);
        settings = v.findViewById(R.id.toolbar_settings);
    }

    private void setOnClickListeners() {
        search.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentNavigation.getInstance().showMovieNavigationFragment();
            }
        });

        settings.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                ToolbarDialog dialog = new ToolbarDialog(listener);
                dialog.startToolbarSettingsDialog(getContext());
            }
        });
    }

    public void setListener(ToolbarListener listener){
        this.listener = listener;
        setOnClickListeners();
    }

    public void setVisibleSearchIcon(int visibility) {
        search.setVisibility(visibility);
    }

}

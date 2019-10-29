package com.smartsoft.movietracker.view.toolbar;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.smartsoft.movietracker.R;
import com.smartsoft.movietracker.interfaces.ToolbarListener;
import com.smartsoft.movietracker.view.toolbar.ToolbarSettingsDialog;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ToolbarView extends RelativeLayout {
    @BindView(R.id.toolbar_settings)
    ImageView settings;
    @BindView(R.id.toolbar_search)
    ImageView search;
    private ToolbarListener listener;
    private Context context;

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
        initViews(context);
    }

    private void initViews(Context context) {
        this.context = context;

        View v = LayoutInflater.from(context).inflate(R.layout.toolbar_view_layout, this, true);

        ButterKnife.bind(this, v);
        setOnClickListeners();
    }

    private void setOnClickListeners() {

        search.setOnClickListener(view -> listener.onSearchButtonClicked());

        settings.setOnClickListener(view -> {
            new ToolbarSettingsDialog(listener, context);
        });
    }

    public void setListener(ToolbarListener listener) {
        this.listener = listener;
    }

    public void setVisibleSearchIcon(int visibility) {
        search.setVisibility(visibility);
    }
}
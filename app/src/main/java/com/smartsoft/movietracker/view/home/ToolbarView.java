package com.smartsoft.movietracker.view.home;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.smartsoft.movietracker.R;

public class ToolbarView extends RelativeLayout {
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
        inflate(context, R.layout.fragment_toolbar, this);
    }



}

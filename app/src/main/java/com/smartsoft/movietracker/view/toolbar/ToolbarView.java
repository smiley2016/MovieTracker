package com.smartsoft.movietracker.view.toolbar;
import android.content.Context;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.smartsoft.movietracker.R;
import com.smartsoft.movietracker.interfaces.ToolbarListener;
import com.smartsoft.movietracker.utils.StringUtils;
import com.smartsoft.movietracker.utils.Utils;
import butterknife.BindView;
import butterknife.ButterKnife;


public class ToolbarView extends RelativeLayout {
    @BindView(R.id.toolbar_settings)
    ImageView settings;
    @BindView(R.id.toolbar_search)
    ImageView search;
    @BindView(R.id.search_edit_text)
    public EditText searchEditText;

    private View view;

    private ToolbarListener listener;
    private Context context;
    private Handler handler;
    private Runnable runnable;

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

        view = LayoutInflater.from(context).inflate(R.layout.toolbar_view_layout, this, true);

        ButterKnife.bind(this, view);
        handler = new Handler();
        initRunnable();
        setOnClickListeners();
    }

    private void initRunnable() {
        runnable = () -> {
            try {
                listener.onSearch();
                searchEditText.setText(StringUtils.EMPTY_STRING);
                Utils.hideKeyboard(view);
            }catch (Exception ignored){

            }
        };
    }

    private void setOnClickListeners() {

        search.setOnClickListener(view -> listener.onSearchButtonClicked());

        settings.setOnClickListener(view -> {
            new ToolbarSettingsDialog(listener, context);
        });

        searchEditText.setOnKeyListener((v, keyCode, event) -> {

            if(event.getKeyCode() == KeyEvent.KEYCODE_ENTER){
                if(searchEditText.getText().toString().trim().length() >= 2){
                    listener.onSearch();
                    searchEditText.setText(StringUtils.EMPTY_STRING);
                    Utils.hideKeyboard(view);
                }
            }
            return false;
        });

        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(searchEditText.getText().toString().trim().length() < 3){
                    handler.removeCallbacks(runnable);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                if(searchEditText.getText().toString().trim().length() >= 3){
                    handler.removeCallbacks(runnable);
                    delayedSearch();
                }
            }
        });
    }
    private void delayedSearch(){
        handler.postDelayed(runnable, 2500);
    }



    public void setListener(ToolbarListener listener) {
        this.listener = listener;
    }

    public void setVisibleSearchIcon(int visibility) {
        search.setVisibility(visibility);
    }

    public String getSearchText(){
        return searchEditText.getText().toString().trim();
    }
}

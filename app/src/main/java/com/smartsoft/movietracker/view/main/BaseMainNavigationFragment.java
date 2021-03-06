package com.smartsoft.movietracker.view.main;

import android.widget.TextView;

import com.smartsoft.movietracker.R;
import com.smartsoft.movietracker.interfaces.ToolbarListener;
import com.smartsoft.movietracker.view.BaseFragment;
import com.smartsoft.movietracker.view.toolbar.ToolbarView;
import butterknife.BindView;
import butterknife.ButterKnife;

public abstract class BaseMainNavigationFragment extends BaseFragment {

    @BindView(R.id.base_toolbar)
    ToolbarView toolbarView;

    @BindView(R.id.choose_textView)
    public TextView text;

    protected void initViews(ToolbarListener listener, int visibility, String genreText) {
        ButterKnife.bind(this, rootView);
        setToolbarViewListener(listener);
        setToolbarSearchButtonVisibility(visibility);
        setTitle(genreText);

    }

    private void setTitle(String genreTitle) {
        if (text != null) {
            text.setText(genreTitle);
        }
    }

    private void setToolbarSearchButtonVisibility(int visibility) {
        if (toolbarView != null) {
            toolbarView.setVisibleSearchIcon(visibility);
        }

    }

    private void setToolbarViewListener(ToolbarListener listener) throws NullPointerException {
        if (toolbarView != null) {
            toolbarView.setListener(listener);
        }
    }

    protected String getSearchText(){
       return toolbarView.getSearchText();
    }
}

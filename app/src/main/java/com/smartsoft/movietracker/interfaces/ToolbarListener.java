package com.smartsoft.movietracker.interfaces;

import android.app.Dialog;

/**
 * {@link ToolbarListener} interface is used for
 * the toolbar buttons. This listener is used when a
 * toolbar element state is changed. More practically
 * when a button clicked these functions trying to do something.
 * this Listener implements two fragments
 * {@link com.smartsoft.movietracker.view.main.genre.GenreSelectorFragment}
 * {@link com.smartsoft.movietracker.view.main.navigation.MovieNavigationFragment}
 * and in this two fragments the listeners are defined different
 */

public interface ToolbarListener {


    /**
     * When the {@link com.smartsoft.movietracker.view.toolbar.ToolbarSettingsDialog}
     * is appeared and the user choose which sort he/she wants and
     * in what order there is a sort Button and when it has clicked
     * then program call this Listener function, but this is implemented in two fragment
     * by this fact the android decide where are the user between two fragments
     * {@link com.smartsoft.movietracker.view.main.genre.GenreSelectorFragment}
     * {@link com.smartsoft.movietracker.view.main.navigation.MovieNavigationFragment}
     * and call the respected Fragment implementation
     * @param dialog is used for to dismiss the dialog when program jump to the implementation
     */

    void onSortButtonClicked(Dialog dialog);


    /**
     * In {@link com.smartsoft.movietracker.view.main.genre.GenreSelectorFragment}
     * the user has some genres. In these genres he/she can chooses. After the choose
     * the user click the search button and this calls the
     * @see #onSearchButtonClicked() function and it's respected implementation
     * The function has a notification as a {@link android.widget.Toast}
     * if the user doesn't choosed at least one genre from the list
     */
    void onSearchButtonClicked();

    /**
     * @see #onSearch() function is a feature callback
     * in the
     * {@link com.smartsoft.movietracker.view.main.genre.GenreSelectorFragment}
     * {@link com.smartsoft.movietracker.view.main.navigation.MovieNavigationFragment}
     * becasue this function is used for searching in all the movies in which the title
     * presented the text we writed in the {@link android.widget.EditText}
     */

    void onSearch();

}

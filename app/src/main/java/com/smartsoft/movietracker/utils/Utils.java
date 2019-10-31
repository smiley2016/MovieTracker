package com.smartsoft.movietracker.utils;

import android.app.Dialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.smartsoft.movietracker.view.main.BaseMainNavigationFragment;

import java.util.List;

/**
 * This class is the container of those functions
 * what the app uses in the background
 */

public final class Utils {

    /**
     * This function makes a {@link String} from the
     * {@link List} for the {@link BaseMainNavigationFragment#text} in the
     * {@link com.smartsoft.movietracker.view.main.navigation.MovieNavigationFragment}
     * @param genreIds The genre IDs with what we can get the name of
     *                 each element.
     * @return A string from the genre names with separation of {@link StringUtils#COMMA_DELIMITER}
     */
    public static String genreListToString(List<Integer> genreIds) {
        if (genreIds == null || genreIds.isEmpty()) {
            return StringUtils.EMPTY_STRING;
        }
        StringBuilder text = new StringBuilder();
        int i;
        for (i = 0; i < genreIds.size() - 1; i++) {
            text.append(genreIds.get(i)).append(StringUtils.COMMA_DELIMITER);
        }
        text.append(genreIds.get(i));
        return text.toString();
    }

    /**
     * Basically checks if there is internetConnection or not
     * @param context The context where running.
     * @return True if is internet connection,
     *          false if isn't.
     */
    public static boolean isOnline(Context context) {
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            assert cm != null;
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            return (netInfo != null && netInfo.isConnected());
        } catch (NullPointerException e) {
            return false;
        }
    }

    /**
     * This function set the focus on the {@link ScrollView} if the text
     * is more longer than the {@link Dialog}'s height. Else set the focus on the
     * {@link Button} namely here closeButton.
     * @param scroll Vertical Scrollbar
     * @param reviewComment {@link TextView} where wrote the {@link com.smartsoft.movietracker.model.review.Review#content}
     * @param closeButton {@link Button} is used for closes the {@link com.smartsoft.movietracker.view.dialogs.ReviewDialog}
     */
    public static void setFocusByScrollViewState(ScrollView scroll, TextView reviewComment, Button closeButton) {
        ViewTreeObserver viewTreeObserver = scroll.getViewTreeObserver();
        viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                scroll.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int childHeight = reviewComment.getHeight();
                boolean isScrollable = scroll.getHeight() < childHeight + scroll.getPaddingTop() + scroll.getPaddingBottom();
                if (isScrollable) {
                    scroll.requestFocus();
                } else {
                    closeButton.setNextFocusDownId(closeButton.getId());
                }
            }
        });
    }

    /**
     * This function calls when the user search with
     * {@link com.smartsoft.movietracker.view.toolbar.ToolbarView#searchEditText}
     * and the text passed to the API, but the keyboard sometimes doesn't hide.
     * By this fact the app with this function forces the keyboard to hide.
     * @param view The view where the keyboard is appeared
     */
    public static void hideKeyboard(View view){
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0, null);
        }
    }

}

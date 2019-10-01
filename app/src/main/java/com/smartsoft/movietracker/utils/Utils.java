package com.smartsoft.movietracker.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import java.util.List;

public final class Utils {

    public static void showToast(Context ctx, String message) {
        Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show();
    }

    public static float pxFromDp(final Context context, final float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }

    public static String genreListToCsvIdString(List<Integer> genreIds) {
        if (genreIds == null || genreIds.isEmpty()) {
            return "";
        }
        StringBuilder text = new StringBuilder();
        int i;
        for (i = 0; i < genreIds.size() - 1; i++) {
            text.append(genreIds.get(i)).append(",");
        }
        text.append(genreIds.get(i));
        return text.toString();
    }

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

}

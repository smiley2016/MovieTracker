package com.smartsoft.movietracker.utils;

import android.content.Context;
import android.widget.Toast;

public final class Util {

    private static final String TAG = Util.class.getSimpleName();

    public static void showToast(Context ctx, String message){
        Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show();
    }

}

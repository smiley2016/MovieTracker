package com.smartsoft.movietracker.utils;

import android.content.Context;
import android.widget.Toast;

import com.smartsoft.movietracker.model.genre.Genre;

import java.util.List;

public final class Util {

    public static void showToast(Context ctx, String message){
        Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show();
    }

    public static float pxFromDp(final Context context, final float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }

    public static String genreListToCsvIdString(List<Integer> genreIds){
        if( genreIds == null || genreIds.isEmpty() ){
            return "";
        }
        StringBuilder text = new StringBuilder();
        int i;
        for(i=0; i<genreIds.size()-1; i++){
            text.append(genreIds.get(i)).append(",");
        }
        text.append(genreIds.get(i));
        return text.toString();
    }

}

package com.smartsoft.movietracker.utils;

import android.content.Context;

public class SharedPreferences {

    private android.content.SharedPreferences sp;
    private String key;


    public SharedPreferences(Context context, String key) {
        this.key = key;
        sp = context.getSharedPreferences(this.key, Context.MODE_PRIVATE);

    }

    void writeOnStorage(String value) {
        android.content.SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value).apply();
    }

    public String ReadFromStorage() {
        return sp.getString(key, "popularity");
    }


}

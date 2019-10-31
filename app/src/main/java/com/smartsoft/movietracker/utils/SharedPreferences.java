package com.smartsoft.movietracker.utils;

import android.content.Context;

/**
 * @see SharedPreferences class is a class for
 * handling the {@link android.content.SharedPreferences.Editor#edit()}
 * and read from the {@link android.content.SharedPreferences}
 * with {@link android.content.SharedPreferences#getString(String, String)}
 */
public class SharedPreferences {

    /**
     * This variable contains the reference for the
     * {@link android.content.SharedPreferences}
     */
    private android.content.SharedPreferences sp;

    /**
     * This variable contains the key(Unique identifier) for the searched
     * {@link android.content.SharedPreferences}
     * This can be a number, or a string.
     */
    private String key;

    /**
     * Public class constructor.
     * Here sets the key and
     * the {@link SharedPreferences#sp} variable
     * @param context The context in what the {@link SharedPreferences} is running.
     * @param key Searched key or identifier. With this decides the
     *            app which {@link SharedPreferences} want to read
     */
    public SharedPreferences(Context context, String key) {
        this.key = key;
        sp = context.getSharedPreferences(this.key, Context.MODE_PRIVATE);
    }

    /**
     * With this function we can write on the storage
     * @param value The value what want to save on hte storage
     */
    public void writeOnStorage(String value) {
        android.content.SharedPreferences.Editor editor = sp.edit();
        editor.putString(key, value).apply();
    }

    /**
     * This function allow to us to read from the storage
     * @return value from the {@link android.content.SharedPreferences}
     * based on the {@link SharedPreferences#key}
     */
    public String ReadFromStorage() {
        return sp.getString(key, "popularity");
    }


}

package com.smartsoft.movietracker.utils;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import com.smartsoft.movietracker.R;

public class SharedPreferences {
    private static final String TAG = "SharedPreferences";
    
    private android.content.SharedPreferences sp;
    private android.content.SharedPreferences.Editor editor;
    private Context context;
    private String key;


    public SharedPreferences(Context context, String key) {
        this.context = context;
        this.key = key;
        sp = this.context.getSharedPreferences(this.key, Context.MODE_PRIVATE);
        editor = sp.edit();
    }

    void writeOnStorage(String value){
        editor.putString(key, value).commit();
    }

    public String ReadFromStorage(){
        return sp.getString(key, "popularity");
    }


}

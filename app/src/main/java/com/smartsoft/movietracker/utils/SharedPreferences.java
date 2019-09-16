package com.smartsoft.movietracker.utils;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Switch;
import android.widget.Toast;

import com.smartsoft.movietracker.R;

import static com.makeramen.roundedimageview.RoundedImageView.TAG;

public class SharedPreferences {
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

    void writeOnStorage(String key, String value){
        editor.putString(key, value).commit();
    }

    public String ReadFromStorage(){
        return sp.getString(key, "popularity");
    }

    void updateSharedPrefForSort(int checked){
        switch(checked){
            case R.id.radio_button_title:{
                writeOnStorage(context.getString(R.string.sortBy), "original_title");
                break;
            }
            case R.id.radio_button_rating:{
                writeOnStorage(context.getString(R.string.sortBy), "popularity");
                break;
            }
            case R.id.radio_button_release_date:{
                writeOnStorage(context.getString(R.string.sortBy), "release_date");
                break;
            }
            case R.id.radio_button_vote_average:{
                writeOnStorage(context.getString(R.string.sortBy), "vote_average");
                break;
            }
        }
    }

     void setActiveButtonWithSharedPref(Dialog dialog){

        String sort = ReadFromStorage();
        RadioGroup group = dialog.findViewById(R.id.radio_button_group);

        switch (sort){
            case "popularity":{
                group.check(R.id.radio_button_rating);
                break;
            }
            case "vote_average":{
                group.check(R.id.radio_button_vote_average);
                break;
            }
            case "release_date":{
                group.check(R.id.radio_button_release_date);
                break;
            }
            case "original_title":{
                group.check(R.id.radio_button_title);
                break;
            }
            default: {
                Toast.makeText(context, "Sort hiba", Toast.LENGTH_SHORT).show();
                Log.e(TAG, "setActiveButtonWithSharedPref: "+sort );
                break;
            }
        }


    }

    void setOrderBySwitchWithSharedPref(Dialog dialog){
        String value = sp.getString(key, ".asc");
        Switch orderBy = dialog.findViewById(R.id.switch_order_by);
        if(value.equals(".asc")){
            orderBy.setChecked(false);
        }else{
            orderBy.setChecked(true);
        }
    }
}

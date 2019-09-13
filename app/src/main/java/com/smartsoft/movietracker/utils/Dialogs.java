package com.smartsoft.movietracker.utils;

import android.app.Dialog;
import android.content.Context;
import android.media.Image;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.smartsoft.movietracker.MainActivity;
import com.smartsoft.movietracker.R;
import com.smartsoft.movietracker.model.cast.Cast;
import com.smartsoft.movietracker.view.navigation.MovieNavigationFragment;

public class Dialogs {

    public static void startReviewDialog(Context context, String comment, String Author){
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.review_description_dialog);
        Button button = dialog.findViewById(R.id.dialog_close_button);
        TextView review_comment = dialog.findViewById(R.id.dialog_review_comment);
        review_comment.setText(comment);
        TextView reviewer = dialog.findViewById(R.id.dialog_reviewer);
        reviewer.setText(String.format("%s %s", reviewer.getText(), Author));
        button.setFocusable(true);
        review_comment.setMovementMethod(new ScrollingMovementMethod() {
        });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public static void startToolbarSettingsDialog(Context context, Fragment fragment){
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.toolbar_settings_dialog);
        RadioButton title = dialog.findViewById(R.id.radio_button_title);
        RadioButton voteAverage = dialog.findViewById(R.id.radio_button_vote_average);
        RadioButton release_date = dialog.findViewById(R.id.radio_button_release_date);
        RadioButton rating = dialog.findViewById(R.id.radio_button_rating);
        Switch orderBy = dialog.findViewById(R.id.switch_sort);
        Button sort = dialog.findViewById(R.id.toolbar_settings_sort_button);
        sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(Constant.MovieNavigationFragment.isSorted){
                    if(title.isSelected() && orderBy.isChecked()){
                        Constant.API.sortList.add("original_title.desc");
                    }else{
                        Constant.API.sortList.add("original_title.asc");
                    }
                    if(voteAverage.isSelected() && orderBy.isChecked()){
                        Constant.API.sortList.add("popularity.desc");
                    }else{
                        Constant.API.sortList.add("popularity.asc");
                    }
                    if(release_date.isSelected() && orderBy.isChecked()){
                        Constant.API.sortList.add("release_date.desc");
                    }else{
                        Constant.API.sortList.add("release_date.asc");
                    }
                    if (rating.isSelected() && orderBy.isChecked()){
                        Constant.API.sortList.add("vote_average.desc");
                    }else{
                        Constant.API.sortList.add("vote_average.desc");
                    }
                    ((MovieNavigationFragment)fragment).getPresenter().updateMovieNavigationGridView();

                }else {
                    if(title.isSelected() && orderBy.isChecked()){
                        Constant.API.sortList.add("original_title.desc");
                    }else{
                        Constant.API.sortList.add("original_title.asc");
                    }
                    if(voteAverage.isSelected() && orderBy.isChecked()){
                        Constant.API.sortList.add("popularity.desc");
                    }else{
                        Constant.API.sortList.add("popularity.asc");
                    }
                    if(release_date.isSelected() && orderBy.isChecked()){
                        Constant.API.sortList.add("release_date.desc");
                    }else{
                        Constant.API.sortList.add("release_date.asc");
                    }
                    if (rating.isSelected() && orderBy.isChecked()){
                        Constant.API.sortList.add("vote_average.desc");
                    }else{
                        Constant.API.sortList.add("vote_average.desc");
                    }
                }
                dialog.dismiss();

            }
        });
        dialog.show();

    }



    public static void startCastDialog(Context context, Cast cast){
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.cast_dialog);
        ImageView poster = dialog.findViewById(R.id.cast_dialog_image);
        TextView name = dialog.findViewById(R.id.cast_dialog_name);
        ImageView close = dialog.findViewById(R.id.cast_dialog_close);

        name.setText(cast.getName());

        Glide.with(context).load(Constant.API.IMAGE_ORIGINAL_BASE_URL+cast.getProfilePath()).into(poster);

        close.setOnClickListener(view -> dialog.dismiss());

        dialog.show();

    }

}

package com.smartsoft.movietracker.utils;

import android.app.Dialog;
import android.content.Context;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.smartsoft.movietracker.R;
import com.smartsoft.movietracker.model.cast.Cast;

public class Dialogs {

    public static void startReviewDialog(Context context, String comment, String Author) {
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




    public static void startCastDialog(Context context, Cast cast) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.cast_dialog);
        ImageView poster = dialog.findViewById(R.id.cast_dialog_image);
        TextView name = dialog.findViewById(R.id.cast_dialog_name);
        ImageView close = dialog.findViewById(R.id.cast_dialog_close);

        name.setText(cast.getName());

        Glide.with(context).load(Constant.API.IMAGE_ORIGINAL_BASE_URL + cast.getProfilePath()).into(poster);

        close.setOnClickListener(view -> dialog.dismiss());

        dialog.show();

    }

}

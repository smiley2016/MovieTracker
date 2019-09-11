package com.smartsoft.movietracker.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.text.method.MovementMethod;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.smartsoft.movietracker.R;

import org.w3c.dom.Text;

import butterknife.BindView;

public class Dialogs {

    public static void startDialog(Context context, String comment, String Author){
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

}

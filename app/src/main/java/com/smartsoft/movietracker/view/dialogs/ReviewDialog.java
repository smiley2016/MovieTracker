package com.smartsoft.movietracker.view.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.text.method.ScrollingMovementMethod;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.smartsoft.movietracker.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReviewDialog {

    private String comment;
    private String author;
    private Context context;

    @BindView(R.id.comment_scroll_view)
    ScrollView scroll;

    @BindView(R.id.dialog_close_button)
    Button closeButton;

    @BindView(R.id.dialog_review_comment)
    TextView reviewComment;

    @BindView(R.id.reviewer)
    TextView reviewer;


    public ReviewDialog(String comment, String author, Context context) {
        this.comment = comment;
        this.author = author;
        this.context = context;
    }


    public void startReviewDialog() {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.review_description_dialog);

        initViews(dialog);

        reviewComment.setText(comment);
        reviewer.setText(String.format("%s %s", reviewer.getText(), author));

        if (reviewComment.getMaxLines() > 25) {
            scroll.setFocusable(true);
            scroll.requestFocus();
        } else {
            scroll.setFocusable(false);
            closeButton.setFocusable(true);
            closeButton.requestFocus();
        }
        reviewComment.setMovementMethod(new ScrollingMovementMethod() {
        });
        closeButton.setOnClickListener(view -> dialog.dismiss());
        dialog.show();
    }

    private void initViews(Dialog dialog) {
        ButterKnife.bind(this, dialog);
    }

}

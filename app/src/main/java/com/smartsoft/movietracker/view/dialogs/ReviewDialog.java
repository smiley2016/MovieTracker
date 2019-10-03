package com.smartsoft.movietracker.view.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.text.Layout;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.smartsoft.movietracker.R;
import com.smartsoft.movietracker.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReviewDialog {

    private static final String TAG = ReviewDialog.class.getName();
    private String comment;
    private String author;
    private Context context;

    @BindView(R.id.comment_scroll_view)
    ScrollView scroll;

    @BindView(R.id.dialog_close_button)
    Button closeButton;

    @BindView(R.id.dialog_review_comment)
    TextView reviewComment;

    @BindView(R.id.dialog_reviewer)
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

        closeButton.setOnClickListener(view -> dialog.dismiss());

        reviewComment.setText(comment);

        reviewer.setText(String.format("%s %s", reviewer.getText(), author));

        Utils.isScrollable(scroll, reviewComment, closeButton);

        reviewComment.setMovementMethod(new ScrollingMovementMethod() {
        });

        dialog.show();
    }

    private void initViews(Dialog dialog) {
        ButterKnife.bind(this, dialog);
    }

}

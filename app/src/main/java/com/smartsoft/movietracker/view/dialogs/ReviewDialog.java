package com.smartsoft.movietracker.view.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.text.method.ScrollingMovementMethod;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import com.smartsoft.movietracker.R;
import com.smartsoft.movietracker.model.review.Review;
import com.smartsoft.movietracker.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * This class holds the {@link com.smartsoft.movietracker.model.review.Review} elements
 * and it's views on the {@link com.smartsoft.movietracker.view.detail.DetailPageFragment}
 */
public class ReviewDialog {

    /**
     * This view is used for the text what have to be scrollable
     */
    @BindView(R.id.comment_scroll_view)
    ScrollView scroll;

    /**
     * {@link ReviewDialog} button which is used for dismiss the dialog
     */
    @BindView(R.id.dialog_close_button)
    Button closeButton;

    /**
     * This {@link TextView} will be the {@link #comment}
     */
    @BindView(R.id.dialog_review_comment)
    TextView reviewComment;

    /**
     * This {@link TextView} will be the {@link #author}
     */
    @BindView(R.id.dialog_reviewer)
    TextView reviewer;

    /**
     * Review
     */
    private String comment;
    /**
     * Who wrote the {@link #comment}
     */
    private String author;

    /**
     * Where running the app
     */
    private Context context;


    /**
     * CLass constructor
     * Here sets the app the {@link #comment},
     * {@link #author} and the {@link #context}.
     * At the final the constructor calls the {@link #startReviewDialog()}
     * function to will be appeared on the UI
     *
     * @param comment {@link Review#getContent()}
     * @param author  Who wrote the {@link }
     * @param context Where running the app
     */
    public ReviewDialog(String comment, String author, Context context) {
        this.comment = comment;
        this.author = author;
        this.context = context;
        startReviewDialog();
    }

    /**
     * Here makes the app the {@link ReviewDialog}
     * from the {@link #context} and sets it's
     * content view from an XML file
     */
    private void startReviewDialog() {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.review_dialog);

        initViews(dialog);
    }

    /**
     * Initializer function what sets views of the dialog to it's own
     * properties
     * @param dialog {@link ReviewDialog} what will be appeared on the UI
     */
    private void initViews(Dialog dialog) {
        ButterKnife.bind(this, dialog);

        closeButton.setOnClickListener(view -> dialog.dismiss());

        reviewComment.setText(comment);

        reviewer.setText(String.format("%s %s", reviewer.getText(), author));

        Utils.setFocusByScrollViewState(scroll, reviewComment, closeButton);

        reviewComment.setMovementMethod(new ScrollingMovementMethod() {
        });

        dialog.show();
    }

}

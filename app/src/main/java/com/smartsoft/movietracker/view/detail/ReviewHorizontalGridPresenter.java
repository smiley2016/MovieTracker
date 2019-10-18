package com.smartsoft.movietracker.view.detail;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.leanback.widget.Presenter;

import com.smartsoft.movietracker.R;
import com.smartsoft.movietracker.model.review.Review;
import com.smartsoft.movietracker.view.dialogs.ReviewDialog;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReviewHorizontalGridPresenter extends Presenter {

    private Context mContext;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        mContext = parent.getContext();
        return new PresenterViewHolder(View.inflate(mContext, R.layout.review_element, null));
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Object item) {
        PresenterViewHolder holder = (PresenterViewHolder) viewHolder;
        Review review = ((Review) item);
        holder.bind(mContext, review);
    }

    @Override
    public void onUnbindViewHolder(ViewHolder viewHolder) {

    }

    class PresenterViewHolder extends ViewHolder {
        @BindView(R.id.review_comment)
        TextView reviewComment;
        @BindView(R.id.reviewer)
        TextView reviewer;
        @BindView(R.id.review_element_layout)
        RelativeLayout layout;


        public PresenterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        void bind(Context ctx, Review review) {
            reviewer.setText(review.getAuthor());
            reviewComment.setText(review.getContent());
            layout.setOnClickListener(view ->
                    new ReviewDialog(review.getContent(), review.getAuthor(), ctx));
        }
    }
}

package com.smartsoft.movietracker.view.detail;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.leanback.widget.ArrayObjectAdapter;
import androidx.leanback.widget.HorizontalGridView;
import androidx.leanback.widget.ItemBridgeAdapter;
import androidx.leanback.widget.Presenter;
import androidx.leanback.widget.SinglePresenterSelector;

import com.smartsoft.movietracker.R;
import com.smartsoft.movietracker.model.review.Review;
import com.smartsoft.movietracker.model.review.ReviewList;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReviewVerticalGridPresenter extends Presenter {
    private static final String TAG = ReviewVerticalGridPresenter.class.getName();
    private Context mContext;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        mContext = parent.getContext();
        return new PresenterViewHolder(View.inflate(mContext, R.layout.review_section_layout, null));
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Object item) {
        PresenterViewHolder holder = (PresenterViewHolder) viewHolder;
        ArrayList<Review> reviews = ((ReviewList) item).getReview();
        holder.bind(reviews);
    }

    @Override
    public void onUnbindViewHolder(ViewHolder viewHolder) {

    }

    class PresenterViewHolder extends ViewHolder {

        @BindView(R.id.review_textView)
        TextView reviewTextView;

        @BindView(R.id.review_GridView)
        HorizontalGridView hGridView;

        @BindView(R.id.review_section_layout)
        RelativeLayout layout;


        ReviewHorizontalGridPresenter reviewAdapter;

        public PresenterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }

        public void bind(ArrayList<Review> reviews) {

            if (!reviews.isEmpty()) {
                view.setVisibility(View.VISIBLE);
                reviewAdapter = new ReviewHorizontalGridPresenter();

                ArrayObjectAdapter objectAdapter = new ArrayObjectAdapter();

                for (Review it : reviews) {
                    objectAdapter.add(it);
                }

                hGridView.setAdapter(new ItemBridgeAdapter(objectAdapter, new SinglePresenterSelector(reviewAdapter)));
                hGridView.setItemSpacing((int) mContext.getResources().getDimension(R.dimen.spacing));
            } else {
                Log.d(TAG, "bind: list size 0");
                reviewTextView.setVisibility(View.GONE);
                hGridView.setVisibility(View.GONE);
            }
        }
    }
}

package com.smartsoft.movietracker.view.detail;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.leanback.widget.HorizontalGridView;
import androidx.leanback.widget.Presenter;

import com.smartsoft.movietracker.R;
import com.smartsoft.movietracker.model.review.Review;
import com.smartsoft.movietracker.model.review.ReviewList;

import org.w3c.dom.Text;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ReviewPresenter extends Presenter {
    private Context mContext;
    private static final String TAG = ReviewPresenter.class.getName();

    public ReviewPresenter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        return new PresenterViewHolder(View.inflate(mContext, R.layout.review_section_layout, null));
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Object item) {
        PresenterViewHolder holder = (PresenterViewHolder) viewHolder;
        ArrayList<Review> reviews  = ((ReviewList)item).getReview();
        holder.bind(reviews);
    }

    @Override
    public void onUnbindViewHolder(ViewHolder viewHolder) {

    }

    class PresenterViewHolder extends ViewHolder{

        @BindView(R.id.review_textView )
        TextView reviewTextView;

        @BindView(R.id.review_GridView)
        HorizontalGridView hGridView;

        DetailPageReviewAdapter reviewAdapter;

        public PresenterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            reviewAdapter = new DetailPageReviewAdapter(mContext);
            hGridView.setNumRows(1);
            hGridView.setAdapter(reviewAdapter);
        }

        public void bind(ArrayList<Review> reviews){
            if(!reviews.isEmpty()){
                view.setVisibility(View.VISIBLE);
                reviewAdapter.addAllToList(reviews);
            }else{
                Log.d(TAG, "bind: list size 0");
                view.setVisibility(View.GONE);
            }
        }
    }
}

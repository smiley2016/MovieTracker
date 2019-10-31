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
import com.smartsoft.movietracker.model.cast.Cast;
import com.smartsoft.movietracker.model.review.Review;
import com.smartsoft.movietracker.model.review.ReviewList;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @see CastVerticalGridPresenter is
 * a {@link androidx.leanback.widget.VerticalGridView} component
 * for handle the {@link Review} element rollability in vertical direction
 */
public class ReviewVerticalGridPresenter extends Presenter {
    /**
     * Contains the class name
     */
    private static final String TAG = ReviewVerticalGridPresenter.class.getName();
    /**
     * Where {@link ReviewVerticalGridPresenter} is running
     */
    private Context mContext;

    /**
     * This function create the from the XML cast_element layout a real view
     * @param parent The ViewGroup wherein the
     *               {@link android.view.LayoutInflater} will paints the XML
     * @return {@link PresenterViewHolder}
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        mContext = parent.getContext();
        return new PresenterViewHolder(View.inflate(mContext, R.layout.review_section_layout, null));
    }

    /**
     * Onto every single element the {@link #onBindViewHolder(ViewHolder, Object)}
     * calls the {@link PresenterViewHolder#bind(ArrayList)} function to initializes it's views
     * how behaves in the app.
     * @param viewHolder {@link androidx.leanback.widget.Presenter.ViewHolder} what holds the elements in
     * {@link androidx.leanback.widget.VerticalGridView}
     * @param item Contains all the elements from the {@link androidx.leanback.widget.ArrayObjectAdapter}
     */
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Object item) {
        PresenterViewHolder holder = (PresenterViewHolder) viewHolder;
        ArrayList<Review> reviews = ((ReviewList) item).getReview();
        holder.bind(reviews);
    }

    /**
     * App doesn't use this function
     * @param viewHolder {@link androidx.leanback.widget.Presenter.ViewHolder} what holds the elements in
     *                  {@link androidx.leanback.widget.HorizontalGridView}
     */
    @Override
    public void onUnbindViewHolder(ViewHolder viewHolder) {

    }

    /**
     * Inner class is contains a {@link Cast} views of the element
     * and it's properties
     */
    class PresenterViewHolder extends ViewHolder {

        @BindView(R.id.review_textView)
        TextView reviewTextView;

        @BindView(R.id.review_GridView)
        HorizontalGridView hGridView;

        @BindView(R.id.review_section_layout)
        RelativeLayout layout;


        ReviewHorizontalGridPresenter reviewAdapter;

        /**
         * Inner class constructor
         * @param view The initialized element
         */
        public PresenterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);

        }

        /**
         * Initializer function for one element
         * @param reviews {@link ArrayList} that contains the {@link Review}
         *                              objects which will be initialize
         */
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

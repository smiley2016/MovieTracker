package com.smartsoft.movietracker.view.detail;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.leanback.widget.Presenter;

import com.smartsoft.movietracker.R;
import com.smartsoft.movietracker.model.cast.Cast;
import com.smartsoft.movietracker.model.review.Review;
import com.smartsoft.movietracker.view.dialogs.ReviewDialog;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @see ReviewHorizontalGridPresenter is
 * a {@link androidx.leanback.widget.HorizontalGridView} component
 * for handle the {@link Cast} element rollability in horizontal direction
 */
public class ReviewHorizontalGridPresenter extends Presenter {

    /**
     * Where the {@link ReviewHorizontalGridPresenter} is initialized and running.
     */
    private Context mContext;

    /**
     * This function create the from the XML cast_element layout a real view
     * @param parent The ViewGroup wherein the
     *               {@link android.view.LayoutInflater} will paints the XML
     * @return {@link ReviewHorizontalGridPresenter.PresenterViewHolder}
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent) {
        mContext = parent.getContext();
        return new PresenterViewHolder(View.inflate(mContext, R.layout.review_element, null));
    }

    /**
     * Onto every single element the {@link ReviewHorizontalGridPresenter#onBindViewHolder(ViewHolder, Object)}
     * calls the {@link ReviewHorizontalGridPresenter.PresenterViewHolder#bind(Context, Review)} function to initializes it's views
     * how behaves in the app.
     * @param viewHolder {@link androidx.leanback.widget.Presenter.ViewHolder} what holds the elements in
     * {@link androidx.leanback.widget.HorizontalGridView}
     * @param item Contains all the elements from the {@link androidx.leanback.widget.ArrayObjectAdapter}
     */
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Object item) {
        PresenterViewHolder holder = (PresenterViewHolder) viewHolder;
        Review review = ((Review) item);
        holder.bind(mContext, review);
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
     * Inner class is contains a {@link Review} views of the element
     * and it's properties
     */
    class PresenterViewHolder extends ViewHolder {
        @BindView(R.id.review_comment)
        TextView reviewComment;
        @BindView(R.id.reviewer)
        TextView reviewer;
        @BindView(R.id.review_element_layout)
        RelativeLayout layout;


        /**
         * Inner class constructor
         * @param view The view that holding the presenter
         */
        public PresenterViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }

        /**
         * Initializer function for one element
         * @param ctx Context where the {@link ReviewHorizontalGridPresenter} is running
         * @param review {@link Review} object which will be initialized
         */
        void bind(Context ctx, Review review) {
            reviewer.setText(review.getAuthor());
            reviewComment.setText(review.getContent());
            layout.setOnClickListener(view ->
                    new ReviewDialog(review.getContent(), review.getAuthor(), ctx));
        }
    }
}

package com.smartsoft.movietracker.view.detail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.smartsoft.movietracker.R;
import com.smartsoft.movietracker.model.review.Review;
import com.smartsoft.movietracker.presenter.DetailPagePresenter;

import java.util.ArrayList;

import okhttp3.internal.connection.RealConnection;

public class DetailPageReviewAdapter extends RecyclerView.Adapter<DetailPageReviewAdapter.Holder> {

    private DetailPagePresenter presenter;
    private ArrayList<Review> reviews;
    private Context ctx;


    public DetailPageReviewAdapter(Context ctx) {
        this.ctx = ctx;
        reviews = new ArrayList<>();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new Holder(LayoutInflater.from(ctx).inflate(R.layout.review_element, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.bind(ctx, reviews.get(position));
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }

    public void addAllToList(ArrayList<Review> reviews) {
        this.reviews.addAll(reviews);
        notifyDataSetChanged();
    }

    public static class Holder extends RecyclerView.ViewHolder{
        private TextView reviewComment;
        private TextView reviewer;
        private RelativeLayout layout;

        public Holder(@NonNull View itemView) {

            super(itemView);
            layout = itemView.findViewById(R.id.cast_element_layout);
            reviewer = itemView.findViewById(R.id.reviewer);
            reviewComment = itemView.findViewById(R.id.review_comment);
        }

        public void bind(Context ctx,Review review){
            reviewer.setText(review.getContent());
            reviewComment.setText(review.getAuthor());
        }
    }
}

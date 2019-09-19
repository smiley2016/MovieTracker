package com.smartsoft.movietracker.view.detail;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.smartsoft.movietracker.R;
import com.smartsoft.movietracker.model.review.Review;
import com.smartsoft.movietracker.utils.Dialogs;

import java.util.ArrayList;

public class DetailPageReviewAdapter extends RecyclerView.Adapter<DetailPageReviewAdapter.Holder> {

    private ArrayList<Review> reviews;
    private Context ctx;


    DetailPageReviewAdapter(Context ctx) {
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

    void addAllToList(ArrayList<Review> reviews) {
        this.reviews.addAll(reviews);
        notifyDataSetChanged();
    }

    static class Holder extends RecyclerView.ViewHolder{
        private TextView reviewComment;
        private TextView reviewer;
        private RelativeLayout layout;

        Holder(@NonNull View itemView) {

            super(itemView);
            layout = itemView.findViewById(R.id.review_element_layout);
            reviewer = itemView.findViewById(R.id.reviewer);
            reviewComment = itemView.findViewById(R.id.review_comment);
        }

        void bind(Context ctx, Review review){
            reviewer.setText(review.getAuthor());
            reviewComment.setText(review.getContent());
            layout.setOnClickListener(view -> Dialogs.startReviewDialog(ctx, review.getContent(), review.getAuthor()));
        }
    }
}

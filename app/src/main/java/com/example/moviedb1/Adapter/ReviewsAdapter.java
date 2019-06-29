package com.example.moviedb1.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.moviedb1.Data.Review;
import com.example.moviedb1.R;

import java.util.ArrayList;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ViewHolder> {
// Reviews  adapter used to adapt the Reviews data to its recyclerView

    Context context;
    ArrayList<Review> reviewArrayList = new ArrayList<>();

    public ReviewsAdapter(Context context, ArrayList<Review> reviews) {
        this.context = context;
        this.reviewArrayList = reviews;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_reviews, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        final Review review = reviewArrayList.get(i);


        viewHolder.reviewAuthor.setText(review.getAuthor());

        viewHolder.reviewText.setText(review.getContent());
    }

    @Override
    public int getItemCount() {
        return reviewArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView reviewAuthor;
        TextView reviewText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            reviewAuthor = itemView.findViewById(R.id.review_author);
            reviewText = itemView.findViewById(R.id.review_text);
        }
    }
}

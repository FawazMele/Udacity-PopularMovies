package com.example.moviedb1.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.moviedb1.Activity.DetailsActivity;
import com.example.moviedb1.Data.Movie;
import com.example.moviedb1.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    // Movies  adapter used to adapt the movies data to the recyclerView
    Context context;
    ArrayList<Movie> moviesArray = new ArrayList<>();
    String sortType;

    public RecyclerAdapter(Context context, ArrayList<Movie> moviesArray, String sortType) {
        this.moviesArray = moviesArray;
        this.context = context;
        this.sortType = sortType;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        final Movie movie = moviesArray.get(i);

        if (sortType == "favorites") {
            Picasso.get()
                    .load(movie.getPoster_path())
                    .placeholder(R.mipmap.ic_launcher_round)
                    .into(viewHolder.itemImage);
        } else {
            Picasso.get()

                    .load("http://image.tmdb.org/t/p/w185" + movie.getPoster_path())
                    .placeholder(R.mipmap.ic_launcher_round)
                    .into(viewHolder.itemImage);
        }

        viewHolder.itemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(new Intent(context, DetailsActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                Bundle b = new Bundle();
                b.putDouble("voteAverage", movie.getVote_average());
                i.putExtras(b);
                i.putExtra("title", movie.getTitle());
                i.putExtra("releaseDate", movie.getRelease_date());
                i.putExtra("overView", movie.getOverview());
                i.putExtra("poster", movie.getPoster_path());
                i.putExtra("movie", movie);
                i.putExtra("movieID", movie.getId());

                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return moviesArray.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        RelativeLayout itemLayout;
        ImageView itemImage;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            itemLayout = itemView.findViewById(R.id.item_layout);
            itemImage = itemView.findViewById(R.id.item_image);
        }
    }
}

package com.example.moviedb1.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.moviedb1.Data.Trailer;
import com.example.moviedb1.R;

import java.util.ArrayList;

public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.ViewHolder> {
// Trailers  adapter used to adapt the Reviews data to its recyclerView

    Context context;
    ArrayList<Trailer> trailerArrayList = new ArrayList<>();

    public TrailersAdapter(Context context, ArrayList<Trailer> trailers) {
        this.context = context;
        this.trailerArrayList = trailers;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_trailers, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {

        final Trailer trailer = trailerArrayList.get(i);

        viewHolder.trailerText.setText(trailer.getName());

        viewHolder.trailerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://www.youtube.com/watch?v=" + trailer.getKey();
                Intent i = new Intent(new Intent(Intent.ACTION_VIEW).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK));
                i.setData(Uri.parse(url));
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return trailerArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView trailerText;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            trailerText = itemView.findViewById(R.id.trailer_text);
        }
    }
}

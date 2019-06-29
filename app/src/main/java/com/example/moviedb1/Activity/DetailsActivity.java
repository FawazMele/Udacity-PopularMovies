package com.example.moviedb1.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moviedb1.Adapter.ReviewsAdapter;
import com.example.moviedb1.Adapter.TrailersAdapter;
import com.example.moviedb1.ApiResponse.ReviewResponse;
import com.example.moviedb1.ApiResponse.TrailerResponse;
import com.example.moviedb1.Data.Movie;
import com.example.moviedb1.Data.RetrofitSingleton;
import com.example.moviedb1.Data.Review;
import com.example.moviedb1.Data.RoomDB;
import com.example.moviedb1.Data.Trailer;
import com.example.moviedb1.Interface.Services;
import com.example.moviedb1.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsActivity extends AppCompatActivity {
    // Details of every movie and the ability to add movies to favorites and to launch trailers
    Boolean favoritesClicked = false;
    String apiKey = "1dfbc1ac58f1f1aa2467406da67afbae";
    long movieID;
    ArrayList<Trailer> trailerArrayList = new ArrayList<>();
    ArrayList<Review> reviewArrayList = new ArrayList<>();
    RecyclerView trailersRecycler;
    RecyclerView reviewsRecycler;
    private RoomDB database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        database = RoomDB.getDatabase(getApplicationContext());

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        String title = intent.getStringExtra("title");
        String releaseDate = intent.getStringExtra("releaseDate");
        Double voteAverage = bundle.getDouble("voteAverage");
        String overView = intent.getStringExtra("overView");
        final String posterURL = intent.getStringExtra("poster");
        final Movie movie = (Movie) getIntent().getSerializableExtra("movie");
        movieID = intent.getLongExtra("movieID", 0);


        ImageView moviePosterIV = (ImageView) findViewById(R.id.movie_poster);
        TextView overViewTV = (TextView) findViewById(R.id.movie_overview);
        TextView titleTV = (TextView) findViewById(R.id.movie_title);
        TextView voteAverageTV = (TextView) findViewById(R.id.vote_average);
        TextView releaseDateTV = (TextView) findViewById(R.id.movie_release_date);

        trailersRecycler = (RecyclerView) findViewById(R.id.trailersRecycler);
        trailersRecycler.setLayoutManager(new LinearLayoutManager(this));
        reviewsRecycler = (RecyclerView) findViewById(R.id.reviewsRecycler);
        reviewsRecycler.setLayoutManager(new LinearLayoutManager(this));

        getTrailers();
        getReviews();

        final ImageView favorites = (ImageView) findViewById(R.id.star);
        favorites.setImageResource(R.drawable.unfav_star);
        favorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (favoritesClicked == false) {
                    favorites.setImageResource(R.drawable.fav_star);
                    Toast.makeText(DetailsActivity.this, R.string.added_to_favorites, Toast.LENGTH_SHORT).show();
                   insertFavMovie(movie);
                } else {
                    favorites.setImageResource(R.drawable.unfav_star);
                    Toast.makeText(DetailsActivity.this, R.string.removed_from_favorites, Toast.LENGTH_SHORT).show();
                    deleteFavMovie(movie);
                }
            }
        });

        if (posterURL.startsWith("http")) {
            Picasso.get()
                    .load(posterURL)
                    .placeholder(R.mipmap.ic_launcher_round)
                    .into(moviePosterIV);
        } else {
            Picasso.get()
                    .load("http://image.tmdb.org/t/p/w185" + posterURL)
                    .placeholder(R.mipmap.ic_launcher_round)
                    .into(moviePosterIV);
        }
        overViewTV.setText(overView);
        titleTV.setText(title);
        voteAverageTV.setText(voteAverage.toString());
        releaseDateTV.setText(releaseDate);
    }

    private void getReviews() {

        Services services = RetrofitSingleton.newInstance().create(Services.class);
        Call<ReviewResponse> call = services.getReviews(movieID, apiKey);
        call.enqueue(new Callback<ReviewResponse>() {
            @Override
            public void onResponse(Call<ReviewResponse> call, Response<ReviewResponse> response) {

                ReviewResponse reviewResponse = response.body();
                reviewArrayList.addAll(reviewResponse.getResults());
                ReviewsAdapter reviewsAdapter = new ReviewsAdapter(getApplicationContext(), reviewArrayList);
                reviewsRecycler.setAdapter(reviewsAdapter);
                reviewsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<ReviewResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), R.string.network_issue, Toast.LENGTH_LONG).show();
            }
        });
    }

    private void getTrailers() {

        Services services = RetrofitSingleton.newInstance().create(Services.class);
        Call<TrailerResponse> call = services.getTrailer(movieID, apiKey);
        call.enqueue(new Callback<TrailerResponse>() {
            @Override
            public void onResponse(Call<TrailerResponse> call, Response<TrailerResponse> response) {

                TrailerResponse trailerResponse = response.body();
                trailerArrayList.addAll(trailerResponse.getResults());
                TrailersAdapter trailersAdapter = new TrailersAdapter(getApplicationContext(), trailerArrayList);
                trailersRecycler.setAdapter(trailersAdapter);
                trailersAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<TrailerResponse> call, Throwable t) {
                Toast.makeText(getApplicationContext(), R.string.network_issue, Toast.LENGTH_LONG).show();
            }
        });
    }

    public void insertFavMovie(Movie movie) {
        favoritesClicked = true;
        new FavoriteMoviesAsyncTask().execute(movie);
    }

    public void deleteFavMovie(Movie movie) {
        favoritesClicked = false;
        new FavoriteMoviesAsyncTask().execute(movie);
    }

    private class FavoriteMoviesAsyncTask extends AsyncTask<Movie, Void, Void> {
        @Override
        protected Void doInBackground(Movie... movieModel) {
            if (favoritesClicked) {
                database.moviesDAO().insertMovie(movieModel[0]);
            } else {
                database.moviesDAO().deleteMovie(movieModel[0].getId());
            }
            return null;
        }
    }
}

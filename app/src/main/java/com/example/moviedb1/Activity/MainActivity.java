package com.example.moviedb1.Activity;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.moviedb1.Adapter.RecyclerAdapter;
import com.example.moviedb1.Data.Movie;
import com.example.moviedb1.Data.MovieViewModel;
import com.example.moviedb1.R;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    // the main activity starts with launching default movies waiting user to select sorting from the menu
    String apiKey = "1dfbc1ac58f1f1aa2467406da67afbae";
    ArrayList<Movie> movieArrayList = new ArrayList<>();
    RecyclerView recyclerView;
    RecyclerAdapter adapter;
    MovieViewModel viewModel;
    String sortType;
    private Parcelable mLayoutManagerState;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        recyclerView = findViewById(R.id.recycler_view);
        if (savedInstanceState != null) {
            mLayoutManagerState = savedInstanceState.getParcelable("savedState");
            sortType = savedInstanceState.getString("sortingType");
        }
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        getMoviesBySort(sortType);
    }

    private void getMoviesBySort(String sortType) {
        adapter = new RecyclerAdapter(getApplicationContext(), movieArrayList, sortType);
        recyclerView.setAdapter(adapter);
        if (sortType == "favorites") { // if the sort is favorites get the movies and observe the liveData (View Model) MVVM approach
            viewModel.getMovies(true, "").observe(this, new Observer<List<Movie>>() {
                @Override
                public void onChanged(@Nullable List<Movie> movieModels) {
                    movieArrayList.clear();
                    if (movieModels != null) {
                        movieArrayList.addAll(movieModels);
                        adapter.notifyDataSetChanged();
                    }
                }
            });
        } else {// if the sort is not favorites get the movies and observe the liveData (View Model) MVVM approach
            viewModel.getMovies(false, sortType).observe(this, new Observer<List<Movie>>() {
                @Override
                public void onChanged(@Nullable List<Movie> movieModels) {
                    movieArrayList.clear();
                    if (movieModels != null) {
                        movieArrayList.addAll(movieModels);
                        adapter.notifyDataSetChanged();
                    }
                }
            });
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable( "savedState", mLayoutManagerState);
        outState.putString("sortingType" , sortType);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.sort_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) { // switch case to check the selected sort and to populate the list after selection
            case R.id.most_popular:
                sortType = "most_popular";
                getMoviesBySort(sortType);
                return true;
            case R.id.top_rated:
                sortType = "top_rated";
                getMoviesBySort(sortType);
                return true;
            case R.id.favorites:
                sortType = "favorites";
                getMoviesBySort(sortType);
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}

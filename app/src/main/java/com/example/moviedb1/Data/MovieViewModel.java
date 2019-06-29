package com.example.moviedb1.Data;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.example.moviedb1.ApiResponse.MoviesResponse;
import com.example.moviedb1.Interface.Services;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieViewModel extends AndroidViewModel {
    // View model to the Movie

    RoomDB database;
    MutableLiveData<List<Movie>> movieLiveData = new MutableLiveData<>();
    String apiKey = "1dfbc1ac58f1f1aa2467406da67afbae";


    public MovieViewModel(@NonNull Application application) {
        super(application);
        database = RoomDB.getDatabase(application);
    }

    public LiveData<List<Movie>> getMovies(boolean isFavorite, String sort) {


        if (isFavorite) { // get the data from room DB if favorites selected
            new FavoritesAsyncTask().execute();
            return movieLiveData;

        } else { // get the data from the API if other than favorites selected

            Services services = RetrofitSingleton.newInstance().create(Services.class);
            Call<MoviesResponse> call = services.getPopularMovies(apiKey);

            if (sort == "most_popular") {
                call = services.getPopularMovies(apiKey);
            } else if (sort == "top_rated") {
                call = services.getTopRatedMovies(apiKey);
            }

            call.enqueue(new Callback<MoviesResponse>() {
                @Override
                public void onResponse(Call<MoviesResponse> call, Response<MoviesResponse> response) {

                    MoviesResponse moviesResponse = response.body();

                    movieLiveData.postValue(moviesResponse.getResults());
                }

                @Override
                public void onFailure(Call<MoviesResponse> call, Throwable t) {
                    Toast.makeText(getApplication(), "Connection Failure", Toast.LENGTH_LONG).show();
                }
            });

            return movieLiveData;
        }
    }

    public class FavoritesAsyncTask extends AsyncTask<Void, Void, MutableLiveData<List<Movie>>> {
        @Override
        protected MutableLiveData<List<Movie>> doInBackground(Void... voids) {
            movieLiveData.postValue(database.moviesDAO().getAllMovies());
            return movieLiveData;
        }
    }
}
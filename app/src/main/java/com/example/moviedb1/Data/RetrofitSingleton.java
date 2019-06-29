package com.example.moviedb1.Data;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitSingleton {
    // Retrofit Singleton approach to reduce redundant coding
    private static Retrofit retrofit;

    private RetrofitSingleton() {
    }

    public static Retrofit newInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl("http://api.themoviedb.org/3/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
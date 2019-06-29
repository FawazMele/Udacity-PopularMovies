package com.example.moviedb1.Interface;

import com.example.moviedb1.ApiResponse.MoviesResponse;
import com.example.moviedb1.ApiResponse.ReviewResponse;
import com.example.moviedb1.ApiResponse.TrailerResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Services {

    // Retrofit Services to Query Data based on networking protocols

    @GET("movie/popular")
    Call<MoviesResponse> getPopularMovies(@Query("api_key") String apiKey);

    @GET("movie/top_rated")
    Call<MoviesResponse> getTopRatedMovies(@Query("api_key") String apiKey);

    @GET("movie/{id}/videos")
    public Call<TrailerResponse> getTrailer(@Path("id") long id, @Query("api_key") String apiKey);

    @GET("movie/{id}/reviews")
    public Call<ReviewResponse> getReviews(@Path("id") long id, @Query("api_key") String apiKey);

}
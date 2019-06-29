package com.example.moviedb1.ApiResponse;

import com.example.moviedb1.Data.Trailer;

import java.util.ArrayList;

public class TrailerResponse {
    // mapping the API response into a POJO class of Trailers

    private long id;

    private ArrayList<Trailer> results = new ArrayList<Trailer>();

    public ArrayList<Trailer> getResults() {
        return results;
    }

    public long getId() {
        return id;
    }

}
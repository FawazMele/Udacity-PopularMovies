package com.example.moviedb1.ApiResponse;

import com.example.moviedb1.Data.Review;

import java.util.ArrayList;

public class ReviewResponse {
    // mapping the API response into a POJO class of reviews
    private long id;
    private long page;
    private ArrayList<Review> results = new ArrayList<Review>();
    private long totalPages;
    private long totalResults;

    public long getId() {
        return id;
    }

    public long getPage() {
        return page;
    }

    public ArrayList<Review> getResults() {
        return results;
    }

    public long getTotalPages() {
        return totalPages;
    }

    public long getTotalResults() {
        return totalResults;
    }
}
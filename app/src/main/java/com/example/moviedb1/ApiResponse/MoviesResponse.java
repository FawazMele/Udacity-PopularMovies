package com.example.moviedb1.ApiResponse;

import com.example.moviedb1.Data.Movie;

import java.util.List;

public class MoviesResponse {
    // mapping the API response into a POJO class of Movies
    private Integer page;

    private Integer totalResults;

    private Integer totalPages;

    private List<Movie> results = null;


    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(Integer totalResults) {
        this.totalResults = totalResults;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public List<Movie> getResults() {
        return results;
    }

    public void setResults(List<Movie> results) {
        this.results = results;
    }
}
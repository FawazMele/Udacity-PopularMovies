package com.example.moviedb1.Interface;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.moviedb1.Data.Movie;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface MoviesDAO {

    // Data access interface to make the Database operations

    @Insert(onConflict = REPLACE)
    public void insertMovie(Movie movie);

    @Query("DELETE FROM Movie WHERE id = :id")
    void deleteMovie(long id);

    @Query("Select * from Movie")
    public List<Movie> getAllMovies();

}

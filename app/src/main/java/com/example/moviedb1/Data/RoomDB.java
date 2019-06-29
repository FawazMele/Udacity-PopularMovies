package com.example.moviedb1.Data;

import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.moviedb1.Interface.MoviesDAO;

@android.arch.persistence.room.Database(entities = Movie.class, version = 8, exportSchema = false)

public abstract class RoomDB extends RoomDatabase {
    // Room Database class Singleton approach
    private static RoomDB database;

    public static RoomDB getDatabase(Context context) {
        if (database == null) {
            database = Room.databaseBuilder(context, RoomDB.class, "Favorites.db").fallbackToDestructiveMigration().build();
        }
        return database;
    }

    public abstract MoviesDAO moviesDAO();
}
package com.example.movieapplication.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.movieapplication.model.Movie

@Database(entities = [Movie::class], version = 1)
@TypeConverters(MovieConverters::class)
abstract class MovieDatabase: RoomDatabase() {
    abstract fun movieDao(): MovieDao

    companion object {
        const val MOVIE_DATABASE_NAME = "movie"
    }
}
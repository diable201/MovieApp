package com.example.movieapplication.data.db


import androidx.room.*
import com.example.movieapplication.model.Movie
import io.reactivex.Observable

@Dao
interface MovieDao {
    @Query("SELECT * FROM movie")
    fun getAll(): Observable<List<Movie>>

    @Insert
    fun insertAll(movies: List<Movie>)

    @Delete
    fun delete(movie: Movie)

    @Query("DELETE FROM movie")
    fun deleteAll()
}
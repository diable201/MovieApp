package com.example.movieapplication.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.movieapplication.di.DaggerApplicationComponent
import com.example.movieapplication.model.APIResponse
import com.example.movieapplication.model.Movie
import com.example.movieapplication.model.MoviesRepository
import com.example.movieapplication.utils.MovieListUtils
import javax.inject.Inject

class MoviesViewModel: ViewModel() {
    @Inject lateinit var repository: MoviesRepository

    var movies: LiveData<APIResponse<List<Movie>>>
        private set

    var currentMovie = MutableLiveData<Movie?>()
        private set

    init {
        DaggerApplicationComponent.create().inject(this)

        movies = repository.movies
    }

    fun refresh(list: MovieListUtils.MovieList) {
        repository.refresh(list)
    }

    fun getMovies(list: MovieListUtils.MovieList) {
        repository.getMovies(list)
    }

    fun setCurrentMovie(movie: Movie) {
        currentMovie.value = movie
    }

    override fun onCleared() {
        super.onCleared()

        repository.clear()
    }
}
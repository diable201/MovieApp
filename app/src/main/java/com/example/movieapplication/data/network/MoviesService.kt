package com.example.movieapplication.data.network


import com.example.movieapplication.di.DaggerApplicationComponent
import com.example.movieapplication.model.MovieWrapper
import com.example.movieapplication.utils.API_KEY
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MoviesService @Inject constructor(private val moviesAPI: MoviesAPI): BaseAPIService() {

    init {
        DaggerApplicationComponent.create().inject(this)
    }

    fun getTopRatedMovies(): Single<MovieWrapper> {
        return moviesAPI.getTopRatedMovies(API_KEY)
    }

    fun getMostPopularMovies(): Single<MovieWrapper> {
        return moviesAPI.getMostPopularMovies(API_KEY)
    }

    companion object {
        const val PHOTO_BASE_URL = "https://image.tmdb.org/t/p/w500"
    }
}
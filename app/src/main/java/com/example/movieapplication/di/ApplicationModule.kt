package com.example.movieapplication.di

import com.example.movieapplication.data.network.MoviesAPI
import com.example.movieapplication.data.network.MoviesService
import com.example.movieapplication.model.MoviesRepository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class ApplicationModule() {
    private val BASE_URL = "https://api.themoviedb.org/3/"


    @Singleton
    @Provides
    fun provideMoviesAPI(): MoviesAPI {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
            .create(MoviesAPI::class.java)
    }

    @Singleton
    @Provides
    fun provideMoviesService(api: MoviesAPI): MoviesService {
        return MoviesService(api)
    }

    @Singleton
    @Provides
    fun provideMoviesRepository(service: MoviesService): MoviesRepository {
        return MoviesRepository(service)
    }
}
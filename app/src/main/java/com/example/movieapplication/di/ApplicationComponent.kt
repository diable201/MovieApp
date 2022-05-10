package com.example.movieapplication.di

import com.example.movieapplication.data.network.MoviesService
import com.example.movieapplication.model.MoviesRepository
import com.example.movieapplication.view.MainActivity
import com.example.movieapplication.viewmodel.MoviesViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {
    fun inject(service: MoviesService)
    fun inject(moviesRepository: MoviesRepository)
    fun inject(moviesViewModel: MoviesViewModel)
    fun inject(mainActivity: MainActivity)
}
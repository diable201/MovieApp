package com.example.movieapplication.application

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.example.movieapplication.di.ApplicationComponent
import com.example.movieapplication.di.ApplicationModule
import com.example.movieapplication.di.DaggerApplicationComponent

class MovieStockApplication: Application() {
    private lateinit var component: ApplicationComponent

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)

        MultiDex.install(this);

    }
    override fun onCreate() {
        super.onCreate()

        appContext = this
        component = DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule())
            .build()
    }


    fun getAppComponent(): ApplicationComponent {
        return component
    }


    companion object {
        lateinit var appContext: Application
    }
}
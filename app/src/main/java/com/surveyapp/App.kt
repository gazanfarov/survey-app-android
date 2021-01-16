package com.surveyapp

import android.app.Application
import com.surveyapp.di.interactorsModule
import com.surveyapp.di.repositoriesModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        // Set up DI with Koin
        setUpKoin()
    }

    private fun setUpKoin() {
        // Start Koin for DI
        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(
                listOf(
                    repositoriesModule,
                    interactorsModule
                )
            )
        }
    }
}
package com.udacity.asteroidradar

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class AsteroidApplication : Application() {
    private val appScope = CoroutineScope(Dispatchers.Default)

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

    }

    private fun delayInit() {
        appScope.launch {

        }
    }
}
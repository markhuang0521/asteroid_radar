package com.udacity.asteroidradar.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.Repository.AsteroidRepo
import com.udacity.asteroidradar.database.getDatabase
import retrofit2.HttpException

class RefreshDataWorker(appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {

    companion object {
        const val WORK_NAME = "RefreshDataWorker"
    }

    override suspend fun doWork(): Result {
        val db = getDatabase(applicationContext)
        val repo = AsteroidRepo(db)
        return try {
            repo.refreshAsteroids()
            repo.refreshPicOfDay()
            Result.success()
        } catch (e: HttpException) {
            Result.retry()
        }

    }
}
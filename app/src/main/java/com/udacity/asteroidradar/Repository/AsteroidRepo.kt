package com.udacity.asteroidradar.Repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.api.Network
import com.udacity.asteroidradar.api.asDatabaseModel
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.database.DbPictureOfDay
import com.udacity.asteroidradar.database.asDomain
import com.udacity.asteroidradar.domain.Asteroid
import com.udacity.asteroidradar.domain.PictureOfDay
import com.udacity.asteroidradar.domain.asDbModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import timber.log.Timber

class AsteroidRepo(private val db: AsteroidDatabase) {

    val savedAsteroids: LiveData<List<Asteroid>> =
        Transformations.map(db.asteroidDao.getSavedAsteroid()) { it.asDomain() }
    val todayAsteroids: LiveData<List<Asteroid>> =
        Transformations.map(db.asteroidDao.getTodayAsteroid()) { it.asDomain() }
    val weeklyAsteroids: LiveData<List<Asteroid>> =
        Transformations.map(db.asteroidDao.getWeeklyAsteroid()) { it.asDomain() }
    val picOfDay = Transformations.map(db.asteroidDao.getPictureOfDay()) { it.asDomain() }


    suspend fun refreshPicOfDay() {
        try {

            withContext(Dispatchers.IO) {
                val pictureOfDay = Network.retrofitService.getpicOfDay()
                val test = pictureOfDay.asDatabaseModel()
                db.asteroidDao.insert(test)
            }
        } catch (t: Throwable) {
            Timber.i(t.localizedMessage)

        }
    }

    suspend fun refreshAsteroids() {

        try {
            withContext(Dispatchers.IO) {
                val jsonString = Network.retrofitService.getAsteroidList()
                val asteroidList = parseAsteroidsJsonResult(JSONObject(jsonString))
                db.asteroidDao.insertAll(*asteroidList.asDbModel())
            }
        } catch (e: Exception) {
            Timber.i(e.localizedMessage)
        }
    }


}
package com.udacity.asteroidradar.Repository

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

    val asteroids = Transformations.map(db.asteroidDao.getSavedAsteroid()) { it.asDomain() }
    val todayAsteroids = Transformations.map(db.asteroidDao.getTodayAsteroid()) { it.asDomain() }
    val picOfDay = Transformations.map(db.picOfDayDao.getPictureOfDay()) { it.asDomain() }


    suspend fun refreshPicOfDay() {
        try {

            withContext(Dispatchers.IO) {
                val pictureOfDay = Network.retrofitService.getpicOfDay()
                val text = pictureOfDay.asDatabaseModel()
                db.picOfDayDao.insert(text)
            }
        } catch (e: Exception) {
            Timber.i(e.localizedMessage)

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
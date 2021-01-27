package com.udacity.asteroidradar.Repository

import androidx.lifecycle.Transformations
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.database.asDomain

class AsteroidRepo(private val db: AsteroidDatabase) {

    val asteroids = Transformations.map(db.asteroidDao.getSavedAsteroid()) { it.asDomain() }


}
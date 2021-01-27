package com.udacity.asteroidradar

import androidx.room.Database
import androidx.room.RoomDatabase
import com.udacity.asteroidradar.domain.Asteroid


@Database(entities = [Asteroid::class], version = 1)
class database() : RoomDatabase() {

}
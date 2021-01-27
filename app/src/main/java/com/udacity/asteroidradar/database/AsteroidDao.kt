package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.udacity.asteroidradar.domain.Asteroid


@Dao
interface AsteroidDao {

    @Query("SELECT * FROM tb_asteroid")
    abstract fun getSavedAsteriod(): LiveData<List<Asteroid>>
}
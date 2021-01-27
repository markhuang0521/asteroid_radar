package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.udacity.asteroidradar.domain.Asteroid


@Dao
interface AsteroidDao {

    @Query("SELECT * FROM tb_asteroid")
    fun getSavedAsteroid(): LiveData<List<DbAsteroid>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg asteroid: DbAsteroid)

}
package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.udacity.asteroidradar.domain.Asteroid
import com.udacity.asteroidradar.util.Constants


@Dao
interface AsteroidDao {


    @Query("SELECT * FROM tb_asteroid ORDER BY closeApproachDate")
    fun getSavedAsteroid(): LiveData<List<DbAsteroid>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg asteroid: DbAsteroid)

    @Query("SELECT * FROM tb_asteroid where closeApproachDate=:today ORDER BY closeApproachDate")
    fun getTodayAsteroid(today: Long = Constants.todayLong): LiveData<List<DbAsteroid>>

    @Query("SELECT * FROM tb_asteroid where closeApproachDate BETWEEN :today AND :endDate ORDER BY closeApproachDate")
    fun getWeeklyAsteroid(
        today: Long = Constants.todayLong,
        endDate: Long = Constants.endDayLong
    ): LiveData<List<DbAsteroid>>


}
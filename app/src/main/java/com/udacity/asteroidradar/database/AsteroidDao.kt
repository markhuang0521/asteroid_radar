package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface AsteroidDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(pic: DbPictureOfDay)

    @Query("SELECT * from tb_pictureOfDay ORDER BY date DESC LIMIT 1 ")
    // should a single object need to be live data as well?
    fun getPictureOfDay(): LiveData<DbPictureOfDay>

    @Query("SELECT * FROM tb_asteroid")
    fun getSavedAsteroid(): LiveData<List<DbAsteroid>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg asteroid: DbAsteroid)


}
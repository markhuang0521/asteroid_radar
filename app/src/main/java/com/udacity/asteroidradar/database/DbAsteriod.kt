package com.udacity.asteroidradar.database

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "tb_asteroid")
data class DbAsteriod (

    @PrimaryKey
    val url:String

)


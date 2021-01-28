package com.udacity.asteroidradar.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.udacity.asteroidradar.domain.Asteroid
import com.udacity.asteroidradar.domain.PictureOfDay


@Entity(tableName = "tb_pictureOfDay")
class DbPictureOfDay(
    @PrimaryKey
    val url: String,
    val mediaType: String,
    val title: String,
    var date: String

)

//changing from dbmodel to domain
fun DbPictureOfDay.asDomain(): PictureOfDay {
    return PictureOfDay(
        this.url,
        this.mediaType,
        this.title,
        date = this.date
    )

}

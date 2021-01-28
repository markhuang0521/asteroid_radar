package com.udacity.asteroidradar.api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import com.udacity.asteroidradar.database.DbPictureOfDay


@JsonClass(generateAdapter = true)
data class NetWorkPictureOfDay(

    val date: String,
    val explanation: String,
    @Json(name = "hdurl") val hdUrl: String,
    @Json(name = "media_type") val mediaType: String,
    @Json(name = "service_version") val serviceVersion: String,
    val title: String = "",
    val url: String = ""
)


fun NetWorkPictureOfDay.asDatabaseModel(): DbPictureOfDay {
    return DbPictureOfDay(
        url = this.url,
        mediaType = this.mediaType,
        title = this.title,
        date = this.date

    )

}




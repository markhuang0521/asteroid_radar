package com.udacity.asteroidradar.domain

import android.os.Parcelable
import com.udacity.asteroidradar.database.DbAsteroid
import com.udacity.asteroidradar.util.formatStringToTimeStamp
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Asteroid(
    val id: Long, val codename: String, val closeApproachDate: String,
    val absoluteMagnitude: Double, val estimatedDiameter: Double,
    val relativeVelocity: Double, val distanceFromEarth: Double,
    val isPotentiallyHazardous: Boolean
) : Parcelable


// changing from domain to dbmodel
fun List<Asteroid>.asDbModel(): Array<DbAsteroid> {
    return this.map {
        val dateLong = formatStringToTimeStamp(it.closeApproachDate)
        DbAsteroid(
            id = it.id,
            codename = it.codename,
            closeApproachDate = dateLong,
            absoluteMagnitude = it.absoluteMagnitude,
            estimatedDiameter = it.estimatedDiameter,
            relativeVelocity = it.relativeVelocity,
            distanceFromEarth = it.distanceFromEarth,
            isPotentiallyHazardous = it.isPotentiallyHazardous
        )
    }.toTypedArray()
}
package com.udacity.asteroidradar.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.udacity.asteroidradar.domain.Asteroid
import com.udacity.asteroidradar.util.formatTimeStampToString


@Entity(tableName = "tb_asteroid")
data class DbAsteroid(
    @PrimaryKey
    val id: Long,
    val codename: String,
    val closeApproachDate: Long,
    val absoluteMagnitude: Double,
    val estimatedDiameter: Double,
    val relativeVelocity: Double,
    val distanceFromEarth: Double,
    val isPotentiallyHazardous: Boolean

)


//changing from dbmodel to domain
fun List<DbAsteroid>.asDomain(): List<Asteroid> {
    return this.map {
        val dateString = formatTimeStampToString(it.closeApproachDate)
        Asteroid(
            it.id,
            it.codename,
            dateString,
            it.absoluteMagnitude,
            it.estimatedDiameter,
            it.relativeVelocity,
            it.distanceFromEarth,
            it.isPotentiallyHazardous

        )
    }
}


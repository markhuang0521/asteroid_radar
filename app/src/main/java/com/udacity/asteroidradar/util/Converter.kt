package com.udacity.asteroidradar.util

import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.*

class Converters {
    val formatter = SimpleDateFormat(Constants.API_QUERY_DATE_FORMAT, Locale.getDefault())

    @TypeConverter
    fun timestampToString(value: Long?): String? {
        return value?.let {
            val date = Date(it)
            formatter.format(date)
        }
    }

    @TypeConverter
    fun stringToTimestamp(string: String?): Long? {
        return string?.let {
            formatter.parse(string)?.time
        }
    }
}


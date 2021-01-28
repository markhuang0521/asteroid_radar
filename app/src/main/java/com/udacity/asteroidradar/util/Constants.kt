package com.udacity.asteroidradar.util

import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*


fun Date.formatToString(format: String, locale: Locale = Locale.getDefault()): String {
    val formatter = SimpleDateFormat(format, locale)
    return formatter.format(this)
}


private fun getDaysAfter(daysAgo: Int): Date {
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.DAY_OF_YEAR, daysAgo)
    val date = calendar.time
    val t2=date.time
    return calendar.time
}

object Constants {

    const val API_QUERY_DATE_FORMAT = "YYYY-MM-dd"
    const val DEFAULT_END_DATE_DAYS = 7
    const val BASE_URL = "https://api.nasa.gov/"


    val startDay = Calendar.getInstance().time.formatToString(API_QUERY_DATE_FORMAT)
    val endDay = getDaysAfter(7).formatToString(API_QUERY_DATE_FORMAT)


}
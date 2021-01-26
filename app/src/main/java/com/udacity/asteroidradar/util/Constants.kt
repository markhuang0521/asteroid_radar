package com.udacity.asteroidradar.util

import com.udacity.asteroidradar.BuildConfig
import java.text.SimpleDateFormat
import java.util.*


fun Date.formatToString(format: String, locale: Locale = Locale.getDefault()): String {
    val formatter = SimpleDateFormat(format, locale)
    return formatter.format(this)
}

private fun getDaysAfter(daysAgo: Int): Date {
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.DAY_OF_YEAR, daysAgo)

    return calendar.time
}

object Constants {

    const val API_QUERY_DATE_FORMAT = "YYYY-MM-dd"
    const val DEFAULT_END_DATE_DAYS = 7
    const val BASE_URL = "https://api.nasa.gov/"
    const val API_KEY = BuildConfig.API_KEY


    val startDay = Calendar.getInstance().time.formatToString(API_QUERY_DATE_FORMAT)
    val endDay = getDaysAfter(7).formatToString(API_QUERY_DATE_FORMAT)


}
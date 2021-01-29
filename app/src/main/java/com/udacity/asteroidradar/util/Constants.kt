package com.udacity.asteroidradar.util

import com.udacity.asteroidradar.util.Constants.API_QUERY_DATE_FORMAT
import java.text.SimpleDateFormat
import java.util.*


fun Date.formatToString(): String {
    val formatter = SimpleDateFormat(API_QUERY_DATE_FORMAT, Locale.getDefault())
    return formatter.format(this)
}

fun formatStringToTimeStamp(dateString: String): Long {
    val formatter = SimpleDateFormat(API_QUERY_DATE_FORMAT, Locale.getDefault())
    val date = formatter.parse(dateString)
    return date!!.time
}

fun formatTimeStampToString(timestamp: Long): String {
    val formatter = SimpleDateFormat("YYYY-MM-dd", Locale.getDefault())
    val date = Date(timestamp)
    return formatter.format(date)
}


private fun getDaysAfter(daysAgo: Int): Date {
    val calendar = Calendar.getInstance()
    calendar.add(Calendar.DAY_OF_YEAR, daysAgo)
    val date = calendar.time
    return calendar.time
}

object Constants {

    const val API_QUERY_DATE_FORMAT = "yyyy-MM-dd"
    const val DEFAULT_END_DATE_DAYS = 7
    const val BASE_URL = "https://api.nasa.gov/"


    val today = Calendar.getInstance().time.formatToString()
    val todayLong = formatStringToTimeStamp(today)
    var endDay = getDaysAfter(7).formatToString()
    var endDayLong: Long = formatStringToTimeStamp(endDay)


}

fun main() {
    val formatter = SimpleDateFormat("yyyy-MM-dd")

    val today = Calendar.getInstance().time.formatToString()
    val todayLong = formatStringToTimeStamp(today)
    var endDay = getDaysAfter(7).formatToString()
    var endDayLong: Long = formatStringToTimeStamp(endDay)
    println(today)
    println(todayLong)
    println(formatStringToTimeStamp(today))
//    1611906538868
    println(formatTimeStampToString(formatStringToTimeStamp(today)))
}
package com.udacity.asteroidradar.api

import com.udacity.asteroidradar.BuildConfig
import com.udacity.asteroidradar.util.Constants
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query


//BuildConfig.API_KEY
// using ano for ConverterFactory
annotation class JsonAno
annotation class StringAno


interface AsteroidService {

    @GET("neo/rest/v1/feed")
    @StringAno
    suspend fun getAsteroidList(
        @Query("start_date") startDate: String = Constants.startDay,
        @Query("end_date") endDate: String = Constants.endDay,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): String

    @GET("planetary/apod")
    @JsonAno
    suspend fun getpicOfDay(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): NetWorkPictureOfDay

}

object Network {
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(StringOrJsonConverterFactory())
        .baseUrl(Constants.BASE_URL)
        .build()

    val retrofitService: AsteroidService by lazy { retrofit.create(AsteroidService::class.java) }

}





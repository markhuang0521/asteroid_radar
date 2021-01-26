package com.udacity.asteroidradar.api

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.BuildConfig
import com.udacity.asteroidradar.domain.Asteroid
import com.udacity.asteroidradar.util.Constants
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.lang.reflect.Type


//BuildConfig.API_KEY
// using ano for ConverterFactory
annotation class JsonAno
annotation class StringAno


interface AsteroidService {

    @GET("neo/rest/v1/feed")
    @StringAno
    suspend fun getAsteroidList(
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): String

    @GET("planetary/apod")
    @JsonAno
    suspend fun getAsteroidOfDay(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY
    ): Asteroid

}

object Network {
    private val retrofit = Retrofit.Builder()
        .addConverterFactory(StringOrJsonConverterFactory())
        .baseUrl(Constants.BASE_URL)
        .build()

    val retrofitService: AsteroidService by lazy { retrofit.create(AsteroidService::class.java) }

}





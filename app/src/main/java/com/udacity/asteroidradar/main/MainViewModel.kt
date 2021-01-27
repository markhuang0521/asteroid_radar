package com.udacity.asteroidradar.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.udacity.asteroidradar.api.AsteroidOfDay
import com.udacity.asteroidradar.api.Network
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.domain.Asteroid
import com.udacity.asteroidradar.util.Constants
import kotlinx.coroutines.launch
import org.json.JSONObject
import timber.log.Timber


enum class ApiStatus { LOADING, DONE, ERROR }

class MainViewModel : ViewModel() {

    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus>
        get() = _status
    private val _refresh = MutableLiveData<Boolean>()
    val refresh: LiveData<Boolean>
        get() = _refresh

    private val _asteroidList = MutableLiveData<List<Asteroid>>()

    // The external LiveData interface to the property is immutable, so only this class can modify
    val asteroids: LiveData<List<Asteroid>>
        get() = _asteroidList


    private val _selectedAsteroid = MutableLiveData<Asteroid>()
    val selectedAsteroid: LiveData<Asteroid>
        get() = _selectedAsteroid

    private val _asteroidOfDay = MutableLiveData<AsteroidOfDay?>()
    val asteroidOfDay: LiveData<AsteroidOfDay?>
        get() = _asteroidOfDay


    init {
        getAsteroidList()
        getAsteroidOfDay()
    }

    fun onSelectAsteroid(asteroid: Asteroid) {
        _selectedAsteroid.value = asteroid
    }

    fun unSelectAsteroid() {
        _selectedAsteroid.value = null
    }


    fun onRefresh() {
        _refresh.value = true
    }

    fun onReady() {
        _refresh.value = false
    }

    fun refresh() {
        getAsteroidList()
    }


//    fun navigateToDetail() {
//        _navigateToDetail.value = true
//    }
//
//    fun doneNavigateToDetail() {
//        _navigateToDetail.value = null
//    }

    private fun getAsteroidOfDay() {
        viewModelScope.launch {
            try {
                val asteroid = Network.retrofitService.getAsteroidOfDay()
                _asteroidOfDay.value = asteroid

            } catch (t: Throwable) {
                _asteroidOfDay.value = AsteroidOfDay()
                Timber.i(t.localizedMessage)

            }

        }


    }

    private fun getAsteroidList() {
        viewModelScope.launch {
            _status.value = ApiStatus.LOADING
            try {
                val asteroidStrings =
                    Network.retrofitService.getAsteroidList(Constants.startDay, Constants.endDay)


                val asteroidList = parseAsteroidsJsonResult(JSONObject(asteroidStrings))
                _asteroidList.value = asteroidList
                _status.value = ApiStatus.DONE


            } catch (t: Throwable) {
                Timber.i(t.localizedMessage)
                _status.value = ApiStatus.ERROR
                _asteroidList.value = listOf()
            }
        }
    }


}
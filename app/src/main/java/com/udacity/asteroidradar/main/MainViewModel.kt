package com.udacity.asteroidradar.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    private val _navigateToDetail = MutableLiveData<Boolean>()
    val navigateToDetail: LiveData<Boolean>
        get() = _navigateToDetail
    private val _curAsteroid = MutableLiveData<Asteroid>()
    val curAsteroid: LiveData<Asteroid>
        get() = _curAsteroid


    init {
        getAsteroidFromApi()
    }

    fun onSelectAsteroid(asteroid: Asteroid) {
        _curAsteroid.value = asteroid
    }

    fun unSelectAsteroid() {
        _curAsteroid.value = null
    }


    fun onRefresh() {
        _refresh.value = true
    }

    fun onReady() {
        _refresh.value = false
    }

    fun refresh() {
        getAsteroidFromApi()
    }


    fun navigateToDetail() {
        _navigateToDetail.value = true
    }

    fun doneNavigateToDetail() {
        _navigateToDetail.value = null
    }


    private fun getAsteroidFromApi() {
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
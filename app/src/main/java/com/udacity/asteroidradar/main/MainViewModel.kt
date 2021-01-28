package com.udacity.asteroidradar.main

import android.app.Application
import androidx.lifecycle.*
import com.udacity.asteroidradar.Repository.AsteroidRepo
import com.udacity.asteroidradar.database.getDatabase
import com.udacity.asteroidradar.domain.Asteroid
import com.udacity.asteroidradar.domain.PictureOfDay
import kotlinx.coroutines.launch
import timber.log.Timber


enum class ApiStatus { LOADING, DONE, ERROR }


class MainViewModelFactory(val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(application) as T
        }
        throw IllegalArgumentException("Unable to construct viewModel")

    }
}


class MainViewModel(application: Application) : AndroidViewModel(application) {
//    private val _asteroidList = MutableLiveData<List<Asteroid>>()
//
//    // The external LiveData interface to the property is immutable, so only this class can modify
//    val asteroids: LiveData<List<Asteroid>>
//        get() = _asteroidList

    // fetching data using repo pattern
    private val db = getDatabase(application)
    private val repo = AsteroidRepo(db)

    val asteroids = repo.asteroids
    val picOfDay = repo.picOfDay

    init {

        refresh()

        Timber.i(picOfDay.toString())

    }


    private val _status = MutableLiveData<ApiStatus>()
    val status: LiveData<ApiStatus>
        get() = _status
    private val _refresh = MutableLiveData<Boolean>()
    val refresh: LiveData<Boolean>
        get() = _refresh


    private val _selectedAsteroid = MutableLiveData<Asteroid>()
    val selectedAsteroid: LiveData<Asteroid>
        get() = _selectedAsteroid


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
        viewModelScope.launch {
            repo.refreshAsteroids()
            repo.refreshPicOfDay()
        }
    }
    fun viewTodayAsteroid(){

    }



//    fun navigateToDetail() {
//        _navigateToDetail.value = true
//    }
//
//    fun doneNavigateToDetail() {
//        _navigateToDetail.value = null
//    }


    // fetching data from api using retrofit
//    private fun getAsteroidList() {
//        viewModelScope.launch {
//            _status.value = ApiStatus.LOADING
//            try {
//                val asteroidStrings =
//                    Network.retrofitService.getAsteroidList(Constants.startDay, Constants.endDay)
//
//
//                val asteroidList = parseAsteroidsJsonResult(JSONObject(asteroidStrings))
//                _asteroidList.value = asteroidList
//                _status.value = ApiStatus.DONE
//
//
//            } catch (t: Throwable) {
//                Timber.i(t.localizedMessage)
//                _status.value = ApiStatus.ERROR
//                _asteroidList.value = listOf()
//            }
//        }
//    }


}
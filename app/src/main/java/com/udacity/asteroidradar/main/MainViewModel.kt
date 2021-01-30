package com.udacity.asteroidradar.main

import android.annotation.SuppressLint
import android.app.Application
import androidx.lifecycle.*
import com.udacity.asteroidradar.Repository.AsteroidRepo
import com.udacity.asteroidradar.database.getDatabase
import com.udacity.asteroidradar.domain.Asteroid
import kotlinx.coroutines.launch
import timber.log.Timber


enum class ApiStatus { LOADING, DONE, ERROR }
enum class AsteroidOption { TODAY, WEEK, SAVED }


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

    private val _asteroids = MutableLiveData<List<Asteroid>>(repo.weeklyAsteroids.value)
    val today = repo.todayAsteroids
    val week = repo.weeklyAsteroids
    val save = repo.savedAsteroids
    var asteroids = repo.todayAsteroids

    val picOfDay = repo.picOfDay

    init {
        refresh()
        Timber.i("today" + today.value.toString())
        Timber.i("week" + week.value.toString())
        Timber.i("save" + save.value.toString())


        Timber.i("pic" + picOfDay.toString())
    }

    fun refresh() {
        viewModelScope.launch {
            repo.refreshPicOfDay()
            repo.refreshAsteroids()
        }
    }

    fun getTodayAsteroid() {
        asteroids = repo.todayAsteroids

//        _asteroids.value = repo.todayAsteroids.value
        Timber.i("today" + repo.todayAsteroids.value.toString())
    }

    fun getSavedAsteroid() {
//        _asteroids.value = save.value
        asteroids = repo.savedAsteroids

        Timber.i("save" + repo.savedAsteroids.value.toString())

    }

    fun getWeeklyAsteroid() {
        asteroids = repo.savedAsteroids
//        _asteroids.value = repo.weeklyAsteroids.value

        Timber.i("week" + repo.weeklyAsteroids.value.toString())

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


    fun viewTodayAsteroid() {

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
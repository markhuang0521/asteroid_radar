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


    // fetching data using repo pattern
    private val db = getDatabase(application)
    private val repo = AsteroidRepo(db)

    private val _option = MutableLiveData<AsteroidOption>(AsteroidOption.TODAY)

    // not sure if this is the proper way to update live data from repo, but it works alright to update the UI.
    // please suggest other ways to update livedata from repo
    var asteroids = Transformations.switchMap(_option)
    {
        when (it) {
            AsteroidOption.SAVED -> repo.savedAsteroids
            AsteroidOption.TODAY -> repo.todayAsteroids
            AsteroidOption.WEEK -> repo.weeklyAsteroids
            else -> repo.todayAsteroids

        }
    }

    //  not sure why pod is null from the repo when there are data in the room.
    // it has the same implementation as asteroid, but it was still initialized as null need more  clarification
    val picOfDay = repo.picOfDay

    fun setTodayOption() {
        _option.value = AsteroidOption.TODAY
    }

    fun setWeekOption() {
        _option.value = AsteroidOption.WEEK
    }

    fun setSaveOption() {
        _option.value = AsteroidOption.SAVED
    }


    init {
        //I can't initialize the api status as the api call is coming from repo.
        //please suggest how to I implement the loading icon
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            repo.refreshPicOfDay()
            repo.refreshAsteroids()
        }
    }


    // bad attends to update adapter livedata using repo.
    //please provide more info for  better understanding
//        fun getTodayAsteroid() {
//            asteroids = repo.todayAsteroids
//
////        _asteroids.value = repo.todayAsteroids.value
//            Timber.i("today" + repo.todayAsteroids.value.toString())
//        }
//
//        fun getSavedAsteroid() {
////        _asteroids.value = save.value
//            asteroids = repo.savedAsteroids
//
//            Timber.i("save" + repo.savedAsteroids.value.toString())
//
//        }
//
//        fun getWeeklyAsteroid() {
//            asteroids = repo.savedAsteroids
////        _asteroids.value = repo.weeklyAsteroids.value
//
//            Timber.i("week" + repo.weeklyAsteroids.value.toString())
//
//        }

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
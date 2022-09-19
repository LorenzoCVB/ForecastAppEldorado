package com.example.forecastappeldorado.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.example.forecastappeldorado.model.Main
import com.example.forecastappeldorado.model.WeatherModel
import com.example.forecastappeldorado.repository.MainRepository
import java.lang.IllegalArgumentException

private const val TAG = "MainViewModel"

class MainViewModel(private val mainRepository: MainRepository, application: Application) : AndroidViewModel(application) {

    val weatherData: MutableLiveData<WeatherModel> = mainRepository.weatherData

    fun getDataFromAPI(cityName: String) {
        mainRepository.getDataFromAPI(cityName)
    }
}

class MainViewModelFactory(
    private val repository: MainRepository,
    private val app: Application
) : ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(MainViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return MainViewModel(repository, app) as T
        }
        throw IllegalArgumentException("unknown ViewModel class")
    }
}
package com.example.forecastappeldorado.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.forecastappeldorado.model.WeatherModel
import com.example.forecasteldorado.service.WeatherAPIService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

private const val TAG = "MainRepository"

class MainRepository {
    private val weatherApiService = WeatherAPIService()
    private val disposable = CompositeDisposable()

    val weatherData = MutableLiveData<WeatherModel>()

    fun getDataFromAPI(cityName: String) {
        disposable.add(
            weatherApiService.getDataService(cityName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<WeatherModel>() {

                    override fun onSuccess(t: WeatherModel) {
                        weatherData.value = t
                        Log.d(TAG, "onSuccess: Success")
                    }

                    override fun onError(e: Throwable) {
                        Log.e(TAG, "onError: " + e)
                    }

                })
        )
    }
}
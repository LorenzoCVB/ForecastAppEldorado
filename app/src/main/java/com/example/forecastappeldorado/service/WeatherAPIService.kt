package com.example.forecasteldorado.service

import com.example.forecastappeldorado.model.WeatherModel
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class WeatherAPIService {

    //http://api.openweathermap.org/data/2.5/weather?q=moscow&APPID=45f4401277cd4e31d8e11cfa4ec29b84
    private val BASE_URL = "http://api.openweathermap.org/"

    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())

        //A call adapter which uses RxJava 2 for creating observables.
        // Adding this class to Retrofit allows you to return an Observable, Flowable, Single, Completable or Maybe from service methods.
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create()) //resolveu o problema do primeiro aplicativo
        .build()
        .create(WeatherAPI::class.java)

    fun getDataService(cityName: String): Single<WeatherModel> {
        return api.getData(cityName)
    }

}
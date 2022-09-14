package com.example.forecasteldorado.service

import com.example.forecastappeldorado.model.WeatherModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {

    //http://api.openweathermap.org/data/2.5/weather?q=moscow&APPID=45f4401277cd4e31d8e11cfa4ec29b84

    @GET("data/2.5/weather?&units=metric&APPID=45f4401277cd4e31d8e11cfa4ec29b84")
    fun getData(
        @Query("q") cityName: String
    ): Single<WeatherModel>

}
package com.example.myapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlin.random.Random

class ForecastRepository {

    private val _weeklyForecasts = MutableLiveData<List<DailyForecast>>()
    val weeklyForecasts: LiveData<List<DailyForecast>> = _weeklyForecasts

    fun loadForeCasts (zipcode: String){
        val randomValues = List(7){ Random.nextFloat().rem(100) * 100}
        val forecastItems = randomValues.map { temp ->
            DailyForecast(temp, "Partly Cloudy")
        }
        _weeklyForecasts.setValue(forecastItems)
    }
}
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
            DailyForecast(temp, getTempDesc(temp))
        }
        _weeklyForecasts.setValue(forecastItems)
    }

    private fun getTempDesc(temp: Float): String{
        return when(temp){
            in Float.MIN_VALUE.rangeTo(0f) -> "Anything below 0 doesn't make sense"
            in 0f.rangeTo(32f) -> "Way too cold"
            in 32f.rangeTo(55f) -> "Getting better"
            in 55f.rangeTo(100f) -> "Hot"
            in 100f.rangeTo(Float.MAX_VALUE) -> "Too hot"
            else -> "Does not compute"
        }
    }
}
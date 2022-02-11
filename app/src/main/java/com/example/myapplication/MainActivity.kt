 package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*

 class MainActivity : AppCompatActivity() {

     private val forecastRepository = ForecastRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editTextZipCode = findViewById<EditText>(R.id.editTextZipCode)
        val btnZipCode = findViewById<Button>(R.id.btnSubmitZipCode)


        btnZipCode.setOnClickListener {
            val zipcode: String = editTextZipCode.text.toString()

            if (zipcode.length != 5){
                Toast.makeText(this,"Enter a valid zipcode",Toast.LENGTH_SHORT).show()
            }else{
                forecastRepository.loadForeCasts(zipcode)
                // Toast.makeText(this,"Your zipcode is $zipcode",Toast.LENGTH_SHORT).show()
            }
        }

        val forecastlist: RecyclerView = findViewById(R.id.rvForecastList)
        forecastlist.layoutManager = LinearLayoutManager(this)

        val dailyForecastAdapter = DailyForecastAdapter(){forecastItem ->
            val msg = getString(R.string.forecast_clicked_format, forecastItem.temp, forecastItem.description)
            Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
        }
        forecastlist.adapter = dailyForecastAdapter


        val weeklyForecastObserver = Observer<List<DailyForecast>> {forecastItems ->
            //update list adapter
            dailyForecastAdapter.submitList(forecastItems)
        }

        forecastRepository.weeklyForecasts.observe(this,weeklyForecastObserver)
    }
}
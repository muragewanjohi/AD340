 package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import java.util.*

 class MainActivity : AppCompatActivity() {

     private val forecastRepository : ForecastRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val editTextZipCode = findViewById<EditText>(R.id.editTextZipCode)
        val btnZipCode = findViewById<Button>(R.id.btnSubmitZipCode)
        val zipcode = editTextZipCode.text.toString()
        btnZipCode.setOnClickListener {
            if (zipcode.length != 5){
                Toast.makeText(this,"Enter a valid zipcode",Toast.LENGTH_SHORT).show()
            }else{
                forecastRepository.loadForeCasts(zipcode)
                // Toast.makeText(this,"Your zipcode is $zipcode",Toast.LENGTH_SHORT).show()
            }
        }


        val weeklyForecastObserver = Observer<List<DailyForecast>> {forecastItems ->
            //
        }

        forecastRepository.weeklyForecasts.observe(this,weeklyForecastObserver)
    }
}
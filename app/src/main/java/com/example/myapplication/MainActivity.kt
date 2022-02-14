 package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.details.ForecastDetailsActivity
import java.util.*

 class MainActivity : AppCompatActivity() {

     private val forecastRepository = ForecastRepository()
     private lateinit var tempDisplaySettingManager: TempDisplaySettingManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        tempDisplaySettingManager = TempDisplaySettingManager(this)

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

        val dailyForecastAdapter = DailyForecastAdapter(tempDisplaySettingManager){forecastItem ->

            showForeCastDetails(forecastItem)
        }
        forecastlist.adapter = dailyForecastAdapter


        val weeklyForecastObserver = Observer<List<DailyForecast>> {forecastItems ->
            //update list adapter
            dailyForecastAdapter.submitList(forecastItems)
        }

        forecastRepository.weeklyForecasts.observe(this,weeklyForecastObserver)
    }

     private fun showForeCastDetails(forecast: DailyForecast){

         val forecastDetailsIntent = Intent(this,ForecastDetailsActivity::class.java)
         forecastDetailsIntent.putExtra("key_temp",forecast.temp)
         forecastDetailsIntent.putExtra("key_description",forecast.description )
         startActivity(forecastDetailsIntent)

     }
     override fun onCreateOptionsMenu(menu: Menu?): Boolean {
         val inflater: MenuInflater = menuInflater
         inflater.inflate(R.menu.settings_menu, menu)
         return true
     }

     override fun onOptionsItemSelected(item: MenuItem): Boolean {
         return when(item.itemId){
             R.id.temp_display_setting -> {
                 showTempDisplayDialog(this,tempDisplaySettingManager)
                 return true
             }
             else -> super.onOptionsItemSelected(item)

         }
     }
}
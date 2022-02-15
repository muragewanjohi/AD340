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
import com.example.myapplication.forecast.CurrentForecastFragment
import com.example.myapplication.location.LocationEntryFragment
import java.util.*

 class MainActivity : AppCompatActivity(), AppNavigator {

     //private val forecastRepository = ForecastRepository()
     private lateinit var tempDisplaySettingManager: TempDisplaySettingManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportFragmentManager
            .beginTransaction()
            .add(R.id.fragmentContainer,LocationEntryFragment())
            .commit()
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

     override fun navigateToCurrentForecast(zipcode: String) {
        // forecastRepository.loadForeCasts(forecast)
         supportFragmentManager
             .beginTransaction()
             .add(R.id.fragmentContainer,CurrentForecastFragment.newInstance(zipcode))
             .commit()
     }

     override fun navigateToLocationEntry() {
         supportFragmentManager
             .beginTransaction()
             .add(R.id.fragmentContainer,LocationEntryFragment())
             .commit()
     }
 }
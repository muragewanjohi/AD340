package com.example.myapplication.forecast

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.lifecycle.Observer
import com.example.myapplication.*
import com.example.myapplication.details.ForecastDetailsActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CurrentForecastFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CurrentForecastFragment : Fragment() {

    private lateinit var tempDisplaySettingManager: TempDisplaySettingManager
    private val forecastRepository = ForecastRepository()
    lateinit var appNavigator: AppNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appNavigator = context as AppNavigator

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_current_forecast, container, false)

        val locationEntryButton: FloatingActionButton = view.findViewById(R.id.locationEntryButton)
        locationEntryButton.setOnClickListener{
            appNavigator.navigateToLocationEntry()
        }

        val zipcode = requireArguments().getString(KEY_ZIPCODE) ?: ""

        tempDisplaySettingManager = TempDisplaySettingManager(requireContext())

        val forecastlist: RecyclerView = view.findViewById(R.id.rvForecastList)
        forecastlist.layoutManager = LinearLayoutManager(requireContext())

        val dailyForecastAdapter = DailyForecastAdapter(tempDisplaySettingManager) { forecastItem ->

            showForeCastDetails(forecastItem)
        }
        forecastlist.adapter = dailyForecastAdapter


        val weeklyForecastObserver = Observer<List<DailyForecast>> { forecastItems ->
            //update list adapter
            dailyForecastAdapter.submitList(forecastItems)
        }

        forecastRepository.weeklyForecasts.observe(viewLifecycleOwner, weeklyForecastObserver)

        forecastRepository.loadForeCasts(zipcode)

        return view
    }

    private fun showForeCastDetails(forecast: DailyForecast) {

        val forecastDetailsIntent = Intent(requireContext(), ForecastDetailsActivity::class.java)
        forecastDetailsIntent.putExtra("key_temp", forecast.temp)
        forecastDetailsIntent.putExtra("key_description", forecast.description)
        startActivity(forecastDetailsIntent)

    }

    companion object {
        const val KEY_ZIPCODE = "key_zipcode"

        fun newInstance(zipcode: String): CurrentForecastFragment {

            val fragment = CurrentForecastFragment()

            val args = Bundle()
            args.putString(KEY_ZIPCODE, zipcode)

            fragment.arguments = args

            return fragment

        }
    }

}
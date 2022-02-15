package com.example.myapplication.location

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.myapplication.AppNavigator
import com.example.myapplication.R


class LocationEntryFragment : Fragment() {

    lateinit var appNavigator: AppNavigator

    override fun onAttach(context: Context) {
        super.onAttach(context)
        appNavigator = context as AppNavigator
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_location_entry, container, false)

        val editTextZipCode = view.findViewById<EditText>(R.id.editTextZipCode)
        val btnZipCode = view.findViewById<Button>(R.id.btnSubmitZipCode)


        btnZipCode.setOnClickListener {
            val zipcode: String = editTextZipCode.text.toString()

            if (zipcode.length != 5){
                Toast.makeText(requireContext(),"Enter a valid zipcode", Toast.LENGTH_SHORT).show()
            }else{
               // forecastRepository.loadForeCasts(zipcode)
                appNavigator.navigateToCurrentForecast(zipcode)
            }
        }

        return view
    }

}
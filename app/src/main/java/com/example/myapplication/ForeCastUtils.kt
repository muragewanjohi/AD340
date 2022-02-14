package com.example.myapplication

import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import java.security.AccessControlContext


fun formatForeCast(temp: Float, tempDisplaySetting: TempDisplaySetting) : String {
    return when(tempDisplaySetting){
        TempDisplaySetting.Farenheight -> String.format("%.2f °F", temp)
        TempDisplaySetting.Celcius -> {
             val temp = (temp - 32f) * (5f/9f)
            String.format("%.2f °C", temp)
        }
    }
}

fun makeToast(context: Context, msg: String){
    Toast.makeText(context,msg,Toast.LENGTH_SHORT).show()
}

fun showTempDisplayDialog(context: Context, tempDisplaySettingManager: TempDisplaySettingManager){
    val dialogBuilder = AlertDialog.Builder(context)
        .setTitle("Choose display units")
        .setMessage("Choose which temperature")
        .setPositiveButton("F °") {_, _ ->
            tempDisplaySettingManager.updateSetting(TempDisplaySetting.Farenheight)
        }
        .setNegativeButton("C °") {_, _ ->
            tempDisplaySettingManager.updateSetting(TempDisplaySetting.Celcius)
        }
        .setOnDismissListener{
            makeToast(context,"Settings will take effect after restarting")
        }

    dialogBuilder.show()
}
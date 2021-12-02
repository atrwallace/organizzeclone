package com.example.organizze.main.helper

import android.icu.text.SimpleDateFormat
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi

class CustomData {
    var date = System.currentTimeMillis()
    companion object {
        @RequiresApi(Build.VERSION_CODES.N)
        fun dataAtual(): String{
            var date = System.currentTimeMillis()
            var dateformat = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                SimpleDateFormat("dd-MM-yyyy")
            } else {
                java.text.SimpleDateFormat("dd-MM-yyyy")
            }
            var dateString = dateformat.format(date)
            return dateString
        }
    }
}
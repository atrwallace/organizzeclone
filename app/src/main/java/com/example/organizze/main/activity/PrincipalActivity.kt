package com.example.organizze.main.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CalendarView
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.example.organizze.R
import com.example.organizze.databinding.ActivityPrincipalBinding
import com.example.organizze.main.configs.CustomCalendar

class PrincipalActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPrincipalBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPrincipalBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initViewers()
        initListeners()

    }

    fun initViewers() {

    }

    fun initListeners() {
        binding.btnDespesas.setOnClickListener { abrirDesp() }
        binding.btnReceita.setOnClickListener { abrirRec() }
        recuperarCalendario()
    }

    fun abrirDesp() {
        startActivity(Intent(this@PrincipalActivity, DespesaActivity::class.java))

    }

    fun abrirRec() {
        startActivity(Intent(this@PrincipalActivity, ReceitaActivity::class.java))
    }

    fun recuperarCalendario() {
        binding.calendarView.setOnDateChangeListener(object : CalendarView.OnDateChangeListener{
            override fun onSelectedDayChange(
                view: CalendarView,
                year: Int,
                month: Int,
                dayOfMonth: Int
            ) {
              Log.i("ai", year.toString() + month.toString())
            }
        })
    }
}
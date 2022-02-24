package com.geotec.cenotesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.geotec.cenotesapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    // variable para acceder al contenido de las vistas
    private lateinit var v: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        v = ActivityMainBinding.inflate(layoutInflater)
        setContentView(v.root)
    }
}
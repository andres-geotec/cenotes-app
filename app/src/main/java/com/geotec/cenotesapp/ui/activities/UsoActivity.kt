package com.geotec.cenotesapp.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.geotec.cenotesapp.R

class UsoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_uso)

        val actionbar = getSupportActionBar()
        // si es una nueva captura mostrar: "Nuevo cenote"
        actionbar?.setTitle(R.string.cenote_name)
        actionbar?.setSubtitle(R.string.cenite_id)
    }
}
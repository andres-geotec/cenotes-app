package com.geotec.cenotesapp.ui.inicio

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.geotec.cenotesapp.R

class MainActivity : AppCompatActivity() {
    private val fragmentTransition = supportFragmentManager.beginTransaction()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fragmentTransition.add(R.id.fragmentToolbarMain, ToolbarMainFragment())
        fragmentTransition.commit()
    }
}
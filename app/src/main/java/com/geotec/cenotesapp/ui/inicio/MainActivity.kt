package com.geotec.cenotesapp.ui.inicio

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.geotec.cenotesapp.R
import com.geotec.cenotesapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var bv : ActivityMainBinding
    private val fragmentTransition = supportFragmentManager.beginTransaction()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bv = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bv.root)

        fragmentTransition.add(R.id.fragmentToolbarMain, ToolbarMainFragment())
        fragmentTransition.commit()

        bv.buttonTest.setOnClickListener {
            Toast.makeText(this@MainActivity, R.string.appName, Toast.LENGTH_SHORT).show()
        }
    }
}
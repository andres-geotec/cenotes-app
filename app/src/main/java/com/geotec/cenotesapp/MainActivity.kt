package com.geotec.cenotesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.geotec.cenotesapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    // variable para acceder al contenido de las vistas
    private lateinit var v: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        v = ActivityMainBinding.inflate(layoutInflater)
        setContentView(v.root)

        setSupportActionBar(v.tbrMain)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //return super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.menu_popup_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.mnuMainOpAbout -> Toast.makeText(
                this,
                getString(R.string.menu_main_op_about),
                Toast.LENGTH_SHORT
            ).show()
            R.id.mnuMainOpSettings -> Toast.makeText(
                this,
                getString(R.string.menu_main_op_settings),
                Toast.LENGTH_SHORT
            ).show()
        }
        return super.onOptionsItemSelected(item)
    }
}
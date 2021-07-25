package com.geotec.cenotesapp.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.geotec.cenotesapp.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button_new_record = findViewById(R.id.button_new_record) as Button
        button_new_record.setOnClickListener {
            abrirEdicionCenote()
        }
    }

    fun abrirEdicionCenote() {
        val intent = Intent(this, EditorCenoteActivity::class.java)
        startActivity(intent)
        //Toast.makeText(this@MainActivity, "mandar actividad", Toast.LENGTH_SHORT).show()
    }
}
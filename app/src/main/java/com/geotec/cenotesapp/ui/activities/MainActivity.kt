package com.geotec.cenotesapp.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.geotec.cenotesapp.R
import com.geotec.cenotesapp.databinding.ActivityMainBinding
import com.geotec.cenotesapp.ui.editor.EditorActivity

class MainActivity : AppCompatActivity() {
    private lateinit var bv : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)
        bv = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bv.root)

        bv.btnNewRecord.setOnClickListener {
            startActivity(Intent(this, EditorActivity::class.java))
        }


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